package mx.uv.tcsw.application;

import mx.uv.tcsw.domain.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
	void guardar(String id, Producto producto);

	Optional<Producto> buscarPorId(String id);

	List<Producto> obtenerTodos();
}
