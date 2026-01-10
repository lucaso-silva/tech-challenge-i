# Build stage using Maven wrapper with Amazon Corretto 21
FROM maven:3.9-amazoncorretto-21 AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make wrapper executable and download dependencies
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage using smaller Amazon Corretto 21 Alpine image
FROM amazoncorretto:21-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/tech-challenge-i-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
