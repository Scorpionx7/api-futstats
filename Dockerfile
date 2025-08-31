FROM ubuntu:latest
LABEL authors="Esther"

ENTRYPOINT ["top", "-b"]

# --- Estágio 1: O Construtor (Builder) ---

FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker.
COPY pom.xml .

# Baixa todas as dependências do projeto.
RUN mvn dependency:go-offline

# Copia o resto do código-fonte.
COPY src ./src

# Roda o mesmo comando do  CI: compila, testa e empacota o .jar.
RUN mvn -B package -DskipTests

# --- Estágio 2: O Executor (Runner) ---

FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho.
WORKDIR /app

# Copia APENAS o .jar final do estágio 'builder' para dentro da imagem final.
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta 8080 para que o mundo externo possa acessar API.
EXPOSE 8080

# Comando que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]