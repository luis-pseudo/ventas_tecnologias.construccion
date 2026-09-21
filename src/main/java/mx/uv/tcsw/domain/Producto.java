package mx.uv.tcsw;

public class Producto {
    private String nombre;
    private Precio precio;

    public Producto(String nombre, Precio precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (precio == null) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public Precio getPrecio() { return precio; }
    
    // Se permite actualizar el precio del catálogo
    public void setPrecio(Precio precio) { 
        if (precio == null) throw new IllegalArgumentException("Precio inválido");
        this.precio = precio; 
    }
}