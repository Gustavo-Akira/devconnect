# Stage 1: Build
FROM gradle:8.14.3-jdk24-ubi-minimal AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: Run
FROM eclipse-temurin:24-jdk
WORKDIR /app

COPY --from=build /app/build/libs/devconnect-*.jar app.jar

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
