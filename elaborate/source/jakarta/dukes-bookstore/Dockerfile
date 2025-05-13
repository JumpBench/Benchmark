# Use Eclipse Temurin JDK 17 with Maven preinstalled
FROM docker.io/maven:3.9.9-eclipse-temurin-24-alpine

# Set working directory inside container
WORKDIR /app

# Copy everything into the container
COPY . /app

# Expose Open Liberty HTTP port
EXPOSE 9081

# Build the app and run Open Liberty server
CMD ["mvn", "clean", "package", "liberty:run"]
