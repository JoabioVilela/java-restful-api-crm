package com.joabio.crm.client;

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

import com.joabio.crm.client.dto.ClientDTO;
import com.joabio.crm.client.dto.ClientPageDTO;
import com.joabio.crm.client.dto.ClientRequestDTO;

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
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ClientPageDTO findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
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

    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable @Positive @NotNull Long id) {
        return clientService.findById(id);
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