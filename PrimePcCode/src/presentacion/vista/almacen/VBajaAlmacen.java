package presentacion.vista.almacen;

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

public class VBajaAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VBajaAlmacen() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Baja Almacen"));
		setLayout(new GridLayout(3, 1, 5, 5));

		JTextField idField = new JTextField();
		JButton bajaButton = new JButton("Dar de baja");
		bajaButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID almacen:"));
		add(idField);
		add(bajaButton);

		bajaButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText().trim());
				ctrl.accion(new Context(Evento.BAJA_ALMACEN, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});
	}

}
