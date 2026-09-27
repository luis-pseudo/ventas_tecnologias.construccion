package mx.uv.tcsw;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;
import mx.uv.tcsw.domain.*;

public class VentaTest {

    @Test
    public void pruebaPrecioHistoricoSeConserva() {
        Precio precioOriginal = new Precio(new BigDecimal("100.50"));
        Producto prod = new Producto("Monitor", precioOriginal);
        DetalleVenta detalle = new DetalleVenta(prod, 2);
        
        // Simulamos que el precio del producto sube después de venderlo
        prod.setPrecio(new Precio(new BigDecimal("150.00")));
        
        // El detalle debe mantener el histórico intacto
        assertEquals(new BigDecimal("100.50"), detalle.getPrecioHistorico().getValor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void pruebaPrecioRechazaNegativos() {
        new Precio(new BigDecimal("-10.00")); // Debe lanzar excepción
    }

    @Test(expected = IllegalArgumentException.class)
    public void pruebaDetalleRechazaCantidadInvalida() {
        Producto prod = new Producto("Mouse", new Precio(new BigDecimal("50.00")));
        new DetalleVenta(prod, 0); // Debe lanzar excepción
    }
}