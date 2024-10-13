package com.joabio.crm.client.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Used as response and request object that represents a Ticket.
 */
public record TicketDTO(
                int _id,
                @NotBlank @NotNull @Length(min = 5, max = 30) String title,
                @NotBlank @NotNull @Length(min = 100, max = 300) String descricao) {
}