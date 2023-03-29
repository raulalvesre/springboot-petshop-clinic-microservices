FROM maven:3.9.0-eclipse-temurin-19-alpine
WORKDIR /home/app
COPY src ./src
COPY pom.xml .
RUN mvn -f pom.xml clean package
COPY ./target/petshop-discovery-server-0.0.1-SNAPSHOT.jar app.jar
RUN apk --no-cache add curl
ENTRYPOINT ["java", "-jar", "app.jar"]
