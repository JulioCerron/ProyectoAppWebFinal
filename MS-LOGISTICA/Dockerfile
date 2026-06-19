FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
# Exponemos el puerto 8083
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]