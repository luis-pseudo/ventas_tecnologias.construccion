# Bitácora de Contribución - Integrante 1

**Rol:** Autor de Puertos y Dominio / Revisor
**Issue atendido:** #1 (Definición del núcleo y puertos)
**Pull Request:** PR #1

Para esta práctica me enfoqué en armar el núcleo de la arquitectura hexagonal. Primero creé la clase `Venta` asegurándome de que no tuviera ninguna dependencia externa, pura lógica de dominio. Después definí el puerto de entrada (`RegistrarVentaUseCase`) y el de salida (`VentaRepository`) para aislar completamente la base de datos de nuestras reglas. Abrí mi PR y, tras los comentarios de mi compañero, ajusté unas validaciones del contrato. También apoyé revisando el código del adaptador en memoria para verificar que no estuviera metiendo clases de infraestructura o anotaciones extrañas dentro de nuestro dominio.