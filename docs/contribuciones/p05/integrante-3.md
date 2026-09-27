# Bitácora de Contribución - Integrante 3



**Rol:** Autor de Pruebas de Arquitectura (ArchUnit)

**Issue atendido:** #3 (Verificación de dependencias con ArchUnit)

**Pull Request:** PR #3



Mi aportación principal fue asegurar que realmente estuviéramos respetando la arquitectura mediante pruebas automatizadas. Configuré ArchUnit para validar por código que las clases del paquete `domain` no dependieran de `application`, `adapter` ni de cosas externas como interfaces gráficas o persistencia. Al principio la prueba falló porque teníamos un import colado por ahí, pero lo corregí rápido para que el test pasara en verde. También revisé el PR de la documentación para comprobar que el diagrama de dependencias reflejara exactamente la estructura que acabábamos de programar.
