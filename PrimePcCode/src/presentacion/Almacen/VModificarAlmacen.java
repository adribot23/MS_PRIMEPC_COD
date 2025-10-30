/**
 * 
 */
package presentacion.Almacen;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.GUI.IGUI;
import presentacion.factoria.Evento;
import negocio.Almacen.TAlmacen;

import java.awt.Color;
import java.awt.GridLayout;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;

public class VModificarAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarAlmacen() {
		ctrl = Controlador.getInstancia();
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
				ctrl.accion(new Context(Evento.MODIFICAR_ALMACEN, almacen));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos inválidos.");
			}
		});
	}

}