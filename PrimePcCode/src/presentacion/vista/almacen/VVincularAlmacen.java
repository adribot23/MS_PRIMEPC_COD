package presentacion.vista.almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.controlador.ControladorImp;
import presentacion.factoria.Evento;

public class VVincularAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VVincularAlmacen() {
		ctrl = ControladorImp.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Vincular / Desvincular Producto"));
		setLayout(new GridLayout(3, 2, 5, 5));

		JTextField idAlmacenField = new JTextField();
		JTextField idProductoField = new JTextField();
		JButton vincularButton = new JButton("Vincular");
		JButton desvincularButton = new JButton("Desvincular");
		vincularButton.setBackground(new Color(200, 255, 200));
		desvincularButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID almacen:"));
		add(idAlmacenField);
		add(new JLabel("ID producto:"));
		add(idProductoField);
		add(vincularButton);
		add(desvincularButton);

		vincularButton.addActionListener(e -> {
			try {
				int idAlmacen = Integer.parseInt(idAlmacenField.getText().trim());
				int idProducto = Integer.parseInt(idProductoField.getText().trim());
				ctrl.accion(Evento.VINCULAR_PRODUCTO_ALMACEN, new int[] { idProducto, idAlmacen });
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "IDs inválidos.");
			}
		});

		desvincularButton.addActionListener(e -> {
			try {
				int idAlmacen = Integer.parseInt(idAlmacenField.getText().trim());
				int idProducto = Integer.parseInt(idProductoField.getText().trim());
				ctrl.accion(Evento.DESVINCULAR_PRODUCTO_ALMACEN, new int[] { idProducto, idAlmacen });
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "IDs invalidos.");
			}
		});
	}
}
