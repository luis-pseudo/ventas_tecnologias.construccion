# Contribución - Integrante 3:

## Fase 1: DTOs de Entrada y Manejo Global de Errores

# PR: 30
## 1. Descripción de la Tarea
Implementación de los contratos de entrada (DTOs) para productos y ventas, junto con el componente centralizado de manejo de excepciones (`GlobalExceptionHandler`) para estandarizar las respuestas de error en toda la API.

## 2. Archivos Implementados / Modificados
- `src/main/java/mx/uv/tcsw/adapter/web/dto/request/CrearProductoRequest.java`
- `src/main/java/mx/uv/tcsw/adapter/web/dto/request/LineaVentaRequest.java`
- `src/main/java/mx/uv/tcsw/adapter/web/dto/request/CrearVentaRequest.java`
- `src/main/java/mx/uv/tcsw/adapter/web/error/ErrorResponse.java`
- `src/main/java/mx/uv/tcsw/adapter/web/error/GlobalExceptionHandler.java`
- `src/main/java/mx/uv/tcsw/adapter/web/error/GlobalExceptionHandlerTest.java`
- `src/main/java/mx/uv/tcsw/adapter/web/dto/request/RequestsValidacionTest.java`



## Fase 2: Pruebas de Integración y Errores

# PR: 32
- **Descripción:** Adición de las pruebas de integración (`ErroresApiTest`).