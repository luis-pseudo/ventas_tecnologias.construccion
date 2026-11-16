# Contribución P10 - Integrante 3

**Nombre:** Jonathan Daniel Paz Sanchez
**Rol:** catálogo (listado y formulario de alta) y calidad (Sonar)
**Issue:** pendiente de registrar · **Rama / PR:** `feature/p10-catalogo` / pendiente de abrir
**Revisó el PR:** Integrante 4 - pendiente de revisión
**Revisé el PR de:** Integrante 2 - pendiente de revisión

## Alcance

- Pantalla **Catálogo**: lista de productos con estados de *carga*, *vacío*, *error con reintento* y *éxito*.
- **Formulario reactivo** de alta de producto con validación (`nombre` obligatorio y máx. 100; `precio` obligatorio y ≥ 0.01),
  mensajes por campo, bloqueo del botón mientras se guarda y visualización de los errores `details` que devuelve la API.
- Botón *Agregar al carrito* conectado a `CarritoService`.
- Análisis de calidad con SonarQube (backend y cliente) y atención de hallazgos.

## Archivos creados o modificados

- `cliente-angular/src/app/features/catalogo/catalogo.ts` (reemplaza la pantalla provisional)
- `cliente-angular/src/app/features/catalogo/catalogo.html`
- `cliente-angular/src/app/features/catalogo/catalogo.spec.ts`
- `cliente-angular/sonar-project.properties`
- `docs/p10/sonar.md`

## Commits

```text
ae43d2d feat(cliente): catalogo con listado, estados de carga y formulario reactivo
```

## Pruebas aportadas (7 casos)

- Estado de carga y lista; estado vacío; error de API con botón *Reintentar*.
- **Negativo:** formulario inválido no genera `POST` y muestra los mensajes de campo.
- **Negativo:** error 400 de la API se muestra con el detalle por campo y no agrega el producto.
- Positivo: alta válida (el nombre se recorta), se agrega a la lista y se limpia el formulario.
- Integración con el carrito: *Agregar al carrito* incrementa las unidades de `CarritoService`.

## Decisiones técnicas

- `ReactiveFormsModule` con `FormGroup` tipado: la validación vive en el modelo del formulario, no en la plantilla.
- Los mensajes de error usan `role="alert"` y los de éxito `role="status"` (accesibles para lectores de pantalla).
- El error se muestra como lo normaliza el interceptor; la pantalla no interpreta códigos HTTP.

## Comprobación reproducible

```bash
cd cliente-angular && npm ci && npm run test:ci   # 27 pruebas en esta etapa del repositorio
```

## Dificultades y cómo se resolvieron

La rama del Integrante 2 todavía estaba abierta al iniciar esta etapa. Para poder
validar el catálogo sin duplicar sus archivos, trabajé temporalmente sobre su
rama y dejé en este PR únicamente los tres archivos del catálogo. La integración
final debe hacerse después de que el PR del Integrante 2 se fusione en `master`.
El análisis Sonar queda pendiente para una rama y PR separados, tal como pide la
guía.
