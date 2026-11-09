# Contribución P09 - Integrante 1

**Rol:** contrato y pruebas
**Rama / PR:** feature/p09-base / #29 · fix/p09-compilacion-base / #28

## Archivos creados o modificados

### PR #29 (feature/p09-base)
- scripts/verificar_m09.sh
- src/main/java/mx/uv/tcsw/adapter/memory/InMemoryProductoRepository.java
- src/main/java/mx/uv/tcsw/application/exception/RecursoNoEncontradoException.java
- src/main/java/mx/uv/tcsw/application/port/in/LineaVentaComando.java
- src/main/java/mx/uv/tcsw/application/port/in/RegistrarVentaUseCase.java
- src/main/java/mx/uv/tcsw/application/service/RegistrarVentaService.java
- src/main/java/mx/uv/tcsw/application/ProductoRepository.java
- src/main/java/mx/uv/tcsw/config/BeanConfig.java
- src/main/java/mx/uv/tcsw/domain/Venta.java
- src/main/java/mx/uv/tcsw/TcswApplication.java
- src/main/resources/application.propierties
- src/test/java/mx/uv/tcsw/application/RegistrarVentaConLineasTest.java
- src/test/java/mx/uv/tcsw/architecture/HexagonalTest.java

### PR #28 (fix/p09-compilacion-base)
- src/main/java/mx/uv/tcsw/application/RegistrarVentaService.java (eliminado)
- src/main/java/mx/uv/tcsw/infrastructure/VentaRepositoryHibernate.java (eliminado)
- src/test/java/mx/uv/tcsw/application/RegistrarVentaServiceTest.java (eliminado)
- src/test/java/mx/uv/tcsw/architecture/HexagonalTest.java (activado con ArchUnitRunner)

## Commits
- e620ffa - chore(verify): agrega verificar_m09.sh con pruebas, reglas del nucleo y prueba de humo
- a2302e4 - refactor(domain): quita anotaciones JPA y expone getDetalles
- 2dabec7 - fix: elimina clases y prueba obsoletas de Hibernate; activa HexagonalTest con ArchUnitRunner

## Pruebas aportadas
- registraVentaConLineas: comprueba que se registra una venta con varias líneas correctamente
- productoInexistenteLanzaNoEncontrado: comprueba que un producto inexistente lanza RecursoNoEncontradoException
- ventaSinLineasEsInvalida: comprueba que una venta sin líneas lanza IllegalArgumentException
- cantidadCeroEsInvalida: comprueba que una línea con cantidad 0 lanza IllegalArgumentException
- HexagonalTest.dominio_no_depende_del_exterior: el dominio no depende de capas externas
- HexagonalTest.nucleo_sin_spring: el núcleo no usa clases de Spring
- HexagonalTest.dominio_sin_persistencia: el dominio no usa anotaciones ni clases de persistencia

## Comprobación reproducible
mvn clean test
./scripts/verificar_m09.sh

El script ejecuta las pruebas, las reglas del núcleo y una prueba de humo.

## Dificultades y cómo se resolvieron
- La regla dominio_sin_persistencia fallaba porque el dominio tenía anotaciones JPA. Se quitaron en a2302e4 y se expuso getDetalles para que la capa de aplicación leyera el detalle sin depender del ORM.
- Quedaban clases y una prueba de la implementación anterior con Hibernate que violaban las reglas de arquitectura. Se eliminaron en #28 y se activó HexagonalTest con ArchUnitRunner.
- Hallazgo pendiente: src/main/resources/application.propierties tiene el nombre mal escrito, por lo que Spring Boot no carga su configuración (server.port y server.error.include-message). Debe renombrarse a application.properties.