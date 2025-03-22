# Build stage
FROM gradle:7.6-jdk17 AS build
WORKDIR /app 
COPY . .
RUN gradle build --no-daemon -x test
RUN ls -la build/libs/

# Final image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "/app/app.jar"]