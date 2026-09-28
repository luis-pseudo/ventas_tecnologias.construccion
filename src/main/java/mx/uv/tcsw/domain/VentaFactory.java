package mx.uv.tcsw.domain;

import java.util.List;

public final class VentaFactory {

    // Agregar este constructor privado soluciona el Code Smell y el 100% de cobertura
    private VentaFactory() {
        throw new IllegalStateException("Clase de utilidad, no instanciar");
    }

    public static Venta nueva(String id, List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("No se puede crear una venta sin detalles");
        }
        
        // Uso de 'var' para solucionar la advertencia de la línea 10
        var venta = new Venta(id);
        for (DetalleVenta detalle : detalles) {
            venta.agregarPartida(detalle.getProducto(), detalle.getCantidad());
        }
        return venta;
    }
}