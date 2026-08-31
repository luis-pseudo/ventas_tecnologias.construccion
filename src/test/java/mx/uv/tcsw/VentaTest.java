package mx.uv.tcsw;

import org.junit.Test;
import static org.junit.Assert.*;

public class VentaTest {

    @Test
    public void pruebaEstadoValido_CalculaTotalCorrectamente() {
        Producto p1 = new Producto("001", "Libro", new Precio(200.0), 10);
        Venta venta = new Venta();
        
        venta.agregarPartida(p1, 2);
        
        assertEquals(400.0, venta.calcularTotal(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void pruebaEstadoInvalido_RechazaCantidadCero() {
        Producto p2 = new Producto("002", "Goma", new Precio(10.0), 5);
        Venta venta = new Venta();
        
        venta.agregarPartida(p2, 0); // Lanza la excepción y la prueba pasa
    }
}