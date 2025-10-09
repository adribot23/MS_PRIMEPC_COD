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

public class VBuscarProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBuscarProveedor() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Buscar Proveedor"));

		JTextField buscarId = new JTextField();
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));
		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(buscarId.getText());
				ctrl.accion(Evento.BUSCAR_PROVEEDOR, id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});

		add(new JLabel("ID Proveedor:"));
		add(buscarId);
		add(btnBuscar);
	}
}
