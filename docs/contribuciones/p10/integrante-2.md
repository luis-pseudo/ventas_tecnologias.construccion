# Contribución P10 - Integrante 2

**Nombre:** Diana Pimentel
**Rol:** servicios HTTP tipados, manejo de errores y estado del carrito
**Issue:** #38 · **Rama / PR:** `feature/p10-servicios` / #39
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

8de0893 docs(p10): contribucion integrante 2
206555d feat(cliente): CarritoService con signals y pruebas
9f5c83a feat(cliente): servicios HTTP tipados, modelos y interceptor de errores ApiError

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
cd cliente-angular && npm ci && npm run test:ci   # 22 pruebas en esta etapa del repositorio
```

## Dificultades y cómo se resolvieron

- `app.config.ts` ya existía en `master` (configuración base del Integrante 1), por lo que al copiar mis archivos se sobrescribió. Comparé con `git diff` para confirmar que se conservaban `provideBrowserGlobalErrorListeners()` y `provideRouter(routes)` y que solo se agregaba `provideHttpClient` con el interceptor.
- Mi copia local de `master` tenía cambios sin guardar, así que clone el repositorio de nuevo en una carpeta limpia (`ventas_p10`) para no mezclarlos en el PR.
- Mi trabajo dependía del proyecto Angular del Integrante 1, por lo que esperé a que se mergeara el PR #37 antes de crear mi rama.

## Cierre R04

- Se reemplazó la pantalla provisional de carrito por integración real con CarritoService y VentaService.
- Se añadieron estados de vacío, éxito y error.
- Se agregaron pruebas de la vista de carrito.
- Evidencia:
  - cd cliente-angular && npm run test:ci
  - capturas del carrito vacío y carrito con productos