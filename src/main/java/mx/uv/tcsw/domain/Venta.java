package mx.uv.tcsw.domain;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private final String id;
    private List<DetalleVenta> detalles = new ArrayList<>();
    private PoliticaDescuento politicaDescuento = new SinDescuento();
    private List<VentaObserver> observadores = new ArrayList<>();

    public Venta(String id) {
        this.id = id;
    }

    public void setPoliticaDescuento(PoliticaDescuento politica) {
        this.politicaDescuento = politica;
    }

    public void agregarObservador(VentaObserver observador) {
        this.observadores.add(observador);
    }

    public void agregarPartida(Producto producto, int cantidad) {
        detalles.add(new DetalleVenta(producto, cantidad));
    }

    public double calcularTotal() {
        double subtotal = 0;
        for (DetalleVenta detalle : detalles) {
            subtotal += detalle.getSubtotal();
        }
        return subtotal - politicaDescuento.calcular(this);
    }

    public void confirmarVenta() {
        for (VentaObserver obs : observadores) {
            obs.enVentaRegistrada(this);
        }
    }
}