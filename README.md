# FutStats API ⚽️📊

![CI/CD Status](https://github.com/Scorpionx7/api-futstats/actions/workflows/ci-pipeline.yml/badge.svg)

API RESTful para consulta de estatísticas de futebol, construída com Java 21, Spring Boot 3 e as melhores práticas de desenvolvimento backend. Este projeto serve como portfólio e demonstra o uso de diversas tecnologias modernas.

## ✨ Funcionalidades

Atualmente, a API oferece os seguintes endpoints:

* `GET /api/v1/competitions/{id}/standings?season={year}`: Retorna a tabela de classificação completa de um campeonato específico para uma determinada temporada.

*(Futuras funcionalidades planejadas: artilharia, resultados de partidas, estatísticas de jogadores, etc.)*

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.x
    * Spring Web (REST Controllers)
    * Spring Data JPA (Persistência de dados)
    * Spring Security (Autenticação JWT)
    * Spring Cache (Otimização de performance)
    * Spring Scheduled (Tarefas agendadas)
* **Banco de Dados:** PostgreSQL
* **Cache:** Redis
* **Build Tool:** Maven
* **Testes:** JUnit 5, Mockito, Testcontainers
* **Documentação:** Springdoc OpenAPI (Swagger UI)
* **Containerização:** Docker & Docker Compose
* **CI/CD:** GitHub Actions

## ⚙️ Como Executar Localmente

**Pré-requisitos:**
* Java 21 (ou superior) instalado
* Maven instalado
* Docker e Docker Compose instalados

**Passos:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/estherrezendea/api-futstats.git
    cd api-futstats
    ```

2.  **Crie sua chave da API-Football:**
    * Registre-se em [https://www.api-football.com/](https://www.api-football.com/) e obtenha sua chave de API gratuita.

3.  **Configure as variáveis de ambiente:**
    * Crie um arquivo `application.properties` na pasta `src/main/resources/` (se não existir).
    * Adicione as seguintes linhas, substituindo pelos seus valores:
        ```properties
        # Chave da API Externa
        api.football.key=SUA_CHAVE_API_FOOTBALL_AQUI

        # Chave secreta para assinatura dos tokens JWT (crie uma longa e segura)
        api.security.token.secret=sua-chave-secreta-super-longa-e-segura-aqui

        # Configurações do Banco de Dados (padrão do docker-compose)
        spring.datasource.url=jdbc:postgresql://localhost:5432/futstatsdb
        spring.datasource.username=admin
        spring.datasource.password=admin123
        spring.jpa.hibernate.ddl-auto=update

        # Configurações do Redis (padrão do docker-compose)
        spring.data.redis.host=localhost
        spring.data.redis.port=6379
        spring.cache.type=redis
        ```

4.  **Suba o ambiente (Banco de Dados e Cache):**
    ```bash
    docker-compose up -d
    ```

5.  **Compile e execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

A API estará disponível em `http://localhost:8080`.

## 📚 Documentação da API (Swagger)

Com a aplicação rodando, acesse a documentação interativa do Swagger UI no seu navegador:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Lá você pode visualizar todos os endpoints, testá-los e ver os modelos de dados. Lembre-se que os endpoints `/api/**` exigem autenticação JWT. Use o endpoint `/login` (usuário: `testuser`, senha: `password123`) para obter um token.

## ✅ Rodando os Testes

Para executar todos os testes unitários e de integração (que usarão Testcontainers para subir um banco de dados temporário), use o comando:
```bash
mvn test
````

## 🚀 Pipeline de CI/CD

Este projeto utiliza GitHub Actions para Integração Contínua (CI) e Entrega Contínua (CD):

* A cada `push` ou `pull request` para a branch `main`, o pipeline compila o código, roda todos os testes e empacota a aplicação.
* Se o CI for bem-sucedido em um push para `main`, a imagem Docker da aplicação é construída e publicada automaticamente no Docker Hub: `estherrezende/futstats-api:latest`.

-----

Desenvolvido por Esther Rezende ([@estherrezendea](https://github.com/estherrezendea))
