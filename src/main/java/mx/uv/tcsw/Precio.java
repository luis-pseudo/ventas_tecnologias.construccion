package mx.uv.tcsw;

public class Precio {
    private final double valor;

    public Precio(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.valor = valor;
    }

    public double getValor() { return valor; }
}