package mx.uv.tcsw;

public class DetalleVenta {
    private final Producto producto;
    private final int cantidad;
    private final Precio precioHistorico;

    public DetalleVenta(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        // Se guarda el precio al momento de la venta (histórico)
        this.precioHistorico = producto.getPrecio();
    }

    public int getCantidad() { return cantidad; }
    public Precio getPrecioHistorico() { return precioHistorico; }
    public double getSubtotal(){
        return (double) cantidad * producto.getPrecio().getValor().doubleValue();
    }
}