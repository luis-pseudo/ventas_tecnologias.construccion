package mx.uv.tcsw.adapter.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import mx.uv.tcsw.adapter.web.dto.response.VentaResponse;
import mx.uv.tcsw.domain.Precio;
import mx.uv.tcsw.domain.Producto;
import mx.uv.tcsw.domain.Venta;
import org.junit.jupiter.api.Test;

class VentaMapperTest {
    @Test
    void mapeaVentaConUnaLinea() {
        Venta venta = new Venta("v1");
        venta.agregarPartida(new Producto("Teclado", new Precio(new BigDecimal("450.00"))), 2);

        VentaResponse response = VentaMapper.toResponse(venta);

        assertEquals("v1", response.getId());
        assertEquals(1, response.getLineas().size());
        assertEquals(new BigDecimal("900.00"), response.getSubtotal());
        assertEquals(new BigDecimal("900.00"), response.getTotal());
        assertEquals("Teclado", response.getLineas().get(0).getProducto());
        assertEquals(2, response.getLineas().get(0).getCantidad());
    }
}
