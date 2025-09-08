# ===== STAGE 1: Build the JAR =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the entire project (parent pom + all modules)
COPY . .

# Build only this service and its dependencies (change module name as needed)
RUN mvn -pl assignment-service -am clean package -DskipTests

# ===== STAGE 2: Run the JAR =====
FROM eclipse-temurin:21-jdk-alpine AS runtime
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/assignment-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
