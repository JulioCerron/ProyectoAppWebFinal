# Usamos una imagen ligera de Java 17 (si usas Java 21, cambia el "17" por "21")
# Cambia el 17 por el 21
FROM eclipse-temurin:21-jdk-alpine
# Creamos una carpeta temporal dentro del contenedor
VOLUME /tmp

# Copiamos el archivo JAR generado por Maven al contenedor
COPY target/*.jar app.jar

# Exponemos el puerto por defecto de Eureka
EXPOSE 8761

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]