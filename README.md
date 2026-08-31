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
