FROM openjdk:8-jdk-alpine

LABEL maintainer="damiane@interia.eu"

EXPOSE 8082

ARG JAR_FILE=target/market-component-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} market-component-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/market-component-0.0.1-SNAPSHOT.jar"]