package mx.uv.tcsw.adapter.web.dto.response;

import java.math.BigDecimal;

public class ProductoResponse {
    private final String id;
    private final String nombre;
    private final BigDecimal precio;

    public ProductoResponse(String id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
}
