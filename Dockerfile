FROM openjdk:17-alpine

VOLUME /tmp

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} emailator-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "emailator-0.0.1-SNAPSHOT.jar"]
