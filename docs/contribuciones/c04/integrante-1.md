# Contribución C04 - Integrante 1

**Nombre:** Diana Pimentel  
**Rol:** integración final y candidato reproducible  
**Rama:** feature/c04-integracion  
**Revisó el PR:** Integrante 2  
**Revisé el PR de:** Integrante 4

## Cambios realizados
- Se creó el flujo reproducible `scripts/verify-release.sh`.
- Se verificó el recorrido backend + Angular + proxy.
- Se verificó el empaquetado Maven y Angular.
- Se consolidó el candidato `v1.0-rc`.
- Se reordenó `mvn package` después de `verify-module.sh M13`, porque este ejecuta `mvn clean` y eliminaba el JAR antes de copiarlo.
- Se reemplazó `|| true` por un error explícito para que el script falle si no se encuentra el JAR.

## Pruebas
- `mvn clean test` (53 pruebas del backend).
- `npm run test:ci` (30 pruebas del cliente Angular).
- `./scripts/verify-module.sh M13` (resultado: `MODULO_M13_VERIFICADO`).
- `./scripts/verify-release.sh` (resultado: `MODULO_R05_VERIFICADO`).

## Commits
- `# 23c84cf feat(release): agrega verificacion completa v1.0-rc`
- `# ce9d0b8 docs(c04): agrega contribucion integrante 1
`
