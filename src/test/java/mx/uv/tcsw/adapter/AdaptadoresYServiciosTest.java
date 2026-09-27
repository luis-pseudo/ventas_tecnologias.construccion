package mx.uv.tcsw.adapter;

import mx.uv.tcsw.adapter.memory.InMemoryProductoRepository;
import mx.uv.tcsw.adapter.memory.InMemoryVentaRepository;
import mx.uv.tcsw.application.service.RegistrarVentaService;
import mx.uv.tcsw.domain.Precio;
import mx.uv.tcsw.domain.Producto;
import mx.uv.tcsw.domain.Venta;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AdaptadoresYServiciosTest {

    // --- PRUEBAS DE IN MEMORY PRODUCTO REPOSITORY ---
    @Test
    public void testInMemoryProductoRepositoryGuardarYBuscar() {
        InMemoryProductoRepository repo = new InMemoryProductoRepository();
        Producto producto = new Producto("Teclado", new Precio(new BigDecimal("500.00")));

        repo.guardar("P-1", producto);

        Optional<Producto> encontrado = repo.buscarPorId("P-1");
        assertTrue(encontrado.isPresent());
        assertEquals("Teclado", encontrado.get().getNombre());

        Optional<Producto> noEncontrado = repo.buscarPorId("INEXISTENTE");
        assertFalse(noEncontrado.isPresent());
    }

    @Test
    public void testInMemoryProductoRepositoryObtenerTodos() {
        InMemoryProductoRepository repo = new InMemoryProductoRepository();
        Producto p1 = new Producto("P1", new Precio(new BigDecimal("10.00")));
        Producto p2 = new Producto("P2", new Precio(new BigDecimal("20.00")));

        repo.guardar("1", p1);
        repo.guardar("2", p2);

        List<Producto> todos = repo.obtenerTodos();
        assertEquals(2, todos.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testInMemoryProductoRepositoryColeccionInmodificable() {
        InMemoryProductoRepository repo = new InMemoryProductoRepository();
        Producto p1 = new Producto("P1", new Precio(new BigDecimal("10.00")));
        repo.guardar("1", p1);

        List<Producto> todos = repo.obtenerTodos();
        todos.add(new Producto("P3", new Precio(new BigDecimal("30.00"))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInMemoryProductoRepositoryGuardarNuloLanzaExcepcion() {
        InMemoryProductoRepository repo = new InMemoryProductoRepository();
        repo.guardar(null, null);
    }

    // --- PRUEBAS DE IN MEMORY VENTA REPOSITORY ---
    @Test
    public void testInMemoryVentaRepositoryGuardarYBuscar() {
        InMemoryVentaRepository repo = new InMemoryVentaRepository();
        Venta venta = new Venta("V-100");

        repo.guardar(venta);

        Optional<Venta> encontrada = repo.buscar("V-100");
        assertTrue(encontrada.isPresent());
        assertEquals("V-100", encontrada.get().getId());

        Optional<Venta> noEncontrada = repo.buscar("INEXISTENTE");
        assertFalse(noEncontrada.isPresent());
    }

    // --- PRUEBAS DE REGISTRAR VENTA SERVICE ---
    @Test
    public void testRegistrarVentaServiceFlujoCompleto() {
        InMemoryVentaRepository repo = new InMemoryVentaRepository();
        RegistrarVentaService servicio = new RegistrarVentaService(repo);

        Venta ventaRegistrada = servicio.registrar("V-200");

        assertNotNull(ventaRegistrada);
        assertEquals("V-200", ventaRegistrada.getId());

        Optional<Venta> guardada = repo.buscar("V-200");
        assertTrue(guardada.isPresent());
    }
}
