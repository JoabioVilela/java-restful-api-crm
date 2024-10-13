package com.joabio.crm.client.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.joabio.crm.client.Client;
import com.joabio.crm.client.Ticket;
import com.joabio.crm.client.dto.ClientDTO;
import com.joabio.crm.client.dto.ClientRequestDTO;
import com.joabio.crm.client.dto.TicketDTO;
import com.joabio.crm.client.enums.Category;

/**
 * Class to map the Client entity to the ClientRequestDTO and vice-versa.
 * ModelMapper currently does not support record types.
 */
@Component
public class ClientMapper {

    public Client toModel(ClientRequestDTO clientRequestDTO) {

        Client client = new Client();
        client.setName(clientRequestDTO.name());
        client.setCpf(clientRequestDTO.cpf());
        client.setCategory(convertCategoryValue(clientRequestDTO.category()));

        Set<Ticket> tickets = clientRequestDTO.tickets().stream()
                .map(ticketDTO -> {
                    Ticket ticket = new Ticket();
                    if (ticketDTO._id() > 0) {
                        ticket.setId(ticketDTO._id());
                    }
                    ticket.setTitle(ticketDTO.title());
                    ticket.setDescricao(ticketDTO.descricao());
                    ticket.setClient(client);
                    return ticket;
                }).collect(Collectors.toSet());
        client.setTickets(tickets);

        return client;
    }

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }
        List<TicketDTO> ticketDTOList = client.getTickets()
                .stream()
                .map(ticket -> new TicketDTO(ticket.getId(), ticket.getTitle(), ticket.getDescricao()))
                .toList();
        return new ClientDTO(client.getId(), client.getName(), client.getCpf(), client.getCategory().getValue(),
                ticketDTOList);
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Bronze" -> Category.BRONZE;
            case "Prata" -> Category.PRATA;
            case "Ouro" -> Category.OURO;
            default -> throw new IllegalArgumentException("Invalid Category.");
        };
    }

    public Ticket convertTicketDTOToTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO._id());
        ticket.setTitle(ticketDTO.title());
        ticket.setDescricao(ticketDTO.descricao());
        return ticket;
    }

}
