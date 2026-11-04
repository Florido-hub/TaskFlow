# TaskFlow

**TaskFlow** é uma API REST para gerenciamento de tarefas, desenvolvida com Java e Spring Boot. O projeto tem como objetivo oferecer operações para criação, consulta, atualização e gerenciamento do ciclo de vida de tarefas, aplicando princípios de arquitetura limpa, separação de responsabilidades e organização do código.

O projeto utiliza **Arquitetura Hexagonal (Ports and Adapters)**, mantendo as regras de negócio independentes dos mecanismos de persistência e da camada de entrada da aplicação.

> 🚧 **Status:** Em desenvolvimento.

## Tecnologias

As principais tecnologias e ferramentas utilizadas são:

| Tecnologia        | Aplicação                                           |
| ----------------- | --------------------------------------------------- |
| Java 25           | Linguagem de programação                            |
| Spring Boot 4.1.2 | Framework para desenvolvimento da API               |
| Spring Web MVC    | Construção dos endpoints REST                       |
| Spring Data JPA   | Persistência e acesso a dados                       |
| MySQL             | Banco de dados relacional                           |
| Hibernate         | Mapeamento objeto-relacional (ORM)                  |
| MapStruct 1.6.3   | Mapeamento entre objetos de domínio e entidades JPA |
| Lombok            | Redução de código boilerplate                       |
| Bean Validation   | Validação de dados de entrada                       |
| Maven             | Gerenciamento de dependências e build               |

## Arquitetura

O TaskFlow utiliza **Arquitetura Hexagonal**, também conhecida como Ports and Adapters.

Essa abordagem busca manter o domínio isolado de detalhes externos, como banco de dados, frameworks e interfaces de comunicação.

A aplicação está organizada em quatro camadas principais:

- **Domain:** contém os modelos, regras de negócio, enumerações e exceções do domínio.
- **Application:** concentra os casos de uso e as portas que definem os contratos de comunicação da aplicação.
- **Adapter In:** responsável por receber requisições externas e encaminhá-las aos casos de uso.
- **Adapter Out:** responsável por implementar a comunicação com recursos externos, como o banco de dados.

### Fluxo de comunicação

```text
        Cliente HTTP
             |
             v
      Adapter In (REST)
             |
             v
       Application
        (Use Cases)
             |
             v
          Domain
             |
             v
      Repository Port
             |
             v
      Adapter Out (JPA)
             |
             v
           MySQL
```

Essa estrutura permite substituir ou modificar componentes externos sem comprometer diretamente as regras de negócio.

## Estrutura do projeto

A organização dos pacotes segue a separação de responsabilidades proposta pela arquitetura hexagonal.

```text
src/
└── main/
    ├── java/
    │   └── com/floridohub/taskflow/
    │       │
    │       ├── domain/
    │       │   ├── model/
    │       │   │   └── Task.java
    │       │   ├── utils/
    │       │   │   ├── TaskPriority.java
    │       │   │   └── TaskStatus.java
    │       │   └── exception/
    │       │
    │       ├── application/
    │       │   ├── in/
    │       │   │   └── [Use Cases]
    │       │   ├── out/
    │       │   │   └── TaskRepositoryPort.java
    │       │   └── service/
    │       │       └── TaskService.java
    │       │
    │       ├── adapter/
    │       │   ├── in/
    │       │   │   └── web/
    │       │   │       ├── TaskController.java
    │       │   │       └── dto/
    │       │   │
    │       │   └── out/
    │       │       ├── entity/
    │       │       │   └── TaskEntity.java
    │       │       ├── mapper/
    │       │       │   └── TaskMapper.java
    │       │       └── repository/
    │       │
    │       └── TaskFlowApplication.java
    │
    └── resources/
        └── application.properties

.env.example
.gitignore
mvnw
mvnw.cmd
pom.xml
README.md
```

*Observação: a estrutura acima representa a organização lógica dos componentes. Os nomes e subpacotes devem acompanhar a implementação atual do repositório.*

## Funcionalidades

### Implementadas

- [x] Modelagem da entidade de domínio `Task`.
- [x] Definição dos status de uma tarefa (`PENDING`, `IN_PROGRESS` e `COMPLETED`).
- [x] Definição dos níveis de prioridade.
- [x] Regras de negócio para inicialização, atualização, início e conclusão de tarefas.
- [x] Validação de dados e regras do domínio.
- [x] Definição dos casos de uso da aplicação.
- [x] Implementação do serviço de aplicação (`TaskService`).
- [x] Definição da porta de persistência (`TaskRepositoryPort`).
- [x] Integração com MySQL utilizando Spring Data JPA.
- [x] Implementação do adaptador de persistência.
- [x] Mapeamento entre objetos de domínio e entidades JPA utilizando MapStruct.
- [x] Integração da camada REST com os casos de uso.

### Em desenvolvimento

- [ ] Refinamento e validação dos endpoints REST.
- [ ] Ampliação dos testes automatizados das camadas da aplicação.
- [ ] Tratamento global e padronização das respostas de erro.
- [ ] Aprimoramento da documentação da API.
- [ ] Interface de usuário Web

## Modelo de domínio

A entidade `Task` representa uma tarefa e possui os seguintes atributos:

| Atributo      | Descrição                  |
| ------------- | -------------------------- |
| `id`          | Identificador único (UUID) |
| `title`       | Título da tarefa           |
| `description` | Descrição da tarefa        |
| `status`      | Estado atual da tarefa     |
| `priority`    | Prioridade da tarefa       |
| `dueDate`     | Data limite para conclusão |
| `createdAt`   | Data de criação            |
| `updatedAt`   | Data da última atualização |

### Ciclo de vida de uma tarefa

Uma tarefa possui três estados possíveis:

| Status        | Descrição                          |
| ------------- | ---------------------------------- |
| `PENDING`     | Tarefa criada e ainda não iniciada |
| `IN_PROGRESS` | Tarefa em execução                 |
| `COMPLETED`   | Tarefa concluída                   |

As transições de estado são controladas pelas regras de negócio da entidade de domínio.

## Instalação e execução

### Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

- Java JDK 25 ou superior compatível com o projeto.
- MySQL instalado e em execução.
- Git.
- Maven (opcional, pois o projeto possui Maven Wrapper).

### 1. Clone o repositório

```bash
git clone https://github.com/Florido-hub/TaskFlow.git
```

Entre no diretório:

```bash
cd TaskFlow
```

### 2. Crie o banco de dados

No MySQL, execute:

```sql
CREATE DATABASE taskflow;
```

### 3. Configure as variáveis de ambiente

O projeto utiliza variáveis de ambiente para configurar a conexão com o banco de dados.

Crie seu arquivo `.env` a partir do modelo disponibilizado:

```bash
cp .env.example .env
```

No Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

Configure os seguintes valores:

```env
DB_URL=jdbc:mysql://localhost:3306/taskflow
DB_USERNAME=root
DB_PASSWORD=sua_senha
```

**Importante:** o arquivo `.env` serve como modelo de configuração. O Spring Boot não carrega arquivos `.env` automaticamente. Portanto, configure essas variáveis no ambiente de execução ou na configuração de execução da sua IDE.

Nunca compartilhe ou versione o arquivo `.env` com credenciais reais.

### 4. Execute a aplicação

Utilizando o Maven Wrapper:

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

Após a inicialização, a aplicação estará disponível em:

```text
http://localhost:8080
```

### 5. Execute os testes

Para executar a suíte de testes:

```bash
mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

## API

A API disponibiliza operações para gerenciamento de tarefas por meio de endpoints REST.

O recurso principal é:

```text
/api/tasks
```

| Método | Endpoint                   | Operação                |
| ------ | -------------------------- | ----------------------- |
| POST   | `/api/tasks`               | Criar tarefa            |
| GET    | `/api/tasks`               | Listar tarefas          |
| GET    | `/api/tasks/{id}`          | Consultar tarefa por ID |
| PUT    | `/api/tasks/{id}`          | Atualizar tarefa        |
| PATCH  | `/api/tasks/{id}/start`    | Iniciar tarefa          |
| PATCH  | `/api/tasks/{id}/complete` | Concluir tarefa         |
| DELETE | `/api/tasks/{id}`          | Excluir tarefa          |

*Os endpoints devem ser conferidos com a implementação atual do controller antes da publicação desta documentação.*

## Objetivos de desenvolvimento

Além de implementar uma API funcional, o TaskFlow é um projeto de aprendizado e aplicação prática de conceitos importantes de desenvolvimento backend, incluindo:

- Arquitetura Hexagonal e princípios de Clean Architecture.
- Separação de responsabilidades e inversão de dependência.
- Modelagem de domínio e encapsulamento das regras de negócio.
- Desenvolvimento de APIs REST.
- Persistência de dados com Spring Data JPA e MySQL.
- Mapeamento de objetos com MapStruct.
- Testes automatizados e qualidade de software.

## Autor

**Flórido Diniz**

- GitHub: [@Florido-hub](https://github.com/Florido-hub)
- Repositório: [TaskFlow](https://github.com/Florido-hub/TaskFlow)

---

Desenvolvido como projeto prático de estudos em desenvolvimento backend com Java e Spring Boot.
