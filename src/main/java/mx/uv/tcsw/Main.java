package mx.uv.tcsw;

import mx.uv.tcsw.adapter.persistence.postgres.VentaRepositoryJdbc;
import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.application.service.RegistrarVentaService;
import mx.uv.tcsw.domain.Venta;

public class Main {
    public static void main(String[] args) {
        VentaRepository repository = new VentaRepositoryJdbc();
        RegistrarVentaUseCase useCase = new RegistrarVentaService(repository);

        String idVenta = "VENTA-001";
        Venta venta = useCase.registrar(idVenta);
        System.out.println("Venta registrada y persistida con éxito en PostgreSQL: " + venta.getId());
    }
}
