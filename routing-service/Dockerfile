FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=/app/target/*.jar

COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
