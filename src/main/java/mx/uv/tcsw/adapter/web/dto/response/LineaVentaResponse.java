package mx.uv.tcsw.adapter.web.dto.response;

import java.math.BigDecimal;

public class LineaVentaResponse {
    private final String producto;
    private final int cantidad;
    private final BigDecimal precioUnitario;
    private final BigDecimal subtotal;

    public LineaVentaResponse(String producto, int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
}
