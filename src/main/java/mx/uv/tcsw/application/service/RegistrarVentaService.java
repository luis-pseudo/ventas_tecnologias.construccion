package mx.uv.tcsw.application.service;

import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Venta;

public class RegistrarVentaService implements RegistrarVentaUseCase {
    private final VentaRepository ventaRepository;

    public RegistrarVentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Venta registrar(String id) {
        Venta venta = new Venta(id);
        ventaRepository.guardar(venta);
        return venta;
    }
}