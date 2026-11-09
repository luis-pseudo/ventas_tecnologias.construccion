package mx.uv.tcsw.application.service;

import java.util.List;
import mx.uv.tcsw.application.ProductoRepository;
import mx.uv.tcsw.application.exception.RecursoNoEncontradoException;
import mx.uv.tcsw.application.port.in.LineaVentaComando;
import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Producto;
import mx.uv.tcsw.domain.Venta;

public class RegistrarVentaService implements RegistrarVentaUseCase {
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public RegistrarVentaService(VentaRepository ventaRepository) {
        this(ventaRepository, null);
    }

    public RegistrarVentaService(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Venta registrar(String id) {
        Venta venta = new Venta(id);
        ventaRepository.guardar(venta);
        return venta;
    }

    @Override
    public Venta registrar(String id, List<LineaVentaComando> lineas) {
        if (productoRepository == null) {
            throw new IllegalStateException("Catálogo de productos no configurado");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador de la venta es obligatorio");
        }
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos una partida");
        }
        Venta venta = new Venta(id);
        for (LineaVentaComando linea : lineas) {
            Producto producto = productoRepository.buscarPorId(linea.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "Producto no encontrado: " + linea.getProductoId()));
            venta.agregarPartida(producto, linea.getCantidad());
        }
        ventaRepository.guardar(venta);
        return venta;
    }
}