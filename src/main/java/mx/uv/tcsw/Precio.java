package mx.uv.tcsw;

import java.math.BigDecimal;

public final class Precio {
    private final BigDecimal valor;

    public Precio(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser nulo o negativo");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }
}