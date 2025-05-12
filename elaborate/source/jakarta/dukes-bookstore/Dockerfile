# Use Eclipse Temurin JDK 17 with Maven preinstalled
FROM maven:3.9.6-eclipse-temurin-17

# Set working directory inside container
WORKDIR /app

# Copy everything into the container
COPY . /app

# Expose Open Liberty HTTP port
EXPOSE 9081

# Build the app and run Open Liberty server
CMD ["mvn", "clean", "package", "liberty:run"]
