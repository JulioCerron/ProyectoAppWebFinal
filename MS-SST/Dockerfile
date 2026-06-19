FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
# Exponemos el puerto oficial de SST 8084
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]