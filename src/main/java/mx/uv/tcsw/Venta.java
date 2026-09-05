package mx.uv.tcsw;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private List<DetalleVenta> detalles = new ArrayList<>();

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
}// TODO: Validar cantidad antes de descontar
