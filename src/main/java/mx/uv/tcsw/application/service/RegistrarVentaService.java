package mx.uv.tcsw.application.service;

import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Venta;

public class RegistrarVentaService implements RegistrarVentaUseCase {
    private final VentaRepository repository;

    public RegistrarVentaService(VentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Venta registrar(String id) {
        Venta venta = new Venta(id);
        repository.guardar(venta);
        return venta;
    }
}