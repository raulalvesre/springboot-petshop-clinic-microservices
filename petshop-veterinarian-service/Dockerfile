FROM openjdk:19-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/petshop-veterinarian-service-0.0.1-SNAPSHOT.jar app.jar
RUN apk --no-cache add curl
ENTRYPOINT ["java", "-jar", "/app.jar"]
