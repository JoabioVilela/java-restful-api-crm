package com.joabio.crm.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.joabio.crm.enums.Category;
import com.joabio.crm.shared.validation.ValueOfEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Used as request object that represents a Client.
 */
public record ClientRequestDTO(
        @NotBlank @NotNull @Length(min = 5, max = 200) String name,
        @NotBlank @NotNull @Length(min = 11, max = 11) String cpf,
        @NotBlank @NotNull @Length(min = 11, max = 11) String telefone,
        @NotBlank @NotNull @ValueOfEnum(enumClass = Category.class) String category,
        @NotNull @NotEmpty @Valid List<TicketDTO> tickets) {
}