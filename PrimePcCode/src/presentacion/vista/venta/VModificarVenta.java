package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.transfers.TVenta;
import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VModificarVenta extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarVenta() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(9, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar Venta"));
		JTextField modID = new JTextField();
		JTextField modMetodoPago = new JTextField();
		JTextField modEmpleado = new JTextField();
		JTextField modCliente = new JTextField();
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				TVenta v = new TVenta();
				v.setId(Integer.parseInt(modID.getText()));
				v.setMetodoPago(modMetodoPago.getText());
				v.setIdEmpleado(Integer.parseInt(modEmpleado.getText()));
				v.setIdCliente(Integer.parseInt(modCliente.getText()));
				ctrl.accion(Evento.MODIFICAR_VENTA, v);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error en campos numericos");
			}
		});
		add(new JLabel("ID:"));
		add(modID);
		add(new JLabel("Metodo de pago:"));
		add(modMetodoPago);
		add(new JLabel("ID Empleado:"));
		add(modEmpleado);
		add(new JLabel("ID Cliente:"));
		add(modCliente);
		add(btnModificar);

	}
}
