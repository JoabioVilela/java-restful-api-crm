package com.joabio.testes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.joabio.crm.config.ConfiguracaoRabbitMQ;
import com.joabio.crm.entity.Client;
import com.joabio.crm.entity.Ticket;
import com.joabio.crm.enums.Category;
import com.joabio.crm.enums.Status;
import com.joabio.crm.service.NotificacaoSmsRabbitMqService;


@ExtendWith(MockitoExtension.class)
class NotificacaoSmsRabbitMqServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private NotificacaoSmsRabbitMqService notificacaoSmsRabbitMqService;

    private Client client, expectedClient;
    
    @BeforeEach
    void setUp() throws IllegalAccessException, InvocationTargetException {
        System.out.println("setUp");
        MockitoAnnotations.openMocks(this);

        // Criar o objeto Client manualmente
        client = new Client();
        client.setId(1L);
        client.setName("Binho Girafalis");
        client.setCpf("99999921990");
        client.setTelefone("73986932939");
        client.setCategory(Category.OURO);
        client.setStatus(Status.ACTIVE);
        client.setIntegrada(false);

        // Criar e adicionar tickets manualmente
        Ticket ticket2 = new Ticket();
        ticket2.setTitle("Ticket 2");
        ticket2.setDescricao("Descrição do ticket 2");
        ticket2.setClient(client); // Estabelece o relacionamento bidirecional

        Ticket ticket1 = new Ticket();
        ticket1.setTitle("Ticket 1");
        ticket1.setDescricao("Descrição do ticket 1");
        ticket1.setClient(client); // Estabelece o relacionamento bidirecional

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        client.setTickets(tickets);
        
        expectedClient = new Client();
        try {
        // Copia superficial dos atributos simples
        BeanUtils.copyProperties(expectedClient, client);
        
        // Realize a cópia profunda de coleções
        Set<Ticket> copiedTickets = new HashSet<>();
        for (Ticket ticket : client.getTickets()) {
                Ticket copiedTicket = new Ticket();
                BeanUtils.copyProperties(copiedTicket, ticket); // Cópia superficial de Ticket
                copiedTickets.add(copiedTicket);
            }
                    expectedClient.setTickets(copiedTickets);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void testNotificar() {

        notificacaoSmsRabbitMqService.notificar(client);

        //client.setTelefone("73986932222");

        verify(rabbitTemplate).convertAndSend(
                ConfiguracaoRabbitMQ.EXCHANGE_NAME,
                ConfiguracaoRabbitMQ.ROUTING_KEY,
                client
        );

        assertEquals(expectedClient, client, "O estado do objeto foi alterado após a execução!");
    }

}