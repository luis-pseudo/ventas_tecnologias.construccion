# Contribución - Integrante 2: Controladores, respuestas y mappers

## Alcance

Se implementó la capa web REST de P09 sin exponer directamente objetos del
dominio. Las escrituras pasan por los casos de uso correspondientes y las
consultas de productos y ventas usan sus puertos de salida.

## Archivos agregados

- `src/main/java/mx/uv/tcsw/adapter/web/dto/response/ProductoResponse.java`
- `src/main/java/mx/uv/tcsw/adapter/web/dto/response/LineaVentaResponse.java`
- `src/main/java/mx/uv/tcsw/adapter/web/dto/response/VentaResponse.java`
- `src/main/java/mx/uv/tcsw/adapter/web/mapper/ProductoMapper.java`
- `src/main/java/mx/uv/tcsw/adapter/web/mapper/VentaMapper.java`
- `src/main/java/mx/uv/tcsw/adapter/web/ProductoRestController.java`
- `src/main/java/mx/uv/tcsw/adapter/web/VentaRestController.java`
- `src/test/java/mx/uv/tcsw/adapter/web/mapper/VentaMapperTest.java`

## Verificación

- `mvn clean test`
- `./scripts/verificar_m09.sh`
