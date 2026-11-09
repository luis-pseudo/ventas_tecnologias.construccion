package mx.uv.tcsw.application.port.in;

public final class LineaVentaComando {
    private final String productoId;
    private final int cantidad;

    public LineaVentaComando(String productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public String getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
}