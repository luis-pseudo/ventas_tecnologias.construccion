package mx.uv.tcsw.application.port.out;
import mx.uv.tcsw.domain.Venta;
import java.util.Optional;

public interface VentaRepository {
    void guardar(Venta venta);
    Optional<Venta> buscar(String id);
}