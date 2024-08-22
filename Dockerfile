FROM amazoncorretto:21-alpine

WORKDIR /app

COPY rest/target/rest-0.0.1-SNAPSHOT.jar /app/search.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/search.jar"]