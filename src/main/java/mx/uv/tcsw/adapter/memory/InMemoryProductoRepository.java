package mx.uv.tcsw.adapter.memory;

import mx.uv.tcsw.application.ProductoRepository;
import mx.uv.tcsw.domain.Producto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductoRepository implements ProductoRepository {
    private final Map<String, Producto> almacen = new LinkedHashMap<>();

    @Override
    public void guardar(String id, Producto producto) {
        if (id == null || producto == null) throw new IllegalArgumentException("Identificador o producto inválido");
        almacen.put(id, producto);
    }

    @Override
    public Optional<Producto> buscarPorId(String id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<Producto> obtenerTodos() {
        return Collections.unmodifiableList(new ArrayList<>(almacen.values()));
    }

    @Override
    public Map<String, Producto> obtenerTodosConId() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(almacen));
    }
}