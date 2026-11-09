package mx.uv.tcsw.application.port.in;

import java.util.List;
import mx.uv.tcsw.domain.Venta;

public interface RegistrarVentaUseCase {
    Venta registrar(String id);

    Venta registrar(String id, List<LineaVentaComando> lineas);
}