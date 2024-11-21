# CRM - Backend Java API RESTful

Tecnologias e Metodologias:

- Java 17, Maven, Spring Framework, Basic Auth, DTO, Mapper, Hibernate, MySQL.

Hoje, 13/10/2024, este repositório contém uma aplicação web em Java com endpoints que permitem realizar operações CRUD (Criar, Ler, Atualizar e Excluir) em registros de clientes e tickets de um CRM.

No raiz do projeto há um arquivo compose.yaml contendo o serviço MySQL.

No arquivo src/main/resources/application-dev.properties contém as configurações de acesso ao banco de dados.

Esta aplicação contempla recursos como validações, tratamento de exceções, autenticação e controle de acesso, entre outros.

Para realizar testes com clientes HTTP basta clonar o repositório, iniciar o serviço de banco de dados MySQL através do comando `docker compose up -d`, iniciar o serviço de RabbitMQ através do comando `docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management` e executar a aplicação.

Caso haja alguma dúvida entre em contato: joabio.vilela@gmail.com

Implementações futuras:

- microsserviços com RabbitMQ

- testes unitários com JUnit

- frontend com Angular
