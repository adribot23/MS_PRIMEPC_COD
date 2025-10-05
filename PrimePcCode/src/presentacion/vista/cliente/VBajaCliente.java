package presentacion.vista.cliente;

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

public class VBajaCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBajaCliente() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Baja Cliente"));

		JTextField bajaID = new JTextField();
		JButton btnBaja = new JButton("Dar de baja cliente");

		btnBaja.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID cliente:"));
		add(bajaID);
		add(btnBaja);

		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaID.getText());
				ctrl.accion(Evento.BAJA_CLIENTE, id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID debe ser un numero.");
			}
		});
	}
}
