# Stage 1: Build the application
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]