FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
# Exponemos el puerto 8085
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app.jar"]