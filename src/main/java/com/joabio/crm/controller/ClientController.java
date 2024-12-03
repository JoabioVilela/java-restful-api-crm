package com.joabio.crm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joabio.crm.dto.ClientDTO;
import com.joabio.crm.dto.ClientPageDTO;
import com.joabio.crm.dto.ClientRequestDTO;
import com.joabio.crm.service.ClientService;
import com.joabio.crm.validation.ValidatePagination;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Represents the REST API for the Client resource.
 */
@Validated
@RestController
@RequestMapping("api/clients")
@Tag(name = "API Clientes e Tickets (OneToMany)")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Obter registros de clientes", description = "Retorna todos os registros de clientes com seus respectivos tickets (OneToMany)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "200 Recuperado com sucesso"),
        @ApiResponse(responseCode = "404", description = "404 Not found - Os registros de clientes não foram encontrados")
    })
    public ClientPageDTO findAll( @RequestParam(defaultValue = "0") 
        @Parameter(name = "page", description = "O campo page indica o número da página a ser solicitada. A contagem começa em 0, ou seja, 0 representa a primeira página, 1 representa a segunda página, e assim por diante.", example = "4") 
        int page,
        @RequestParam(defaultValue = "10") 
        @Parameter(name = "pageSize", description = "O campo pageSize especifica o número de registros a serem retornados por página. Este valor determina a quantidade máxima de itens exibidos em cada página da resposta. Se não especificado, um valor padrão pode ser aplicado pelo sistema.", example = "10") 
        int pageSize) {

        ValidatePagination.validatePage(page);
        ValidatePagination.validatePageSize(pageSize);

        return clientService.findAll(page, pageSize);
    }

    @GetMapping("/searchByName")
    public List<ClientDTO> findByName(@RequestParam @NotNull @NotBlank String name) {
        return clientService.findByName(name);
    }

    @GetMapping("/searchByCpf")
    public List<ClientDTO> findByCpf(@RequestParam @NotNull @NotBlank String cpf) {
        return clientService.findByCpf(cpf);
    }

    @GetMapping("/searchByTelefone")
    public List<ClientDTO> findByTelefone(@RequestParam @NotNull @NotBlank String telefone) {
        return clientService.findByTelefone(telefone);
    }

    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable @Positive @NotNull Long id) {
        return clientService.findById(id);
    }

    @GetMapping("/searchByIntegrada")
    public List<ClientDTO> findByIntegrada(@RequestParam @NotNull @NotBlank boolean integrada) {
        return clientService.findByIntegrada(integrada);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClientDTO create(@RequestBody @Valid ClientRequestDTO client) {
        return clientService.create(client);
    }

    @PutMapping(value = "/{id}")
    public ClientDTO update(@PathVariable @Positive @NotNull Long id,
            @RequestBody @Valid ClientRequestDTO client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive @NotNull Long id) {
        clientService.delete(id);
    }
}