package mx.uv.tcsw.domain;
public final class DescuentoPorVolumen implements PoliticaDescuento {
    @Override
    public double calcular(Venta venta) {
        // Ejemplo de regla: 5% si el total supera 1000
        return venta.calcularTotal() > 1000 ? venta.calcularTotal() * 0.05 : 0.0;
    }
}
