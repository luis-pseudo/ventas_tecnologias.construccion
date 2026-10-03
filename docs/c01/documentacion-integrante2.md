# ADR: Justificación de patrones de diseño

**Integrante:** 2  
**Estado:** Aceptado

## Problema resuelto

El sistema de ventas debe aplicar reglas que pueden cambiar, como descuentos, impuestos y promociones, sin modificar constantemente el flujo principal de una venta. También debe crear objetos válidos y permitir que otros componentes reaccionen a una venta registrada sin quedar directamente acoplados a ella.

## Patrones elegidos

### Strategy

Elegimos **Strategy** porque las reglas de una venta pueden cambiar. Por ejemplo, no siempre se calcula igual un descuento o un impuesto. En vez de llenar la venta de `if` y `else`, cada forma de cálculo queda separada y la venta usa la que corresponde por medio de un contrato común. Así, agregar una nueva regla no obliga a cambiar toda la lógica de la venta.

**Consecuencias:** el código queda más fácil de ampliar y de probar, porque cada regla se revisa por separado. La desventaja es que se agregan más componentes y hay que decidir cuál estrategia usar en cada caso.

### Factory

Elegimos **Factory** porque crear un producto o una venta implica cumplir varias validaciones. La fábrica reúne esa creación en un solo lugar y evita que cada parte del sistema tenga que repetir los mismos pasos. De esta forma, el código que usa los objetos no necesita conocer todos sus detalles internos.

**Consecuencias:** se reduce la duplicación y es más probable que todos los objetos se creen correctamente. Como consecuencia, la fábrica debe mantenerse sencilla y enfocada, porque si empieza a contener demasiadas reglas puede convertirse en otro punto de acoplamiento.

### Observer

Elegimos **Observer** porque una venta registrada puede interesar a varias partes del sistema. Por ejemplo, se puede actualizar el inventario, enviar una notificación o guardar una auditoría. La venta comunica que ocurrió el evento y cada componente interesado reacciona sin que la venta tenga que conocer todos esos detalles.

**Consecuencias:** se pueden agregar nuevos receptores sin modificar la lógica principal y se reduce el acoplamiento entre módulos. La desventaja es que hay que controlar el orden de las notificaciones, los errores y la posibilidad de publicar dos veces el mismo evento.

## Patrones descartados

### Singleton

Se descarta porque introduce una instancia global y estado global compartido. Esto oculta dependencias, dificulta las pruebas y puede provocar que distintos componentes dependan del mismo estado. Evitarlo reduce el acoplamiento y permite reemplazar dependencias de manera controlada.

### Facade

Se descarta una fachada general porque concentraría ventas, catálogo, inventario, notificaciones y persistencia en un único punto. Esto aumentaría el acoplamiento entre módulos y ocultaría sus responsabilidades. Se prefieren contratos pequeños y responsabilidades separadas.

## Verificación del README

La sección de justificación técnica del `README.md` permanece intacta y clara. Esta sección explica el uso de `BigDecimal`, la conservación del precio histórico y las validaciones aplicadas. El presente ADR complementa esa justificación con los patrones elegidos, sus consecuencias y las alternativas descartadas.
