FROM maven:3.6-openjdk-11-slim

#RUN addgroup -S spring && adduser -S spring -G spring

#USER spring:spring


ADD . migration
WORKDIR migration

#RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/mordeczki-migration.jar"]


