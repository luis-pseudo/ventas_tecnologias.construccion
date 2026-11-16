# Contribución P10 - Integrante 3

**Nombre:** Jonathan Daniel Paz Sanchez
**Rol:** catálogo (listado y formulario de alta) y calidad (Sonar)
**Issue:** pendiente de registrar · **Rama / PR:** `feature/p10-catalogo` / [PR #40](https://github.com/luis-pseudo/ventas_tecnologias.construccion/pull/40)
**Revisó el PR:** Integrante 4 - pendiente de revisión
**Revisé el PR de:** Integrante 2 - pendiente de revisión

## Alcance

- Pantalla **Catálogo**: lista de productos con estados de *carga*, *vacío*, *error con reintento* y *éxito*.
- **Formulario reactivo** de alta de producto con validación (`nombre` obligatorio y máx. 100; `precio` obligatorio y ≥ 0.01),
  mensajes por campo, bloqueo del botón mientras se guarda y visualización de los errores `details` que devuelve la API.
- Botón *Agregar al carrito* conectado a `CarritoService`.
- Análisis de calidad con SonarQube (backend y cliente) y atención de hallazgos,
  pendiente para la fase posterior en la rama `docs/p10-sonar`.

## Archivos creados o modificados

- `cliente-angular/src/app/features/catalogo/catalogo.ts` (reemplaza la pantalla provisional)
- `cliente-angular/src/app/features/catalogo/catalogo.html`
- `cliente-angular/src/app/features/catalogo/catalogo.spec.ts`

## Commits

```text
9293cef feat(cliente): catalogo con listado, estados de carga y formulario reactivo
5db2227 docs(p10): contribucion integrante 3
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

El PR del Integrante 2 estaba abierto al iniciar esta etapa, así que validé el
catálogo sobre esa base y después separé la rama para que esta PR incluyera sólo
los tres archivos del catálogo y esta bitácora. La revisión del Integrante 4 y
el análisis Sonar quedan pendientes para las fases indicadas en la guía.
