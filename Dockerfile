# Use uma imagem base que inclui o JDK 1.8 e Maven pré-instalados
FROM maven:3.8.4-openjdk-8 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos de configuração e o arquivo pom.xml para o contêiner
COPY pom.xml .
COPY src ./src

# Compila o projeto com Maven
RUN mvn package -DskipTests

# Fase de execução
FROM openjdk:8-jre-alpine

# Define o diretório de trabalho para a aplicação dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR gerado na fase de compilação para o contêiner
COPY --from=build /app/target/*.jar ./app.jar

# Expondo a porta 8080, se necessário
EXPOSE 8080

# Comando para iniciar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]