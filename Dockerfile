# Use the official OpenJDK image for JDK 21
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven executable to the image's filesystem
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml and source code
COPY pom.xml .
COPY src src

# Test and build the application
RUN ./mvnw package

# Extract the built application JAR from the target directory
RUN mv target/*.jar app.jar

# Open port 8080 for external access to your application
EXPOSE 8080

# Command to execute the application
CMD ["java", "-jar", "app.jar"]
