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

public class VModificarAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarAlmacen() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Modificar Almacen"));
		setLayout(new GridLayout(7, 1, 5, 5));

		JTextField idField = new JTextField();
		JTextField nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton modificarButton = new JButton("Modificar");

		modificarButton.setBackground(new Color(200, 255, 200));

		add(new JLabel("ID:"));
		add(idField);
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(modificarButton);

		modificarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText());
				String nombre = nombreField.getText();
				int capacidad = Integer.parseInt(capacidadField.getText());
				TAlmacen almacen = new TAlmacen(id, nombre, capacidad, -1);
				ctrl.accion(Evento.MODIFICAR_ALMACEN, almacen);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos inválidos.");
			}
		});
	}

}
