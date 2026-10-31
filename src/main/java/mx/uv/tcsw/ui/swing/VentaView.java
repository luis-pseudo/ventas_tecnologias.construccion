package mx.uv.tcsw.ui.swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class VentaView extends JFrame {
    private final JTextField txtCliente;
    private final JButton btnRegistrar;

    public VentaView() {
        setTitle("Sistema de Ventas TCSW");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Cliente:"));
        txtCliente = new JTextField();
        add(txtCliente);

        btnRegistrar = new JButton("Registrar Venta");
        add(new JLabel());
        add(btnRegistrar);
    }

    public String getCliente() {
        return txtCliente.getText();
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void mostrarMensaje(String mensaje, boolean exito) {
        int tipo = exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", tipo);
    }
}
