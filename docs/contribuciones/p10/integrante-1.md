# Contribución P10 - Integrante 1

**Nombre:** Emmanuel Perez Tovar (`0Dezzigner`)
**Rol:** base del proyecto y API (listado de ventas, script de verificación)
**Issue:** #36   **Rama**:`feature/p10-base`   **PR:** #37
**Revisó el PR:** Integrante 2 (pendiente de revisión cruzada en PR #37)

## Alcance

Dejar listo el terreno para que el resto del equipo trabaje en paralelo:

1. **API:** el cliente necesita *listar* las ventas y la API sólo tenía `POST` y `GET /{id}`. Se agregó `GET /api/v1/ventas`
   (puerto `VentaRepository.listar()`, adaptadores en memoria y JDBC, controlador y contrato OpenAPI).
2. **Configuración:** se renombró `application.propierties` → `application.properties` (hallazgo pendiente de P09: Spring Boot
   no cargaba el archivo por el nombre mal escrito).
3. **Cliente Angular:** proyecto `cliente-angular/` (Angular 21, standalone, Vitest), proxy `/api` → `localhost:8080`,
   estilos globales, rutas y pantallas provisionales que reemplazan los integrantes 3 y 4.
4. **Verificación:** `scripts/verificar_m13.sh` y `scripts/verify-module.sh M13`.

## Archivos creados o modificados

- `src/main/java/mx/uv/tcsw/application/port/out/VentaRepository.java`
- `src/main/java/mx/uv/tcsw/adapter/memory/InMemoryVentaRepository.java`
- `src/main/java/mx/uv/tcsw/adapter/persistence/postgres/VentaRepositoryJdbc.java`
- `src/main/java/mx/uv/tcsw/adapter/web/VentaRestController.java`
- `src/main/resources/application.properties` (renombrado)
- `docs/api/openapi.yaml`
- `cliente-angular/` (configuración, rutas, estilos, `app.*`, README)
- `scripts/verificar_m13.sh`, `scripts/verify-module.sh`

## Commits

```text
6dcabb6 chore(verify): agrega verificar_m13.sh y verify-module.sh
2b5738b feat(cliente): crea proyecto Angular 21 con proxy a la API, rutas y estilos base
f862bbd feat(api): agrega GET /api/v1/ventas con listar() en el puerto VentaRepository
```

## Pruebas aportadas

- `VentaListadoApiTest.listarDevuelve200ConUnArreglo`: `GET /api/v1/ventas` responde 200 con un arreglo.
- `VentaListadoApiTest.listarIncluyeLaVentaRegistrada`: una venta creada con `POST` aparece en el listado.
- `VentaListadoApiTest.ventaInvalidaNoApareceEnElListado` (caso de error): un `POST` con `lineas: []` devuelve 400 y no cambia el listado.
- `InMemoryVentaRepositoryListarTest`: lista vacía, orden de registro y lista inmodificable.
- `ContratoApiTest` (existente) sigue verificando que las rutas de `openapi.yaml` existen en la API.
- `app.spec.ts`: la navegación principal del cliente.

## Decisiones técnicas

- **Proxy y no CORS:** el navegador sólo habla con `localhost:4200`; `ng serve` reenvía `/api` a la API. No se amplía la superficie
  de la API con orígenes permitidos.
- **`LinkedHashMap` sincronizado** en `InMemoryVentaRepository`: el listado conserva el orden de registro (antes era `HashMap`, sin orden).
- **`listar()` es abstracto en el puerto** (no `default`): el adaptador JDBC también lo implementa, de modo que ninguna implementación
  devuelve una lista vacía "de relleno".

## Comprobación reproducible

```bash
mvn clean test
MIN_TESTS_WEB=1 ./scripts/verify-module.sh M13   # en mi rama sólo existe la base del cliente
```

## Dificultades y cómo se resolvieron

Versión mínima de Node.js para Angular 21: Al ejecutar npm ci && npm run build dentro de cliente-angular/, Angular CLI rechazó la compilación porque la sesión tenía activo Node v20.11.1 y requería mínimo v20.19 o v22.12 (EBADENGINE). Se resolvió instalando y activando Node v22.23.5 (con npm 10.9.9) mediante nvm install 22 && nvm use 22