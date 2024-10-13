package com.joabio.crm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.joabio.crm.client.Client;
import com.joabio.crm.client.ClientRepository;
import com.joabio.crm.client.Ticket;
import com.joabio.crm.client.enums.Category;
import com.joabio.crm.client.enums.Status;

@SpringBootApplication
public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner initDatabase(ClientRepository clientRepository) {
		return args -> extracted(clientRepository);
	}

	private void extracted(ClientRepository clientRepository) {
		clientRepository.deleteAll();
		for (int i = 1; i < 5; i++) {
			Client c = new Client();
			c.setName("Client " + i);
			c.setCpf("1234567890" + i);
			c.setCategory(Category.BRONZE);
			c.setStatus(Status.ACTIVE);

			for (int j = 1; j < 10; j++) {
				Ticket ticket = new Ticket();
				ticket.setTitle("Ticket " + j);
				ticket.setDescricao("Lorem ipsum dolor sit amet. Ex sequi sapiente eos dolor aperiam non consequatur laborum hic dolore repudiandae aut placeat blanditiis. Et ducimus quidem aut voluptatum quidem aut galisum neque.");
				c.addTicket(ticket);
			}

			clientRepository.save(c);
		}
	}

}
