package mx.uv.tcsw.adapter.memory;

import mx.uv.tcsw.application.ProductoRepository;
import mx.uv.tcsw.Producto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import java.util.ArrayList;

public class InMemoryProductoRepository implements ProductoRepository {
	private final Map<String, Producto> almacen = new HashMap<>();

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
		// Corrección aplicada: Se protege la lista devuelta
		return Collections.unmodifiableList(new ArrayList<>(almacen.values()));
	}
}
