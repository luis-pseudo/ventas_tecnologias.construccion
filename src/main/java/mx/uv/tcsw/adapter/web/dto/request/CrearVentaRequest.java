package mx.uv.tcsw.adapter.web.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CrearVentaRequest {
    @NotEmpty(message = "debe incluir al menos una línea")
    @Valid
    private List<LineaVentaRequest> lineas;

    public List<LineaVentaRequest> getLineas() { return lineas; }
    public void setLineas(List<LineaVentaRequest> lineas) { this.lineas = lineas; }
}