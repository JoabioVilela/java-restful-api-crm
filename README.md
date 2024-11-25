# Microsserviço Backend Java API RESTful - CRM (Customer Relationship Management)

## Descrição

Este repositório contém uma aplicação web desenvolvida em **Java 17**, projetada para gerenciar um sistema CRM. A aplicação oferece **endpoints RESTful** que permitem realizar operações CRUD (Criar, Ler, Atualizar e Excluir) em registros de clientes e tickets, com um relacionamento **OneToMany** (um cliente para muitos tickets).

Ao realizar um novo cadastro, uma mensagem é enviada via **RabbitMQ** para um microsserviço de notificação, que informa ao usuário vinculado ao cadastro.

A aplicação inclui recursos como:

- Mensageria com **Dead Letter Queue** e dois níveis de resiliência.
- Validações e tratamento de exceções.
- Autenticação com **Basic Auth** e controle de acesso.
- Uso de **DTOs** e **Mapper** para abstração e eficiência, entre outros.

## Tecnologias e Ferramentas:

- **Java 17**

- **RabbitMQ**

- **Swagger UI**

- **Maven**

- **Spring Framework**

- **Hibernate**

- **MySQL**

---

## Estrutura do Projeto

### Configuração

- As configurações do banco de dados e RabbitMQ estão no arquivo: `src/main/resources/application-dev.properties`.
- O arquivo `compose.yaml` na raiz do projeto contém o serviço MySQL configurado para execução com Docker Compose.

---

## Pré-requisitos

Certifique-se de que você tenha o seguinte instalado em sua máquina:

- Docker e Docker Compose
- Java 17+
- Maven

---

## Como Executar

### 1. Clone o repositório

```bash

git clone <URL_DO_REPOSITORIO>

cd <PASTA_DO_REPOSITORIO>

isto é:

git clone https://github.com/JoabioVilela/java-restful-api-crm

cd java-restful-api-crm

## 2. Inicie os serviços necessários
   
### Banco de dados MySQL:

`docker compose up -d`

### RabbitMQ:

`docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management`

### Microsserviço de Notificação (SMS):

Certifique-se de clonar e executar o microsserviço de notificação, disponível no repositório: [https://github.com/JoabioVilela/notificacaosms](https://github.com/JoabioVilela/notificacaosms).

## 3. Compile e execute
   
Ao executar, este microsserviço (Microsserviço CRM - Backend Java API RESTful) estará disponível em:

`http://localhost:8080`

## 4. Acesse o Swagger UI

A documentação interativa dos endpoints pode ser acessada em:

`http://localhost:8080/swagger-ui/index.html`

Exemplo de visualização do Swagger:

![image](https://github.com/user-attachments/assets/9de90841-7058-4853-ae15-c0d96f8faa15)

---

## Recursos Adicionais

### Mensageria

- Mensagens são gerenciadas com RabbitMQ e incluem:
  - **Dead Letter Queue** para processamento de mensagens não entregues.
  - Dois níveis de resiliência para alta disponibilidade.

### Relacionamentos

- Gerenciamento de relacionamento **OneToMany** entre clientes e tickets.

---

Implementações futuras:

- testes unitários com JUnit

- frontend com Angular

---

Se precisar de suporte ou encontrar problemas, entre em contato ou abra uma **Issue** neste repositório.

Atenciosamente,

Joábio Vilela

joabio.vilela@gmail.com
