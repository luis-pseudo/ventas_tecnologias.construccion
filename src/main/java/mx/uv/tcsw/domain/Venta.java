package mx.uv.tcsw.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Venta {
    // ID necesario para que el Puerto de Salida y el Adaptador puedan buscar/guardar
    private final String id = UUID.randomUUID().toString();
    private List<DetalleVenta> detalles = new ArrayList<>();

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