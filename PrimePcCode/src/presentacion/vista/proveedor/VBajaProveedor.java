package presentacion.vista.proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VBajaProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBajaProveedor() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Baja Proveedor"));
		JTextField bajaId = new JTextField();
		JButton btnBaja = new JButton("Dar de baja");
		btnBaja.setBackground(new Color(200, 255, 200));
		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaId.getText());
				ctrl.accion(Evento.BAJA_PROVEEDOR, id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});

		add(new JLabel("ID Proveedor:"));
		add(bajaId);
		add(btnBaja);
	}
}
