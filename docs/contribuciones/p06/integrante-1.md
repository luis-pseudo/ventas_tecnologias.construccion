# Bitácora de Contribución - Integrante 1

**Rol:** Autor de Strategy y Documentación (ADR) / Revisor
**Issue atendido:** #1 (Implementar patrón Strategy y documentar decisiones)
**Pull Request:** PR #1

Para esta práctica me encargué de dos partes clave. En el código, implementé el patrón Strategy (`PoliticaDescuento`, `SinDescuento` y `DescuentoPorVolumen`) para resolver la variación de descuentos sin llenar de condicionales la entidad principal. Por otro lado, asumí la responsabilidad de documentar la entrega en el `README.md`. Ahí redacté el archivo ADR detallando las alternativas descartadas y el análisis crítico, explicando por qué decidimos no usar Singleton (ya que el estado global rompe las pruebas unitarias) ni Facade (porque aún no hay subsistemas complejos).
