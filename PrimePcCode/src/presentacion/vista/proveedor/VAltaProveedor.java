package presentacion.vista.proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.transfers.TProveedor;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VAltaProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VAltaProveedor() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Alta Proveedor"));

		JTextField altaNombre = new JTextField();
		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			String nombre = altaNombre.getText();
			TProveedor p = new TProveedor(0, nombre);
			ctrl.accion(Evento.ALTA_PROVEEDOR, p);
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(btnAlta);
	}
}
