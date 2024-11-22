# Microsserviço CRM - Backend Java API RESTful

Tecnologias e Metodologias:

- Java 17, Microsserviços, RabbitMQ, Swagger UI, Maven, Spring Framework, Basic Auth, DTO, Mapper, Hibernate, MySQL.

Hoje, 22/11/2024, este repositório contém uma aplicação web em Java com endpoints que permitem realizar operações CRUD (Criar, Ler, Atualizar e Excluir) em registros de clientes e tickets de um CRM.

No raiz do projeto há um arquivo compose.yaml contendo o serviço MySQL.

No arquivo src/main/resources/application-dev.properties contém as configurações de acesso ao banco de dados e do RabbitMQ.

Esta aplicação contempla recursos como validações, tratamento de exceções, mensageria com 2 níveis de resiliência, Dead Letter Queue, autenticação e controle de acesso, entre outros.

Para realizar testes com clientes HTTP basta clonar o repositório, iniciar o serviço de banco de dados MySQL através do comando `docker compose up -d` no raiz do projeto, iniciar o serviço de RabbitMQ através do comando `docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management`, iniciar o microsserviço de notificação SMS que está está disponível no repositório [https://github.com/JoabioVilela/notificacaosms](https://github.com/JoabioVilela/notificacaosms) e executar este microsserviço (# Microsserviço CRM - Backend Java API RESTful) que estará disponível em localhost na porta `8080`. O Swagger UI estará disponível em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) conforme imagem:

![image](https://github.com/user-attachments/assets/9de90841-7058-4853-ae15-c0d96f8faa15)


Caso haja alguma dúvida entre em contato: joabio.vilela@gmail.com

Implementações futuras:

- testes unitários com JUnit

- frontend com Angular
