package mx.uv.tcsw.adapter.memory;

import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Venta;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryVentaRepository implements VentaRepository {
    private final Map<String, Venta> almacen = new HashMap<>();

    @Override
    public void guardar(Venta venta) {
        almacen.put(venta.getId(), venta);
    }

    @Override
    public Optional<Venta> buscar(String id) {
        return Optional.ofNullable(almacen.get(id));
    }
}