FROM maven:3.6-openjdk-11-slim

#RUN addgroup -S spring && adduser -S spring -G spring

#USER spring:spring


ADD . search
WORKDIR search

#RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["bash", "start.sh"]


