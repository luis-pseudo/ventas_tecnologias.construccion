Bitácora de Contribución - Integrante 1
Rol: Responsable de Infraestructura y Base de Datos / Pull Request: PR #

En esta práctica me encargué de configurar el entorno de infraestructura y persistencia inicial para el módulo utilizando PostgreSQL 16 y Docker, asegurando la reproducibilidad del proyecto para todo el equipo:

1. Configuré el servicio de base de datos **PostgreSQL 16** mediante `docker-compose.yml`, estableciendo las credenciales predeterminadas, el mapeo del puerto 5432 y la persistencia de datos mediante volúmenes Docker (`pgdata`).
2. Diseñé y estructuré el script DDL inicial en `scripts/db/01-schema.sql` para definir las tablas relacionales fundamentales del sistema (`productos`, `ventas` y `detalle_venta`).
3. Realicé pruebas locales de levantamiento de contenedores, inicialización limpia (*clean start*) y validación de esquemas mediante `psql` para garantizar que el entorno funcione sin errores para el resto de los integrantes.