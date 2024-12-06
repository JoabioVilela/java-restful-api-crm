package com.joabio.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.joabio.crm.config.ConfiguracaoRabbitMQ;
import com.joabio.crm.entity.Client;


@Service
public class NotificacaoSmsRabbitMqService {

    private RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(NotificacaoSmsRabbitMqService.class);

    public NotificacaoSmsRabbitMqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notificar(Client client) {
        try {
            logger.info("Enviando mensagem para RabbitMQ: {}", client);
            rabbitTemplate.convertAndSend(
            ConfiguracaoRabbitMQ.EXCHANGE_NAME,
            ConfiguracaoRabbitMQ.ROUTING_KEY,
            client
        );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para RabbitMQ para o cliente com ID: " + client.getId() + "\nA mensagem de erro é: " + e.getMessage());
        }
   }

}
