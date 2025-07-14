FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY ./build/libs/devconnect-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]