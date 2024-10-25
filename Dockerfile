FROM maven:3.9.0-eclipse-temurin-19 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:19-jdk-jammy

WORKDIR /app

COPY --from=build /app/target/qrgen-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/qrgen-0.0.1-SNAPSHOT.jar"]