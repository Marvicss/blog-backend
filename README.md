# Blog Backend API

Esta é uma API REST desenvolvida em Java e Spring Boot para servir como o backend de uma plataforma de blog. O projeto conta com autenticação stateless por JWT, controle de acesso baseado em perfis de usuário, persistência de dados e otimização de leitura através de cache distribuído.

---

## 🚀 Tecnologias Utilizadas

A stack principal do projeto é composta por:

* **Linguagem:** Java 21
* **Framework Web:** Spring Boot 4.0.5 (Spring MVC)
* **Segurança:** Spring Security com autenticação Stateless por JWT (JSON Web Token)
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados Relacional:** PostgreSQL 15 (armazenamento persistente)
* **Cache Distribuído:** Redis (armazenamento em cache de listagem de posts)
* **Containerização:** Docker & Docker Compose
* **Gerenciador de Dependências:** Maven
* **Auxiliares:** Project Lombok (para redução de código boilerplate)
* **Testes:** JUnit 5, Mockito & MockMvc (testes unitários e de integração)

---

## 🔐 Matriz de Permissões de Segurança

As rotas da API são protegidas de acordo com o método HTTP e as regras (roles) do usuário:

| Método HTTP | Rota | Descrição | Permissão / Role |
| :--- | :--- | :--- | :--- |
| **POST** | `/login/` | Autenticação e geração de token JWT | Público |
| **GET** | `/user/` | Listagem de usuários cadastrados | Público |
| **GET** | `/user/{id}` | Busca de usuário por ID único | Público |
| **POST** | `/user/` | Cadastro de novos usuários | `ADMIN`, `AUTHOR` |
| **GET** | `/posts/` | Listagem de posts (cacheada via Redis) | Público |
| **GET** | `/posts/not-cached` | Listagem de posts (direto do banco PostgreSQL) | Público |
| **GET** | `/posts/{id}` | Busca de post por ID | Público |
| **POST** | `/posts/` | Criação de um novo post | `ADMIN`, `AUTHOR` |

---

## 🛠️ Quick Start (Como Executar)

### Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
* **JDK 21**
* **Docker & Docker Compose**
* **Maven** (opcional, o projeto possui o wrapper `./mvnw`)

### Passo 1: Executar a Infraestrutura (Banco e Redis)
Acesse a pasta raiz do projeto onde está localizado o arquivo `docker-compose.yml` e inicie os containers do PostgreSQL e do Redis rodando o comando:

```bash
docker compose up -d
```

Isso subirá:
* Um banco PostgreSQL na porta `5432` com as credenciais definidas.
* Um servidor Redis na porta `6379` para cache de dados.

### Passo 2: Configurações do Ambiente
As configurações de conexão já estão pré-configuradas no arquivo [application.properties](file:///home/marconi/Documentos/projetos/blog-backend/src/main/resources/application.properties). O segredo de segurança da API está configurado por padrão como `"teste123"`.

### Passo 3: Compilar e Iniciar a Aplicação
Inicie a aplicação utilizando o Maven Wrapper na pasta raiz do projeto:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível no endereço: `http://localhost:8080`

---

## 🧪 Executando a Suíte de Testes

Os testes de unidade e de integração estão divididos em suas respectivas pastas. Para rodar todos os testes automatizados da aplicação e gerar os relatórios, execute:

```bash
./mvnw test
```
