# ==============================
# Stage 1: Build con Maven
# ==============================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar POM primero (aprovecha caché de capas)
COPY pom.xml .

# Descargar dependencias (capa cacheable)
RUN mvn dependency:go-offline -B

# Copiar código fuente y compilar
COPY src src
RUN mvn clean package -DskipTests -B

# ==============================
# Stage 2: Runtime con JRE
# ==============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no root para seguridad
RUN addgroup -S iotgroup && adduser -S iotuser -G iotgroup

# Copiar el JAR compilado
COPY --from=build /app/target/iot-backend-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario al usuario no root
RUN chown iotuser:iotuser app.jar

# Cambiar a usuario no root
USER iotuser

# Render inyecta el puerto mediante la variable PORT
# La aplicación respeta SERVER_PORT; mapeamos PORT -> SERVER_PORT
ENV SERVER_PORT=${PORT:-8080}

EXPOSE 8080

# Health check para Docker/Render
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:InitialRAMPercentage=50.0", "-jar", "app.jar"]
