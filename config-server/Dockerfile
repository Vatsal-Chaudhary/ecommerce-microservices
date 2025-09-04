FROM maven:3.9.9-zulu-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


FROM azul/zulu-openjdk-distroless:21 AS runner
WORKDIR /app

COPY --from=builder /app/target/user-service-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8888

CMD ["-jar", "/app.jar"]
