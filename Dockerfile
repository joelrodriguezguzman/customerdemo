# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/customer-0.0.1-SNAPSHOT.jar /app/customer-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]
