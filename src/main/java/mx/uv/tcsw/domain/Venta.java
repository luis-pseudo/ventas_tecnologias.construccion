package mx.uv.tcsw.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Venta {
    // ID necesario para que el Puerto de Salida y el Adaptador puedan buscar/guardar
    private String id = UUID.randomUUID().toString();
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID de la venta no puede ser nulo o vacío");
        }
        this.id = id;
    }

    public Venta() {
        // Constructor por defecto para crear una venta con un ID generado automáticamente
    }

    public String getId() {
        return id;
    }

    public void agregarPartida(Producto producto, int cantidad) {
        detalles.add(new DetalleVenta(producto, cantidad));
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }
}