# Reporte de Actividades - Integrante 2

*Rol:* Desarrollo frontend (Swing y MVC)

## Actividades realizadas

- Se construyó `VentaView` con componentes de `javax.swing` para capturar el
  identificador del cliente y mostrar el resultado de la operación.
- Se implementó `VentaController` para conectar los eventos de la vista con el
  puerto de entrada `RegistrarVentaUseCase`, manteniendo la lógica de negocio
  fuera de la interfaz gráfica.
- Se añadió el punto de entrada `mx.uv.tcsw.ui.swing.Main`, que ensambla el
  repositorio, el servicio de aplicación, el controlador y la vista.
- Se agregaron notificaciones mediante `JOptionPane` para informar el éxito o
  el fallo de la transacción.

| Actividad | Archivos modificados | Issue asociado |
| :--- | :--- | :--- |
| Implementación de la vista | `VentaView.java` | #20 |
| Implementación del controlador | `VentaController.java` | #20 |
| Inyección de dependencias y arranque | `Main.java` | #20 |