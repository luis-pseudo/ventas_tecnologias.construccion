package mx.uv.tcsw;

public class Producto {
    private String codigo;
    private String nombre;
    private Precio precio;
    private int existencia;

    public Producto(String codigo, String nombre, Precio precio, int existencia) {
        if (existencia < 0) {
            throw new IllegalArgumentException("La existencia no puede ser menor a cero");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }

    public void reducirExistencia(int cantidad) {
        if (cantidad > this.existencia) {
            throw new IllegalArgumentException("Existencia insuficiente");
        }
        this.existencia -= cantidad;
    }

    public Precio getPrecio() { return precio; }
}