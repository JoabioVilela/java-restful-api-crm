package com.joabio.crm.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Used as response object that represents a Course
 */
public record ClientDTO(
        @JsonProperty("_id") Long id,
        String name, String cpf, String telefone, String category, List<TicketDTO> tickets) {
}
