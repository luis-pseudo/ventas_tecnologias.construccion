# Guía de Reproducción - P02 Venta en Memoria

## Herramientas y versiones
* Java 11 (SDKMAN)
* Maven
* JUnit 4.13.2

## Comandos para reproducir
1. Ejecutar pruebas automatizadas: `mvn clean test`
2. Verificación de módulo: `./scripts/verify-module.sh M02`

## Calidad y Sonar
* **Estado:** PENDIENTE. 
* **Justificación:** Fallo crítico en la red de Docker (módulo veth) en Arch Linux minutos antes del cierre impidió levantar el contenedor de SonarQube.

## Decisiones Técnicas
* Se implementaron Objetos de Valor (Precio) e Invariantes para proteger el estado de Producto y DetalleVenta.

# Evidencia P03 - Ramas y Calidad

## URL / Identificador
Identificador del commit final: (Pega aquí el hash de tu último commit, ej. a1b2c3d)

## Guía de reproducción
- **Herramientas:** Java 11, Maven 3, Git, SonarQube (Docker)[cite: 4, 5].
- **Comandos de pruebas:** `mvn clean test` y `./scripts/verify-module.sh M03`[cite: 1].
- **Comando de calidad:** `mvn clean verify sonar:sonar -Dsonar.projectKey=tcsw-ventas -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<TOKEN>`

## Justificación técnica (Saber teórico y analítico)
Se identificó que el modelo dependía de valores mutables de precisión limitada y no conservaba el precio histórico. 
Para resolverlo:
1. Se refactorizó `Precio` usando `BigDecimal` inmutable y se validaron entradas nulas o negativas.
2. `DetalleVenta` ahora captura el estado del `Precio` en el momento de creación, protegiendo el historial ante cambios futuros del catálogo.
3. Se provocó un conflicto en Git al modificar concurrentemente validaciones en `Venta.java`, el cual se resolvió manualmente preservando ambas invariantes (rechazo de nulos y cantidades negativas) sin pérdida funcional.