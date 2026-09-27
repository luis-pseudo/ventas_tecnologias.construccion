# Bitácora de Contribución - Integrante 3

Rol: Autor de Integración en el Dominio / Revisor
Pull Request: PR #13

En esta práctica me encargué de actualizar la entidad `Venta` (`Venta.java`) para integrar los patrones de diseño creados por mis compañeros dentro del modelo de dominio:
1. Integré el patrón Strategy mediante el atributo `PoliticaDescuento` (inicializado por defecto con `SinDescuento`), actualizando el cálculo del total de la venta para que aplique el descuento correspondiente sobre el subtotal.
2. Integré el patrón Observer agregando la lista de observadores (`VentaObserver`) y los métodos para registrarlos y notificarles automáticamente cuando se confirma la venta.
3. Como revisor, verifiqué la integración de las ramas previas en `master` para asegurar que únicamente quedaran las clases del dominio correspondientes a esta práctica y que el proyecto compilara sin conflictos.