# --------------------------
# Build stage
# --------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Step 1: copy parent pom
COPY pom.xml .

# Step 2: prefetch only external dependencies (no submodules yet)
RUN mvn -B dependency:go-offline -N

# Step 3: copy full source (all modules now)
COPY . .

# Step 4: build common-events first (install into local repo)
RUN mvn -pl common-events clean install -DskipTests

# Step 5: build errand-service with all required modules
RUN mvn -pl tracking-service -am clean package -DskipTests

# --------------------------
# Runtime stage
# --------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/tracking-service/target/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]