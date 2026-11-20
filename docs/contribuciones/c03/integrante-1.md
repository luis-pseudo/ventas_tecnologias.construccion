## Cierre R04

- Se agregó ProductoApiTest con casos de listado, validación y no encontrado.
- Se validó formato uniforme de errores en la API (code, message, details).
- Evidencia:
  - mvn clean test
  - mvn -Dtest=ProductoApiTest,ContratoApiTest,ErroresApiTest test