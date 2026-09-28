package mx.uv.tcsw.domain;
public final class DescuentoPorVolumen implements PoliticaDescuento {
    @Override
    public double calcular(Venta venta) {
        // Ejemplo de regla: 5% si el total supera 1000
        return venta.calcularSubtotal() > 1000 ? venta.calcularSubtotal() * 0.05 : 0.0;
    }
}
