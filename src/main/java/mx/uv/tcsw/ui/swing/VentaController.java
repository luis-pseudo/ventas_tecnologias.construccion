package mx.uv.tcsw.ui.swing;

import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;

import java.awt.event.ActionEvent;

public class VentaController {
    private final VentaView vista;
    private final RegistrarVentaUseCase casoDeUso;

    public VentaController(VentaView vista, RegistrarVentaUseCase casoDeUso) {
        this.vista = vista;
        this.casoDeUso = casoDeUso;
        this.vista.getBtnRegistrar().addActionListener(this::registrarVenta);
    }

    private void registrarVenta(ActionEvent evento) {
        try {
            casoDeUso.registrar(vista.getCliente());
            vista.mostrarMensaje("Venta registrada exitosamente.", true);
        } catch (RuntimeException ex) {
            String detalle = ex.getMessage() == null ? "Error inesperado." : ex.getMessage();
            vista.mostrarMensaje("Error al registrar: " + detalle, false);
        }
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
}
