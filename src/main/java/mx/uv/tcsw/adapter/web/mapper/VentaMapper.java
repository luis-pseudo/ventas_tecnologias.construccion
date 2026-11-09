package mx.uv.tcsw.adapter.web.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import mx.uv.tcsw.adapter.web.dto.response.LineaVentaResponse;
import mx.uv.tcsw.adapter.web.dto.response.VentaResponse;
import mx.uv.tcsw.domain.Venta;

public final class VentaMapper {
    private VentaMapper() { }

    public static VentaResponse toResponse(Venta venta) {
        List<LineaVentaResponse> lineas = venta.getDetalles().stream()
            .map(detalle -> new LineaVentaResponse(
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioHistorico().getValor(),
                dinero(detalle.getSubtotal())))
            .collect(Collectors.toList());
        return new VentaResponse(
            venta.getId(),
            lineas,
            dinero(venta.calcularSubtotal()),
            dinero(venta.calcularTotal()));
    }

    private static BigDecimal dinero(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }
}
