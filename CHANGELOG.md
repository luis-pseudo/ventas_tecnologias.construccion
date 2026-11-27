# Changelog

## v1.0-rc

### Added
- API REST para productos y ventas.
- Cliente Angular con catálogo, carrito e historial.
- Estados de carga, vacío, éxito y error.
- Interfaz reactiva con validación.
- Manejo normalizado de errores.
- Verificación integrada M13.
- Verificación reproducible del candidato.
- Flujo GitHub Actions.
- Configuración Sonar y reportes de cobertura.

### Changed
- README consolidado para candidato de liberación.
- Contrato OpenAPI alineado con implementación.
- Arquitectura de infraestructura separada del núcleo.

### Fixed
- Validaciones de productos y líneas de venta.
- Errores HTTP normalizados.
- Navegación y estados de pantalla Angular.

### Verification
- `mvn clean test`
- `npm run test:ci`
- `npm run test:cov`
- `npm run build`
- `./scripts/verify-module.sh M13`
- `./scripts/verify-release.sh`