package mx.uv.tcsw.adapter.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mx.uv.tcsw.adapter.web.dto.request.CrearProductoRequest;
import mx.uv.tcsw.adapter.web.dto.response.ProductoResponse;
import mx.uv.tcsw.adapter.web.mapper.ProductoMapper;
import mx.uv.tcsw.application.ProductoRepository;
import mx.uv.tcsw.application.exception.RecursoNoEncontradoException;
import mx.uv.tcsw.domain.Precio;
import mx.uv.tcsw.domain.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoRestController {
    private final ProductoRepository productos;

    public ProductoRestController(ProductoRepository productos) {
        this.productos = productos;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return productos.obtenerTodosConId().entrySet().stream()
            .map(entry -> ProductoMapper.toResponse(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable String id) {
        Producto producto = productos.buscarPorId(id).orElseThrow(
            () -> new RecursoNoEncontradoException("Producto no encontrado: " + id));
        return ProductoMapper.toResponse(id, producto);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody CrearProductoRequest request) {
        Producto producto = new Producto(request.getNombre(), new Precio(request.getPrecio()));
        String id = UUID.randomUUID().toString();
        productos.guardar(id, producto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(ProductoMapper.toResponse(id, producto));
    }
}
