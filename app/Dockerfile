# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Run mvn clean install and package to make changes visible
RUN mvn clean install -DskipTests && mvn package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/carwars-0.0.1.jar /app/carwars-0.0.1.jar

# Install rsync for live reload and other dependencies
RUN apt-get update && apt-get install -y rsync && rm -rf /var/lib/apt/lists/*

# Expose the application port
EXPOSE 8080

# Start the Spring Boot application with devtools enabled for live reload
ENTRYPOINT ["java", "-Dspring.devtools.restart.enabled=true", "-Dspring.devtools.livereload.enabled=true", "-jar", "/app/carwars-0.0.1.jar"]
