package mx.uv.tcsw.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
    
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    private List<DetalleVenta> detalles = new ArrayList<>();
    
    @Transient
    private PoliticaDescuento politicaDescuento = new SinDescuento();
    
    @Transient
    private List<VentaObserver> observadores = new ArrayList<>();

    // Constructor vacío requerido obligatoriamente por Hibernate
    public Venta() {
    }

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

    public double calcularSubtotal() {
        double subtotal = 0;
        for (DetalleVenta detalle : detalles) {
            subtotal += detalle.getSubtotal();
        }
        return subtotal;
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

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}