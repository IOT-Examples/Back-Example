# IoT Backend — REST API

Backend REST en Spring Boot que recibe, valida y persiste los promedios de telemetría calculados por el Edge (FastAPI).

## Stack Tecnológico

- **Java 21 LTS**
- **Spring Boot 4.1.1**
- **Spring Web MVC**
- **Spring Data JPA**
- **Spring Boot Validation**
- **PostgreSQL 15+**
- **Spring Boot Actuator**
- **springdoc-openapi (Swagger UI)**
- **Maven 3.9+**

## Estructura del Proyecto

```
back/
├── src/main/java/com/iot/back/
│   ├── BackApplication.java          # Punto de entrada
│   ├── controller/
│   │   └── TelemetryAverageController.java   # Endpoints REST
│   ├── dto/
│   │   ├── TelemetryAverageRequestDto.java   # DTO de entrada con validaciones
│   │   └── TelemetryAverageResponseDto.java  # DTO de salida
│   ├── entity/
│   │   └── TelemetryAverage.java             # Entidad JPA mapeada a PostgreSQL
│   ├── exception/
│   │   └── GlobalExceptionHandler.java      # Manejador global de errores
│   ├── repository/
│   │   └── TelemetryAverageRepository.java   # Repositorio Spring Data JPA
│   └── service/
│       └── TelemetryAverageService.java      # Lógica de negocio
├── src/main/resources/
│   └── application.yaml                      # Configuración con variables de entorno
├── pom.xml
└── .env.example                              # Plantilla de variables de entorno
```

## Configuración de PostgreSQL

### 1. Instalar PostgreSQL

Si no lo tienes instalado:

```bash
# Windows ( Chocolatey )
choco install postgresql

# macOS ( Homebrew )
brew install postgresql

# Linux ( Ubuntu/Debian )
sudo apt install postgresql postgresql-contrib
```

### 2. Crear la base de datos y el usuario

Accede a PostgreSQL y ejecuta:

```sql
CREATE USER iot_user WITH PASSWORD 'iot_password';
CREATE DATABASE iot_telemetry OWNER iot_user;
GRANT ALL PRIVILEGES ON DATABASE iot_telemetry TO iot_user;
```

O de forma rápida por consola:

```bash
sudo -u postgres psql -c "CREATE USER iot_user WITH PASSWORD 'iot_password';"
sudo -u postgres psql -c "CREATE DATABASE iot_telemetry OWNER iot_user;"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE iot_telemetry TO iot_user;"
```

## Configuración de Variables de Entorno

Las credenciales se configuran mediante variables de entorno. **Nunca guardes secretos en el repositorio.**

```bash
# Windows (PowerShell)
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="iot_telemetry"
$env:DB_USERNAME="iot_user"
$env:DB_PASSWORD="iot_password"
$env:SERVER_PORT="8080"

# Linux/macOS
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=iot_telemetry
export DB_USERNAME=iot_user
export DB_PASSWORD=iot_password
export SERVER_PORT=8080
```

Copia `.env.example` como `.env` y ajusta los valores.

## Ejecución Local

### Prerrequisitos

- Java 21 LTS instalado
- Maven 3.9+ instalado
- PostgreSQL corriendo

### Compilar y ejecutar

```bash
cd back

# Compilar
mvn clean package

# Ejecutar
mvn spring-boot:run

# O ejecutar el JAR generado
java -jar target/iot-backend-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en `http://localhost:8080`.

## Documentación Interactiva (Swagger UI)

La aplicación incluye Swagger UI para probar los endpoints de forma interactiva:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

Desde la interfaz web puedes:
- Ver todos los endpoints documentados
- Probar el `POST /api/v1/telemetry/averages` directamente desde el navegador
- Ver los ejemplos de respuesta y los esquemas de datos
- Probar el `GET /api/v1/telemetry/averages` con paginación

## Endpoints

### 1. Registrar promedio (POST)

```http
POST /api/v1/telemetry/averages
Content-Type: application/json

{
  "device_id": "esp32_001",
  "samples": 5,
  "average_temperature": 44.62,
  "average_humidity": 46.80
}
```

**Respuesta exitosa (HTTP 201):**

```json
{
  "id": 1,
  "device_id": "esp32_001",
  "samples": 5,
  "average_temperature": 44.62,
  "average_humidity": 46.80,
  "received_at": "2026-10-15T10:30:00Z"
}
```

### 2. Listar registros (GET)

```http
GET /api/v1/telemetry/averages?page=0&size=20
```

**Respuesta exitosa (HTTP 200):**

```json
{
  "content": [
    {
      "id": 1,
      "device_id": "esp32_001",
      "samples": 5,
      "average_temperature": 44.62,
      "average_humidity": 46.80,
      "received_at": "2026-10-15T10:30:00Z"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": { "empty": true, "sorted": false, "unsorted": true },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 20,
  "number": 0,
  "sort": { "empty": true, "sorted": false, "unsorted": true },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

### 3. Health Check (GET)

```http
GET /actuator/health
```

**Respuesta exitosa (HTTP 200):**

```json
{
  "status": "UP"
}
```

## Pruebas con curl

### Enviar un promedio

```bash
curl -X POST http://localhost:8080/api/v1/telemetry/averages \
  -H "Content-Type: application/json" \
  -d '{
    "device_id": "esp32_001",
    "samples": 5,
    "average_temperature": 44.62,
    "average_humidity": 46.80
  }'
```

### Listar registros

```bash
curl http://localhost:8080/api/v1/telemetry/averages
```

### Health check

```bash
curl http://localhost:8080/actuator/health
```

### Probar validación (debe devolver 400)

```bash
curl -X POST http://localhost:8080/api/v1/telemetry/averages \
  -H "Content-Type: application/json" \
  -d '{
    "device_id": "",
    "samples": 0,
    "average_temperature": 100,
    "average_humidity": -10
  }'
```

## Validaciones

| Campo | Regla | Mensaje de error |
|-------|-------|------------------|
| `device_id` | Obligatorio, no vacío | `device_id es obligatorio y no puede estar vacío` |
| `samples` | ≥ 1 | `samples debe ser mayor o igual a 1` |
| `average_temperature` | -40 a 80 °C | `Temperatura mínima/máxima aceptada` |
| `average_humidity` | 0 a 100 % | `Humedad mínima/máxima aceptada` |

Los valores no finitos (NaN, Infinity) son rechazados automáticamente por Jackson.

## Integración Futura con el Edge (FastAPI)

El servicio FastAPI en Render puede enviar un POST al endpoint de recepción:

```python
import httpx

BACKEND_URL = "https://tu-backend.com"  # No usar localhost:8080

async def send_average(device_id: str, samples: int, avg_temp: float, avg_hum: float):
    async with httpx.AsyncClient() as client:
        response = await client.post(
            f"{BACKEND_URL}/api/v1/telemetry/averages",
            json={
                "device_id": device_id,
                "samples": samples,
                "average_temperature": avg_temp,
                "average_humidity": avg_hum
            },
            timeout=10.0
        )
        response.raise_for_status()
        return response.json()
```

**Importante:** El backend debe estar accesible desde Render. Opciones:

1. **Túnel con ngrok (desarrollo):** `ngrok http 8080` — genera una URL pública temporal.
2. **Despliegue en la nube:** Railway, Render, AWS, GCP, etc.
3. **Red privada:** Si ambos servicios están en la misma red, usar la IP interna.

## Próximos Pasos (No implementados)

- [ ] Autenticación/autorización (API Key o JWT)
- [ ] Filtros por dispositivo y rango de fechas
- [ ] Agregaciones estadísticas (promedio, mín, máx por dispositivo)
- [ ] WebSocket para notificaciones en tiempo real
- [ ] Docker + docker-compose para despliegue simplificado

## Licencia

MIT
