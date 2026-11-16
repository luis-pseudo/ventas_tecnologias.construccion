# Cliente Angular - Ventas TCSW (P10)

Cliente web (Angular 21, componentes standalone, signals y formularios reactivos) que consume la API REST
de `tcsw-ventas`: catálogo de productos, carrito y registro/listado de ventas.

## Requisitos

| Herramienta | Versión |
|-------------|---------|
| Node.js     | 20.19+, 22.12+ o 24+ |
| npm         | 10+ |
| Java / Maven (API) | Java 11+ / Maven 3 |

## Comandos (desde `cliente-angular/`)

| Comando | Qué hace |
|---------|----------|
| `npm ci` | Instala las dependencias exactas de `package-lock.json` |
| `npm start` | Servidor de desarrollo en http://localhost:4200 (reenvía `/api` a `localhost:8080`) |
| `npm run build` | Build de producción en `dist/` |
| `npm run test:ci` | Pruebas unitarias y de integración (Vitest, sin navegador) |
| `npm run test:cov` | Pruebas con cobertura (`coverage/cliente-angular/lcov.info`) |

## Ejecución local completa

```bash
# Terminal 1 - API (desde la raíz del repositorio)
mvn spring-boot:run          # http://localhost:8080

# Terminal 2 - cliente
cd cliente-angular
npm ci
npm start                    # http://localhost:4200
```

Recorrido manual: **Catálogo** → registrar producto → *Agregar al carrito* → **Carrito** → *Registrar venta* → **Ventas** (aparece la venta).

Verificación automatizada de todo el recorrido: `./scripts/verify-module.sh M13` (desde la raíz).

## Decisiones técnicas

- **Proxy en lugar de CORS.** `proxy.conf.json` reenvía `/api` al puerto 8080; el navegador ve un solo origen,
  así no se abre CORS en el backend. `API_BASE_URL` (en `core/config/api.config.ts`) es `/api/v1`.
- **Un solo formato de error.** `apiErrorInterceptor` convierte cualquier `HttpErrorResponse` en `ApiError`
  (`code`, `message`, `details`), igual que `ErrorResponse` de la API, más `NETWORK_ERROR` cuando la API no responde.
- **Estado del carrito con signals** (`CarritoService`), sin librerías adicionales.
- **`.npmrc` con `legacy-peer-deps=true`**: evita un fallo de npm 10 (`Cannot read properties of null (reading 'edgesOut')`)
  al resolver peers opcionales de `jsdom`.

## Estructura

```
src/app/
  core/        config (API_BASE_URL), models, interceptors, services
  features/    catalogo, carrito, ventas
```
