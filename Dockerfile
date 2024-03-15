FROM openjdk:17-alpine

COPY build/libs/Weather-0.0.1-SNAPSHOT-all.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]