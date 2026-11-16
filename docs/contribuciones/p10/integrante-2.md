# Contribución P10 - Integrante 2

**Nombre:** Diana Pimentel
**Rol:** servicios HTTP tipados, manejo de errores y estado del carrito
**Issue:** #38· **Rama / PR:** `feature/p10-servicios` / #
**Revisó el PR:** Integrante 3 (DN242005) - Pendiente de revision
**Revisé el PR de:** Integrante 1 (0Dezzigner) - https://github.com/luis-pseudo/ventas_tecnologias.construccion/pull/37

## Alcance

Capa de acceso a la API del cliente Angular, sin dependencias de pantallas:

- Modelos tipados que reflejan los DTO de la API (`Producto`, `Venta`, solicitudes y `ApiError`).
- `ProductoService` y `VentaService` (`HttpClient`, URL base inyectable `API_BASE_URL`).
- `apiErrorInterceptor`: convierte cualquier error HTTP al formato `ApiError` (`code`, `message`, `details`) y distingue
  `NETWORK_ERROR` (API caída) de errores de validación (400) y de negocio (404, 422).
- `CarritoService`: estado del carrito con *signals* (agregar, cambiar cantidad, quitar, total, solicitud de venta).

## Archivos creados o modificados

- `cliente-angular/src/app/app.config.ts` (registra `provideHttpClient` con el interceptor)
- `cliente-angular/src/app/core/config/api.config.ts`
- `cliente-angular/src/app/core/models/{producto,venta,api-error}.model.ts`
- `cliente-angular/src/app/core/interceptors/api-error.interceptor.ts`
- `cliente-angular/src/app/core/services/{producto,venta,carrito}.service.ts`
- Pruebas: `api-error.model.spec.ts`, `producto.service.spec.ts`, `venta.service.spec.ts`, `carrito.service.spec.ts`

## Commits

<pegar la salida de `git log --oneline origin/master..HEAD`>

## Pruebas aportadas (19 casos)

- Servicios con `HttpTestingController`: método, URL y cuerpo de cada petición contra el contrato de la API.
- **Casos de error:** 400 `VALIDATION_ERROR` con `details`, 404 `NOT_FOUND` al registrar una venta con producto inexistente,
  API caída (`NETWORK_ERROR`) y cuerpo no reconocible (`UNKNOWN`, sin filtrar el contenido).
- Carrito: acumulación de cantidades, total redondeado a centavos, cantidad menor a 1, cantidades inválidas rechazadas.

## Decisiones técnicas

- Los componentes nunca ven `HttpErrorResponse`: el interceptor normaliza el error una sola vez (principio de responsabilidad única).
- URL base inyectable (`InjectionToken`): se puede cambiar por entorno sin tocar los servicios.
- `encodeURIComponent` en los identificadores de ruta para evitar peticiones a rutas no previstas.

## Comprobación reproducible

```bash
cd cliente-angular && npm ci && npm run test:ci   # 20 pruebas en esta etapa del repositorio
```

## Dificultades y cómo se resolvieron

<escribir con tus palabras lo que realmente te pasó>
