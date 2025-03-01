FROM openjdk:21-jdk-slim

WORKDIR /usr/src/app
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]