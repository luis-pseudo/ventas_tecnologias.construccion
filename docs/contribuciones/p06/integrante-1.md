# Bitácora de Contribución - Integrante 1

**Rol:** Autor de Strategy / Revisor
**Issue atendido:** #1 (Implementar patrón Strategy para variación de descuentos)
**Pull Request:** PR #1

Para esta práctica me enfoqué en resolver el problema de cómo íbamos a manejar los diferentes tipos de descuento. Si dejábamos todo dentro de la clase `Venta` con puros `if/else`, el código se iba a volver muy difícil de mantener y probar. Por eso decidí aplicar el patrón Strategy. Creé la interfaz `PoliticaDescuento` y dos clases concretas (`SinDescuento` y `DescuentoPorVolumen`). Con esto, ahora podemos cambiar la regla de los descuentos en tiempo de ejecución sin tener que modificar la entidad principal, evitando la sobreingeniería. Además, revisé el PR de la fábrica de mi compañero para comprobar que no se nos estuviera pasando ninguna validación al momento de crear los objetos.
