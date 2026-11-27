# Contribución C04 - Integrante 2

**Nombre:** Diana Pimentel
**Rol:** pruebas e integración continua
**Issue:** #[COMPLETAR]
**Rama:** feature/c04-ci
**PR:** #[COMPLETAR]
**Revisó el PR:** Integrante 3
**Revisé el PR de:** Integrante 1

## Alcance

- Se agregó el flujo de GitHub Actions `.github/workflows/ci.yml`.
- El flujo valida el backend con Maven y el frontend Angular con npm.
- Se ejecutan las pruebas backend y frontend en cada push y pull request.
- Se generan reportes de cobertura JaCoCo y Vitest/LCOV.
- Se ejecuta el recorrido integrado `./scripts/verify-module.sh M13`.
- Se publican artefactos de reportes y build para que el candidato sea verificable.

## Archivos creados o modificados

- `.github/workflows/ci.yml`
- `docs/contribuciones/c04/integrante-2.md`

## Trazabilidad

- Issue: #[COMPLETAR]
- Rama: `feature/c04-ci`
- PR: #[COMPLETAR]
- Revisión cruzada: Integrante 3
- PR revisado: Integrante 1

## Verificaciones implementadas

### Backend

```bash
mvn -B clean test
mvn -B jacoco:report
```

### Frontend

```bash
cd cliente-angular
npm ci
CI=true npm run test:ci
npm run test:cov
npm run build
```

### Integración

```bash
./scripts/verify-module.sh M13
```

## Evidencia

Completar al ejecutar GitHub Actions:

- URL de ejecución del workflow: `[COMPLETAR]`
- Resultado backend: `[COMPLETAR]`
- Resultado frontend: `[COMPLETAR]`
- Resultado integración M13: `[COMPLETAR]`
- Artefactos publicados: `backend-reports`, `frontend-build`

## Commits

```text
[COMPLETAR] feat(ci): agrega pipeline R05 con backend, frontend e integracion
[COMPLETAR] docs(c04): agrega contribucion integrante 2
```
