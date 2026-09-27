package mx.uv.tcsw.application.port.in;
import mx.uv.tcsw.domain.Venta;

public interface RegistrarVentaUseCase {
    Venta registrar(String id);
}