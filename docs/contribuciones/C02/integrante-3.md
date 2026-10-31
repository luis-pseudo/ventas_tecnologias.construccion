# Reporte de Actividades - Integrante 3
**Rol:** Persistencia y Control Transaccional

## Actividades Realizadas
- **Persistencia con Hibernate:** Se agregaron las dependencias requeridas en el `pom.xml`, se configuró la conexión a PostgreSQL mediante `hibernate.cfg.xml` y se mapeó la entidad `Venta` utilizando JPA (Issue #21).
- **Adaptador de Base de Datos:** Se implementó `VentaRepositoryHibernate` para la gestión y apertura de sesiones con la base de datos (Issue #21).
- **Control Transaccional:** Se modificó `RegistrarVentaService` para asegurar la consistencia de los datos, implementando un bloque `try-catch` con control manual de transacciones que ejecuta un `rollback` automático ante cualquier fallo (Issue #22).

## Tabla de Trazabilidad

| Actividad | Archivos Modificados | Issue Asociado |
| :--- | :--- | :--- |
| Configuración Hibernate | `pom.xml`, `hibernate.cfg.xml`, `Venta.java` | #21 |
| Adaptador Hibernate | `VentaRepositoryHibernate.java` | #21 |
| Rollback y Transacciones | `RegistrarVentaService.java` | #22 |