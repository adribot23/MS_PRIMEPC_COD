package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VBajaVenta extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBajaVenta() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {

		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Devolucion"));

		JTextField devId = new JTextField();
		JButton btnDevolucion = new JButton("Solicitar Devolucion");
		btnDevolucion.setBackground(new Color(200, 255, 200));
		btnDevolucion.addActionListener(e -> {
			try {
				int id = Integer.parseInt(devId.getText());
				ctrl.accion(Evento.BAJA_VENTA, id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error en campos numericos");
			}
		});

		add(new JLabel("ID Venta:"));
		add(devId);
		add(btnDevolucion);

	}

}
