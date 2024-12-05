package com.joabio.crm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.joabio.crm.entity.Client;
import com.joabio.crm.entity.Ticket;
import com.joabio.crm.enums.Category;
import com.joabio.crm.enums.Status;
import com.joabio.crm.repository.ClientRepository;

@SpringBootApplication
@EnableScheduling
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
			c.setName("Cliente " + i);
			c.setCpf("1234567890" + i);
			c.setTelefone("7198693993" + i);
			c.setCategory(Category.BRONZE);
			c.setStatus(Status.ACTIVE);
			c.setIntegrada(false);

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
