# --------------------------
# Multi-stage Dockerfile for Errand Service
# --------------------------

# --------------------------
# Build Stage
# --------------------------
# Use Maven with JDK 21 to build the project
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory inside container
WORKDIR /app

# Step 1: Copy root POM file (to download dependencies separately)
COPY pom.xml .

# Step 2: Prefetch dependencies (speeds up rebuilds)
RUN mvn dependency:go-offline -B

# Step 3: Copy all source code into container
COPY . .

# Step 4: Build all modules (skip tests for faster CI/CD)
RUN mvn clean package -DskipTests

# --------------------------
# Runtime Stage
# --------------------------
# Use lightweight JRE for runtime (no Maven, smaller image)
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Step 5: Copy the built JAR from build stage
# Assumes the main service produces a JAR in target/ folder
COPY --from=build /app/errand-service/target/errand-service-*.jar app.jar

# Step 6: Expose port the Spring Boot app will run on
EXPOSE 8080

# Step 7: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
