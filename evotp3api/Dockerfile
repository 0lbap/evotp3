# Stage 1: Build the application
FROM gradle:7.6.0-jdk17-alpine AS build
WORKDIR /app
COPY . /app
RUN gradle build -x test --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine AS run
WORKDIR /app
COPY --from=build /app/build/libs/evotp3api-0.0.1-SNAPSHOT.jar evotp3api.jar
ENTRYPOINT ["java", "-jar", "/app/evotp3api.jar"]
EXPOSE 8000
