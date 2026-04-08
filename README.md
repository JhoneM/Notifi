# Notifi — Módulo de Notificaciones Multi-Canal

API REST construida con Spring Boot para el envío de notificaciones a través de múltiples canales. El dominio es completamente independiente de Spring (arquitectura hexagonal).

## Requisitos

- Java 21+ (JDK 26 compatible)
- Maven 3.8+

## Cómo correr

```bash
# Compilar
mvn clean package -DskipTests

# Perfil local — usa mocks, sin providers reales
mvn spring-boot:run -Dspring-boot.run.profiles=local 
# Alternativa -> java -jar target/notifi-1.0.0.jar --spring.profiles.active=local 
```

La aplicación queda disponible en `http://localhost:8080`.

## Endpoint

### `POST /api/notifications`

**Request:**
```json
{
  "clientId": "usuario@ejemplo.com",
  "channel": "EMAIL",
  "messageBody": "<h1>Hola</h1>",
  "contentType": "HTML"
}
```

| Campo | Valores válidos |
|---|---|
| `channel` | `EMAIL`, `SMS`, `WHATSAPP` |
| `contentType` | `PLAIN_TEXT`, `HTML` |

**Response exitoso (200):**
```json
{
  "status": "SENT",
  "description": "Notification successfully sent"
}
```

**Response error de validación (400):**
```json
{
  "error": "Bad Request",
  "message": "clientId is required"
}
```

| Código | Cuándo                                                                                                  |
|--------|---------------------------------------------------------------------------------------------------------|
| `200`  | Request válido — revisar `status` (`SENT` o `FAILED`)                                                   |
| `400`  | Campos faltantes, valor de enum inválido, clientId formato inválido para el canal , canal no registrado |
| `500`  | Error interno inesperado                                                                                |

## Ejemplos curl

### Casos exitosos (200 SENT)

**EMAIL con texto plano:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"usuario@ejemplo.com","channel":"EMAIL","messageBody":"Hola, tu pedido fue confirmado.","contentType":"PLAIN_TEXT"}'
```

**EMAIL con HTML:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"usuario@ejemplo.com","channel":"EMAIL","messageBody":"<h1>Bienvenido</h1><p>Tu cuenta fue creada.</p>","contentType":"HTML"}'
```

**SMS:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"+5491112345678","channel":"SMS","messageBody":"Tu código de verificación es 4821.","contentType":"PLAIN_TEXT"}'
```

**WhatsApp:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"+5491112345678","channel":"WHATSAPP","messageBody":"Tu pedido está en camino.","contentType":"PLAIN_TEXT"}'
```

### Errores de validación (400)

**Campo faltante (clientId):**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"channel":"EMAIL","messageBody":"Hola","contentType":"PLAIN_TEXT"}'
```

**ContentType inválido:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"usuario@ejemplo.com","channel":"EMAIL","messageBody":"Hola","contentType":"WORD"}'
```

**ClientId con formato incorrecto para el canal (email esperado):**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"no-es-un-email","channel":"EMAIL","messageBody":"Hola","contentType":"PLAIN_TEXT"}'
```

**ClientId con formato incorrecto para el canal (E.164 esperado):**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"1112345678","channel":"SMS","messageBody":"Hola","contentType":"PLAIN_TEXT"}'
```

**Canal no registrado:**
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"clientId":"usuario@ejemplo.com","channel":"MAIL","messageBody":"Hola","contentType":"PLAIN_TEXT"}'
```

## Perfiles de Spring

| Perfil | Comportamiento |
|---|---|
| `local` | Inyecta mocks — no requiere credenciales externas |
| `prod` | Inyecta stubs reales — requiere configuración de providers en `application-prod.properties` |

## Estructura de packages

```
com.notifi
├── application/     — casos de uso: servicio y DTO de entrada
├── domain/          — modelos, enums y ports (sin dependencia de framework)
├── infrastructure/
│   ├── client/      — interfaces de providers + stubs para prod
│   ├── mock/        — implementaciones mock para perfil local
│   ├── resolver/    — resolución de canal a sender
│   ├── sender/      — adapters por canal (email, sms, whatsapp)
│   └── web/         — controller REST, exception handler, DTOs HTTP
├── config/          — wiring por perfil (local / prod)
└── exception/       — excepciones de dominio
```

---

Patrones de Diseño de Software, UADE.