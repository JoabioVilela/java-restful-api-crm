# Microsserviços, Mensageria, Backend Java API RESTful - CRM (Customer Relationship Management)
## Demonstração de funcionamento
<img width="1680" height="1079" alt="Captura de tela funcionamento" src="https://github.com/user-attachments/assets/f1e40bb7-0034-4e22-8541-61000d6feadf" />

## Índice

- [Descrição](#descrição)
- [Tecnologias e Ferramentas](#tecnologias-e-ferramentas)
- [Estrutura do Projeto](#estrutura-do-projeto)
  - [Configuração](#configuração)
  - [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Recursos Adicionais](#recursos-adicionais)
- [Implementações Futuras](#implementações-futuras)
- [Contato](#contato)



## Descrição

Este repositório contém uma aplicação web desenvolvida em **Java 21**, projetada para gerenciar um sistema CRM. Nela, criei 2 microsserviços:

- Gestão de Clientes e Tickets
- Notificação

A aplicação oferece **endpoints RESTful** que permitem realizar operações CRUD (Criar, Ler, Atualizar e Excluir) em registros de clientes e tickets, com um relacionamento **OneToMany** (um cliente para muitos tickets).

Ao realizar um novo cadastro, uma mensagem é enviada via **RabbitMQ** para um microsserviço de notificação, que informa ao usuário vinculado ao cadastro.

A aplicação inclui recursos como:

- Mensageria com **Dead Letter Queue** e 3 níveis de resiliência.
- Validações e tratamento de exceções.
- Autenticação com **Basic Auth** e controle de acesso.
- Uso de **DTOs** e **Mapper** para abstração e eficiência, entre outros.


## Tecnologias e Ferramentas:

- **Java 21**

- **RabbitMQ**

- **Swagger UI**

- **Maven**

- **Spring Framework**

- **Hibernate**

- **MySQL**

- **Docker**

- **Jenkins**

- **Prometheus**

- **Observabilidade**

- **Kubernetes**

---


## Estrutura do Projeto

### Configuração

- As configurações do banco de dados e RabbitMQ estão no arquivo: `src/main/resources/application-dev.properties`.
- O arquivo `compose.yaml` na raiz do projeto contém o serviço MySQL configurado para execução com Docker Compose.

---


## Pré-requisitos

Certifique-se de que você tenha o seguinte instalado em sua máquina:

- Docker e Docker Compose
- Java 21+
- Maven
- Jenkins
- Kubernetes

---


## Como Executar

### 1. Clone o repositório

bash

`git clone <URL_DO_REPOSITORIO>`

`cd <PASTA_DO_REPOSITORIO>`

isto é:

`git clone git@github.com:JoabioVilela/java-restful-api-crm.git`

`cd java-restful-api-crm`

### 2. Inicie os serviços
   
`docker compose up -d --build`

- ### Microsserviço de Notificação (SMS):

Certifique-se de clonar e executar o microsserviço de notificação, disponível no repositório: [https://github.com/JoabioVilela/notificacaosms](https://github.com/JoabioVilela/notificacaosms)

### 3. Compile e execute
   
Ao executar, este microsserviço (Microsserviço CRM - Backend Java API RESTful) estará disponível em:

`http://localhost:9090`

Observação: esta etapa será alterada|atualizada, pois implementei pipeline CI/CD e orquestração de contêineres com Docker, Jenkins e Kubernetes.

### 4. Acesse o Swagger UI

A documentação interativa dos endpoints pode ser acessada em:

`http://localhost:9090/swagger-ui/index.html`

Exemplo de visualização do Swagger:

![image](https://github.com/user-attachments/assets/9de90841-7058-4853-ae15-c0d96f8faa15)

### 5. Monitoramento com Prometheus

Monitoramento configurado, implementado e funcionando.

Como exemplo, há uma métrica chamada `crm.clients.created` que monitora o `Número de clientes criados`.

Disponível em `http://localhost:9090/actuator/metrics/crm.clients.created`:

<img width="538" height="392" alt="image" src="https://github.com/user-attachments/assets/23bf6e40-e02c-480e-8001-bc0d9fc0dfc1" />

### 6. Observabilidade com Grafana

Observabilidade configurada, implementada e funcionando.

Como exemplo, há uma métrica chamada `crm_clients_total`, que permite observar o `Número total de clientes cadastrados`.

Disponível em `http://localhost:3000`:

<img width="1600" height="915" alt="image" src="https://github.com/user-attachments/assets/36d7ca82-28d4-4b00-a6d4-45357c4f3a7a" />

---


## Recursos Adicionais

### Mensageria

- Mensagens são gerenciadas com RabbitMQ e incluem:
  - **Dead Letter Queue** para processamento de mensagens não entregues.
  - 3 níveis de resiliência para alta disponibilidade.

### Relacionamentos

- Gerenciamento de relacionamento **OneToMany** entre clientes e tickets.

---


## Implementações futuras:

- Observabilidade com Grafana

- Frontend com Angular

- 2 Microsserviços:

    - Automação de Marketing
    - Relatórios e Análises


---


## Contato

Se precisar de suporte ou encontrar problemas, entre em contato ou abra uma **Issue** neste repositório.

Atenciosamente,

Joabio Vilela

joabio.vilela@gmail.com

[https://www.linkedin.com/in/joabiovilela](https://www.linkedin.com/in/joabiovilela)
