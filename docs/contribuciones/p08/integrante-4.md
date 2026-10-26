# Bitácora de Contribución - Integrante 4

**Actividad:** P08 - PostgreSQL y arquitectura hexagonal

## Resumen de actividades

* Implementé `Main.java` como punto de entrada de la aplicación.
* Conecté el caso de uso `RegistrarVentaUseCase` con `VentaRepositoryJdbc` mediante `RegistrarVentaService`.
* Dejé preparado el flujo de extremo a extremo con PostgreSQL en Docker para ejecutarlo cuando el adaptador JDBC de Integrante 2 esté integrado.

## Evidencia

* Punto de entrada: `src/main/java/mx/uv/tcsw/Main.java`.
* Infraestructura utilizada: `docker-compose.yml`.
* Comandos de validación:

```bash
docker compose up -d
docker exec -it ventas_postgres_16 psql -U postgres -d ventas_db -c "\dt"
mvn clean compile
mvn exec:java -Dexec.mainClass="mx.uv.tcsw.Main"
```

El flujo imprimirá el identificador de la venta registrada y permitirá verificar la tabla
`ventas` directamente en PostgreSQL. La ejecución queda pendiente de la integración de
`VentaRepositoryJdbc`, que es responsabilidad del Integrante 2.
