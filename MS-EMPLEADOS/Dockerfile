# Usamos la misma imagen de Java 21
FROM eclipse-temurin:21-jdk-alpine

VOLUME /tmp

# Copiamos el JAR generado por Maven para este microservicio
COPY target/*.jar app.jar

# Exponemos el puerto de MS-AUTH (asumiendo que es el 8081, verifícalo en tu application.properties)
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar"]