package presentacion.vista.empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VBajaEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBajaEmpleado() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Baja Empleado"));

		JTextField bajaID = new JTextField();
		JButton btnBaja = new JButton("Dar de baja");
		btnBaja.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID empleado:"));
		add(bajaID);
		add(btnBaja);

		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaID.getText());
				ctrl.accion(new Context(Evento.BAJA_EMPLEADO, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID debe ser un numero.");
			}
		});
	}
}
