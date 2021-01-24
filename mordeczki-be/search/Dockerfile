FROM openjdk:11-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","target/mordeczki-search.jar"]