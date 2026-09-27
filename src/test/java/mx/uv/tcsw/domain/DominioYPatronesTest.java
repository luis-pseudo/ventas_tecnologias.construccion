package mx.uv.tcsw.domain;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class DominioYPatronesTest {

    // --- PRUEBAS DE ENTIDADES BASE (Precio, Producto, DetalleVenta) ---
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
    public void testProductoValidoGettersYSetter() {
        Precio precio = new Precio(new BigDecimal("100.00"));
        Producto producto = new Producto("Laptop", precio);
        assertEquals("Laptop", producto.getNombre());
        assertEquals(precio, producto.getPrecio());
        
        Precio nuevoPrecio = new Precio(new BigDecimal("200.00"));
        producto.setPrecio(nuevoPrecio);
        assertEquals(nuevoPrecio, producto.getPrecio());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductoNombreVacioLanzaExcepcion() {
        new Producto("   ", new Precio(new BigDecimal("100.00")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductoPrecioNuloLanzaExcepcion() {
        new Producto("Laptop", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductoSetPrecioNuloLanzaExcepcion() {
        Producto producto = new Producto("Laptop", new Precio(new BigDecimal("100.00")));
        producto.setPrecio(null);
    }

    @Test
    public void testDetalleVentaValidoYGetters() {
        Producto producto = new Producto("Mouse", new Precio(new BigDecimal("25.00")));
        DetalleVenta detalle = new DetalleVenta(producto, 2);
        assertEquals(2, detalle.getCantidad());
        assertEquals(new BigDecimal("25.00"), detalle.getPrecioHistorico().getValor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetalleVentaProductoNuloLanzaExcepcion() {
        new DetalleVenta(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetalleVentaCantidadInvalidaLanzaExcepcion() {
        Producto producto = new Producto("Mouse", new Precio(new BigDecimal("25.00")));
        new DetalleVenta(producto, 0);
    }

    // --- PRUEBAS DE VENTA Y PATRONES P06 ---
    
    @Test
    public void testVentaGettersYCalculoSinDescuentoExplicito() {
        Venta venta = new Venta("V-001");
        assertEquals("V-001", venta.getId());

        venta.agregarPartida(new Producto("Teclado", new Precio(new BigDecimal("50.00"))), 2);
        
        // Forzamos el uso de SinDescuento explícitamente para cobertura
        venta.setPoliticaDescuento(new SinDescuento());
        assertEquals(100.0, venta.calcularTotal(), 0.01);
    }

    @Test
    public void testStrategyDescuentoPorVolumenAplicaMayorA1000() {
        Venta venta = new Venta("V-002");
        venta.agregarPartida(new Producto("Monitor", new Precio(new BigDecimal("1200.00"))), 1);
        
        venta.setPoliticaDescuento(new DescuentoPorVolumen());
        // 1200 - 5% (60) = 1140
        assertEquals(1140.0, venta.calcularTotal(), 0.01);
    }

    @Test
    public void testStrategyDescuentoPorVolumenNoAplicaMenorOIgualA1000() {
        Venta venta = new Venta("V-003");
        // Límite exacto de 1000 para probar la condición del ternario
        venta.agregarPartida(new Producto("Cable", new Precio(new BigDecimal("1000.00"))), 1);
        
        venta.setPoliticaDescuento(new DescuentoPorVolumen());
        // Total <= 1000, no aplica descuento, se mantiene en 1000
        assertEquals(1000.0, venta.calcularTotal(), 0.01);
    }

    @Test
    public void testObserverNotificacionCorrecta() {
        Venta venta = new Venta("V-004");
        final boolean[] notificado = {false};

        venta.agregarObservador(v -> notificado[0] = true);
        venta.confirmarVenta();

        assertTrue("El observer debió ser notificado al confirmar la venta", notificado[0]);
    }

    @Test
    public void testVentaFactoryCreacionValida() {
        Producto producto = new Producto("Silla", new Precio(new BigDecimal("150.00")));
        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(new DetalleVenta(producto, 1));

        Venta venta = VentaFactory.nueva("F-001", detalles);
        assertNotNull(venta);
        assertEquals("F-001", venta.getId());
        assertEquals(150.0, venta.calcularTotal(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVentaFactoryListaVaciaLanzaExcepcion() {
        VentaFactory.nueva("F-002", Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVentaFactoryListaNulaLanzaExcepcion() {
        VentaFactory.nueva("F-003", null);
    }
}   