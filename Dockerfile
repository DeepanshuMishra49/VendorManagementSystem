# 🔧 Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (to leverage Docker layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy the source code
COPY src ./src

# Build the project (skip tests for faster build)
RUN mvn clean package -DskipTests

# 🚀 Stage 2: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/vendor-management-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render looks for port 8080 by default)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]
