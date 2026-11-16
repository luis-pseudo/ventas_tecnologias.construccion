package mx.uv.tcsw.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import mx.uv.tcsw.adapter.memory.InMemoryVentaRepository;
import mx.uv.tcsw.domain.Venta;
import org.junit.Test;

public class InMemoryVentaRepositoryListarTest {

    @Test
    public void listarSinVentasDevuelveListaVacia() {
        InMemoryVentaRepository repo = new InMemoryVentaRepository();

        assertTrue(repo.listar().isEmpty());
    }

    @Test
    public void listarConservaElOrdenDeRegistro() {
        InMemoryVentaRepository repo = new InMemoryVentaRepository();
        repo.guardar(new Venta("V-3"));
        repo.guardar(new Venta("V-1"));
        repo.guardar(new Venta("V-2"));

        List<Venta> ventas = repo.listar();

        assertEquals(3, ventas.size());
        assertEquals("V-3", ventas.get(0).getId());
        assertEquals("V-1", ventas.get(1).getId());
        assertEquals("V-2", ventas.get(2).getId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void laListaDevueltaNoSePuedeModificar() {
        InMemoryVentaRepository repo = new InMemoryVentaRepository();

        repo.listar().add(new Venta("V-X"));
    }
}
