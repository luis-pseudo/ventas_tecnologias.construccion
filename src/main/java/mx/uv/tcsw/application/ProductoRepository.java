package mx.uv.tcsw.application;

import mx.uv.tcsw.Producto;
import java.util.List;
import java.util.Optional; // Corrección aplicada

public interface ProductoRepository {
    void guardar(String id, Producto producto);
    Optional<Producto> buscarPorId(String id); // Devuelve Optional
    List<Producto> obtenerTodos();
}