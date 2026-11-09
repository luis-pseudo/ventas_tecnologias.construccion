package mx.uv.tcsw.adapter.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mx.uv.tcsw.adapter.web.dto.request.CrearVentaRequest;
import mx.uv.tcsw.adapter.web.dto.response.VentaResponse;
import mx.uv.tcsw.adapter.web.mapper.VentaMapper;
import mx.uv.tcsw.application.exception.RecursoNoEncontradoException;
import mx.uv.tcsw.application.port.in.LineaVentaComando;
import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Venta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaRestController {
    private final RegistrarVentaUseCase registrarVenta;
    private final VentaRepository ventas;

    public VentaRestController(RegistrarVentaUseCase registrarVenta, VentaRepository ventas) {
        this.registrarVenta = registrarVenta;
        this.ventas = ventas;
    }

    @PostMapping
    public ResponseEntity<VentaResponse> crear(@Valid @RequestBody CrearVentaRequest request) {
        List<LineaVentaComando> lineas = request.getLineas().stream()
            .map(linea -> new LineaVentaComando(linea.getProductoId(), linea.getCantidad()))
            .collect(Collectors.toList());
        Venta venta = registrarVenta.registrar(UUID.randomUUID().toString(), lineas);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(venta.getId()).toUri();
        return ResponseEntity.created(location).body(VentaMapper.toResponse(venta));
    }

    @GetMapping("/{id}")
    public VentaResponse obtener(@PathVariable String id) {
        Venta venta = ventas.buscar(id).orElseThrow(
            () -> new RecursoNoEncontradoException("Venta no encontrada: " + id));
        return VentaMapper.toResponse(venta);
    }
}
