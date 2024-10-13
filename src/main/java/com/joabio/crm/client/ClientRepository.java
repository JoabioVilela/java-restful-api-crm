package com.joabio.crm.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.joabio.crm.client.enums.Status;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByStatus(Pageable pageable, Status status);

    List<Client> findByName(String name);

    List<Client> findByCpf(String cpf);
}
