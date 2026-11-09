package mx.uv.tcsw.adapter.web.mapper;

import mx.uv.tcsw.adapter.web.dto.response.ProductoResponse;
import mx.uv.tcsw.domain.Producto;

public final class ProductoMapper {
    private ProductoMapper() { }

    public static ProductoResponse toResponse(String id, Producto producto) {
        return new ProductoResponse(id, producto.getNombre(), producto.getPrecio().getValor());
    }
}
