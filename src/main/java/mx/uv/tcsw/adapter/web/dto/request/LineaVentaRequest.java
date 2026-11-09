package mx.uv.tcsw.adapter.web.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LineaVentaRequest {
    @NotBlank(message = "es obligatorio")
    private String productoId;

    @NotNull(message = "es obligatorio")
    @Min(value = 1, message = "debe ser al menos 1")
    private Integer cantidad;

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}