/**
 * 
 */
package presentacion.Almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.GUI.IGUI;
import presentacion.factoria.Evento;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;

public class VBuscarAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBuscarAlmacen() {
		ctrl = Controlador.getInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Buscar Almacen"));
		setLayout(new GridLayout(3, 1, 5, 5));

		JTextField idField = new JTextField();
		JButton buscarButton = new JButton("Buscar");
		buscarButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID almacen:"));
		add(idField);
		add(buscarButton);

		buscarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText().trim());
				ctrl.accion(new Context(Evento.BUSCAR_ALMACEN, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un numero.");
			}
		});
	}

}