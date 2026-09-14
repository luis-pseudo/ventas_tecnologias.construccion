package mx.uv.tcsw.application;

import mx.uv.tcsw.Producto;
import java.util.List;

public interface ProductoRepository {
    void guardar(String id, Producto producto);
    Producto buscarPorId(String id);
    List<Producto> obtenerTodos();
}