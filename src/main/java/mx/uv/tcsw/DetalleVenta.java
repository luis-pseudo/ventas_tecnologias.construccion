package mx.uv.tcsw;

public class DetalleVenta {
    private Producto producto;
    private int cantidad;

    public DetalleVenta(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.producto.reducirExistencia(cantidad);
    }

    public double getSubtotal() {
        return producto.getPrecio().getValor() * cantidad;
    }
}