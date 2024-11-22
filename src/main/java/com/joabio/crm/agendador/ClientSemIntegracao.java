package com.joabio.crm.agendador;

import com.joabio.crm.repository.ClientRepository;
import com.joabio.crm.service.NotificacaoSmsRabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ClientSemIntegracao {

    private final ClientRepository clientRepository;

    private final NotificacaoSmsRabbitMqService notificacaoSmsRabbitMqService;

    private final Logger logger = LoggerFactory.getLogger(ClientSemIntegracao.class);

    public ClientSemIntegracao(
        ClientRepository clientRepository,
        NotificacaoSmsRabbitMqService notificacaoSmsRabbitMqService) {
        this.clientRepository = clientRepository;
        this.notificacaoSmsRabbitMqService = notificacaoSmsRabbitMqService;
    }

    @Scheduled(fixedDelay = 20, timeUnit = TimeUnit.SECONDS)
    public void buscarClientsSemIntegracao() {
        clientRepository.findAllByIntegradaIsFalse()
        .forEach(client -> {
            try {
                notificacaoSmsRabbitMqService.notificar(client);
                clientRepository.atualizarStatusIntegrada(client.getId(), true);
            } catch (RuntimeException ex) {
                logger.error(ex.toString());
            }
        });
    }
}