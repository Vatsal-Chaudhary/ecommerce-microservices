FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre AS runner
WORKDIR /app

# Install wget for healthchecks
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/target/product-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]