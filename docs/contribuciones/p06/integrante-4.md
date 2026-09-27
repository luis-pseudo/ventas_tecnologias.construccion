# Bitácora de Contribución - Integrante 4

*Rol:* Autor de Pruebas (100% Cobertura) y Calidad / Verificación
*Pull Request:* PR #

Mi trabajo consistió exclusivamente en asegurar que todo el código nuevo estuviera respaldado por pruebas unitarias sólidas para alcanzar el 100% de cobertura en JaCoCo. Escribí todos los casos en DominioYPatronesTest.java asegurando que se validaran los límites: que la fábrica rechazara listas nulas o vacías, que el observer notificara correctamente, y que las estrategias calcularan los montos exactos. Finalmente, ejecuté el script ./scripts/verify-module.sh M06 y el análisis de SonarQube para generar las evidencias de calidad previas a nuestro tag.