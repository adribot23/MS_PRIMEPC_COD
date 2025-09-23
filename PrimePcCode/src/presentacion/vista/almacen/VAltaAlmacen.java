package presentacion.vista.almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.transfers.TAlmacen;
import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VAltaAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VAltaAlmacen() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Alta Almacen"));
		setLayout(new GridLayout(5, 1, 5, 5));

		JTextField  nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton altaButton = new JButton("Dar de alta");
		altaButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(altaButton);

		altaButton.addActionListener(e -> {
			try {
				String nombre = nombreField.getText().trim();
				int capacidad = Integer.parseInt(capacidadField.getText().trim());
				TAlmacen almacen = new TAlmacen(nombre, capacidad, 0);
				ctrl.accion(Evento.ALTA_ALMACEN, almacen);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Capacidad y ocupacion deben ser numeros.");
			}
		});
	}

	
}
