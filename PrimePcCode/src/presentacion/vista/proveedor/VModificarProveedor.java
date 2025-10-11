package presentacion.vista.proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VModificarProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarProveedor() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(5, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar Proveedor"));

		JTextField modId = new JTextField();
		JTextField modNombre = new JTextField();
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modId.getText());
				String nombre = modNombre.getText();
				TProveedor p = new TProveedor(id, nombre);
				ctrl.accion(new Context(Evento.MODIFICAR_PROVEEDOR, p));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});
		add(new JLabel("ID:"));
		add(modId);
		add(new JLabel("Nombre:"));
		add(modNombre);
		add(btnModificar);

	}
}
