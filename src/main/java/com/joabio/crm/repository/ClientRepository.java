package com.joabio.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.joabio.crm.entity.Client;
import com.joabio.crm.enums.Status;

import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByStatus(Pageable pageable, Status status);

    List<Client> findByName(String name);

    List<Client> findByCpf(String cpf);

    List<Client> findByTelefone(String telefone);

    List<Client> findByIntegrada(boolean integrada);

    List<Client> findAllByIntegradaIsFalse();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client SET integrada = :integrada WHERE id = :id")
    void atualizarStatusIntegrada(Long id, boolean integrada);
}