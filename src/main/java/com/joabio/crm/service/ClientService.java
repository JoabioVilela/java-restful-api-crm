package com.joabio.crm.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joabio.crm.dto.ClientDTO;
import com.joabio.crm.dto.ClientPageDTO;
import com.joabio.crm.dto.ClientRequestDTO;
import com.joabio.crm.dto.mapper.ClientMapper;
import com.joabio.crm.entity.Client;
import com.joabio.crm.enums.Status;
import com.joabio.crm.exception.BusinessException;
import com.joabio.crm.exception.RecordNotFoundException;
import com.joabio.crm.repository.ClientRepository;

import io.micrometer.core.instrument.Counter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
@Validated
//@SuppressWarnings("null")
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final NotificacaoSmsRabbitMqService notificacaoSmsRabbitMqService;
    private final Counter clientCreatedCounter;

    public ClientService(ClientRepository clientRepository, NotificacaoSmsRabbitMqService notificacaoSmsRabbitMqService,
        ClientMapper clientMapper, Counter clientCreatedCounter) {
        this.clientRepository = clientRepository;
        this.notificacaoSmsRabbitMqService = notificacaoSmsRabbitMqService;
        this.clientMapper = clientMapper;
        this.clientCreatedCounter = clientCreatedCounter;
    }

    public ClientPageDTO findAll(@PositiveOrZero int page, @Positive @Max(1000) int pageSize) {
        Page<Client> clientPage = clientRepository.findAll(PageRequest.of(page, pageSize));
        List<ClientDTO> list = clientPage.getContent().stream()
                .map(clientMapper::toDTO)
                .toList();
        return new ClientPageDTO(list, clientPage.getTotalElements(), clientPage.getTotalPages());
    }

    public List<ClientDTO> findByName(@NotNull @NotBlank String name) {
        return clientRepository.findByName(name).stream().map(clientMapper::toDTO).toList();
    }

    public List<ClientDTO> findByCpf(@NotNull @NotBlank String cpf) {
        return clientRepository.findByCpf(cpf).stream().map(clientMapper::toDTO).toList();
    }

    public List<ClientDTO> findByTelefone(@NotNull @NotBlank String telefone) {
        return clientRepository.findByTelefone(telefone).stream().map(clientMapper::toDTO).toList();
    }

    @SuppressWarnings("null")
    public ClientDTO findById(@Positive @NotNull Long id) {
        return clientRepository.findById(id).map(clientMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public List<ClientDTO> findByIntegrada(@NotNull @NotBlank boolean integrada) {
        return clientRepository.findByIntegrada(integrada).stream().map(clientMapper::toDTO).toList();
    }

    public ClientDTO create(@Valid ClientRequestDTO clientRequestDTO) {
        clientRepository.findByCpf(clientRequestDTO.cpf()).stream()
                .filter(c -> c.getStatus().equals(Status.ACTIVE))
                .findAny().ifPresent(c -> {
                    throw new BusinessException("Um cliente com CPF " + clientRequestDTO.cpf() + " já existe.");
                });
        Client client = clientMapper.toModel(clientRequestDTO);
        client.setStatus(Status.ACTIVE);
        client.setIntegrada(false);
        clientRepository.save(client);
        notificarRabbitMQ(client);
        clientCreatedCounter.increment();
        return clientMapper.toDTO(client);
    }

    private void notificarRabbitMQ(Client client) {
        try {
            log.info("Tentando notificar RabbitMQ para o cliente com ID {}", client.getId());
            notificacaoSmsRabbitMqService.notificar(client);
            clientRepository.atualizarStatusIntegrada(client.getId(), true);
            log.info("Notificação enviada com sucesso para o cliente com ID {}", client.getId());
        } catch (RuntimeException ex) {
            log.error("Erro ao notificar RabbitMQ para o cliente com ID {}", client.getId(), ex);
        }
    }

    @SuppressWarnings("null")
    public ClientDTO update(@Positive @NotNull Long id, @Valid ClientRequestDTO clientRequestDTO) {
        return clientRepository.findById(id).map(actual -> {
            actual.setName(clientRequestDTO.name());
            actual.setCpf(clientRequestDTO.cpf());
            actual.setTelefone(clientRequestDTO.telefone());
            actual.setCategory(clientMapper.convertCategoryValue(clientRequestDTO.category()));
            mergeTicketsForUpdate(actual, clientRequestDTO);
            return clientMapper.toDTO(clientRepository.save(actual));
        })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    private void mergeTicketsForUpdate(Client updatedClient, ClientRequestDTO clientRequestDTO) {

        clientRequestDTO.tickets().forEach(ticketDto -> {
            // new ticket, add it
            if (ticketDto._id() == 0) {
                updatedClient.addTicket(clientMapper.convertTicketDTOToTicket(ticketDto));
            } else {
                // existing ticket, find it and update
                updatedClient.getTickets().stream()
                        .filter(ticket -> Objects.equals(ticket.getId(), ticketDto._id()))
                        .findAny()
                        .ifPresent(ticket -> {
                            ticket.setTitle(ticketDto.title());
                            ticket.setDescricao(ticketDto.descricao());
                        });
            }
        });
    }

    @SuppressWarnings("null")
    public void delete(@Positive @NotNull Long id) {
        clientRepository.delete(clientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
