package mx.uv.tcsw.ui.swing;

import mx.uv.tcsw.adapter.persistence.postgres.VentaRepositoryJdbc;
import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.application.service.RegistrarVentaService;

import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        VentaRepository repository = new VentaRepositoryJdbc();
        RegistrarVentaUseCase casoDeUso = new RegistrarVentaService(repository);

        SwingUtilities.invokeLater(() -> {
            VentaView vista = new VentaView();
            VentaController controlador = new VentaController(vista, casoDeUso);
            controlador.iniciar();
        });
    }
}
