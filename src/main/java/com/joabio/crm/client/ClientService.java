package com.joabio.crm.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joabio.crm.client.dto.ClientDTO;
import com.joabio.crm.client.dto.ClientPageDTO;
import com.joabio.crm.client.dto.ClientRequestDTO;
import com.joabio.crm.client.dto.mapper.ClientMapper;
import com.joabio.crm.client.enums.Status;
import com.joabio.crm.exception.BusinessException;
import com.joabio.crm.exception.RecordNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
@Validated
@SuppressWarnings("null")
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
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

    public ClientDTO findById(@Positive @NotNull Long id) {
        return clientRepository.findById(id).map(clientMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public ClientDTO create(@Valid ClientRequestDTO clientRequestDTO) {
        clientRepository.findByCpf(clientRequestDTO.cpf()).stream()
                .filter(c -> c.getStatus().equals(Status.ACTIVE))
                .findAny().ifPresent(c -> {
                    throw new BusinessException("A client with cpf " + clientRequestDTO.cpf() + " already exists.");
                });
        Client client = clientMapper.toModel(clientRequestDTO);
        client.setStatus(Status.ACTIVE);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    public ClientDTO update(@Positive @NotNull Long id, @Valid ClientRequestDTO clientRequestDTO) {
        return clientRepository.findById(id).map(actual -> {
            actual.setName(clientRequestDTO.name());
            actual.setCpf(clientRequestDTO.cpf());
            actual.setCategory(clientMapper.convertCategoryValue(clientRequestDTO.category()));
            mergeTicketsForUpdate(actual, clientRequestDTO);
            return clientMapper.toDTO(clientRepository.save(actual));
        })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    private void mergeTicketsForUpdate(Client updatedClient, ClientRequestDTO clientRequestDTO) {

        // find the tickets that were removed
        List<Ticket> ticketsToRemove = updatedClient.getTickets().stream()
                .filter(ticket -> clientRequestDTO.tickets().stream()
                        .anyMatch(ticketDto -> ticketDto._id() != 0 && ticketDto._id() != ticket.getId()))
                .toList();
        ticketsToRemove.forEach(updatedClient::removeTicket);

        clientRequestDTO.tickets().forEach(ticketDto -> {
            // new ticket, add it
            if (ticketDto._id() == 0) {
                updatedClient.addTicket(clientMapper.convertTicketDTOToTicket(ticketDto));
            } else {
                // existing ticket, find it and update
                updatedClient.getTickets().stream()
                        .filter(ticket -> ticket.getId() == ticketDto._id())
                        .findAny()
                        .ifPresent(ticket -> {
                            ticket.setTitle(ticketDto.title());
                            ticket.setDescricao(ticketDto.descricao());
                        });
            }
        });
    }

    public void delete(@Positive @NotNull Long id) {
        clientRepository.delete(clientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
