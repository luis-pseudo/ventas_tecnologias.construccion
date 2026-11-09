package mx.uv.tcsw.application;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import mx.uv.tcsw.adapter.memory.InMemoryProductoRepository;
import mx.uv.tcsw.adapter.memory.InMemoryVentaRepository;
import mx.uv.tcsw.application.exception.RecursoNoEncontradoException;
import mx.uv.tcsw.application.port.in.LineaVentaComando;
import mx.uv.tcsw.application.service.RegistrarVentaService;
import mx.uv.tcsw.domain.Precio;
import mx.uv.tcsw.domain.Producto;
import mx.uv.tcsw.domain.Venta;
import org.junit.Before;
import org.junit.Test;

public class RegistrarVentaConLineasTest {
    private RegistrarVentaService servicio;

    @Before
    public void setUp() {
        InMemoryProductoRepository productos = new InMemoryProductoRepository();
        productos.guardar("p1", new Producto("Teclado", new Precio(new BigDecimal("450.00"))));
        servicio = new RegistrarVentaService(new InMemoryVentaRepository(), productos);
    }

    @Test
    public void registraVentaConLineas() {
        Venta v = servicio.registrar("v1", Arrays.asList(new LineaVentaComando("p1", 2)));
        assertEquals(900.0, v.calcularSubtotal(), 0.001);
        assertEquals(1, v.getDetalles().size());
    }

    @Test(expected = RecursoNoEncontradoException.class)
    public void productoInexistenteLanzaNoEncontrado() {
        servicio.registrar("v2", Arrays.asList(new LineaVentaComando("no-existe", 1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ventaSinLineasEsInvalida() {
        servicio.registrar("v3", Collections.<LineaVentaComando>emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantidadCeroEsInvalida() {
        servicio.registrar("v4", Arrays.asList(new LineaVentaComando("p1", 0)));
    }
}