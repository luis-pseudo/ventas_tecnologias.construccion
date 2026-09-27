package mx.uv.tcsw.domain;
public final class SinDescuento implements PoliticaDescuento {
    @Override
    public double calcular(Venta venta) {
        return 0.0;
    }
}
