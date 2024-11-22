package com.joabio.crm.dto;

import java.util.List;

/**
 * Used as response object that represents a Page with a list of Client.
 */
public record ClientPageDTO(List<ClientDTO> clients, long totalElements, int totalPages) {

}
