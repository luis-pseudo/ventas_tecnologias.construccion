package mx.uv.tcsw.domain;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class DominioYPatronesTest {

    @Test
    public void testPrecioValidoYGetter() {
        Precio precio = new Precio(new BigDecimal("150.00"));
        assertEquals(new BigDecimal("150.00"), precio.getValor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrecioNuloLanzaExcepcion() {
        new Precio(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrecioNegativoLanzaExcepcion() {
        new Precio(new BigDecimal("-10.00"));
    }

    @Test
    public void testProductoCreacionYGetters() {
        Precio precio = new Precio(new BigDecimal("200.00"));
        Producto producto = new Producto("PROD-1", "Laptop", precio);

        assertEquals("PROD-1", producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals(precio, producto.getPrecio());
    }

    @Test
    public void testDetalleVentaSubtotal() {
        Precio precio = new Precio(new BigDecimal("50.00"));
        Producto producto = new Producto("P1", "Teclado", precio);
        DetalleVenta detalle = new DetalleVenta(producto, 3);

        assertEquals(3, detalle.getCantidad());
        assertEquals(new BigDecimal("150.00"), detalle.calcularSubtotal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetalleVentaCantidadInvalida() {
        Precio precio = new Precio(new BigDecimal("50.00"));
        Producto producto = new Producto("P1", "Teclado", precio);
        new DetalleVenta(producto, 0);
    }
}