FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copia o jar gerado localmente (ajuste o nome se necessário)
COPY target/devconnect.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]