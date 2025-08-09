FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY ./build/libs/devconnect-*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]