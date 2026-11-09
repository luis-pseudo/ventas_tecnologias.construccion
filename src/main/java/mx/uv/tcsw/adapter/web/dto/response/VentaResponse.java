package mx.uv.tcsw.adapter.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class VentaResponse {
    private final String id;
    private final List<LineaVentaResponse> lineas;
    private final BigDecimal subtotal;
    private final BigDecimal total;

    public VentaResponse(String id, List<LineaVentaResponse> lineas, BigDecimal subtotal, BigDecimal total) {
        this.id = id;
        this.lineas = lineas;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getId() { return id; }
    public List<LineaVentaResponse> getLineas() { return lineas; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTotal() { return total; }
}
