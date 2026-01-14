FROM maven:3.9-amazoncorretto-21 AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21-alpine

RUN addgroup -S appgroup && \
    adduser -S -G appgroup appuser

WORKDIR /app

COPY --from=build /app/target/tech-challenge-i-0.0.1-SNAPSHOT.jar app.jar
RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
