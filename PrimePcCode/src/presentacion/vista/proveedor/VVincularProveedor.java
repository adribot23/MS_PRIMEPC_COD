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
import presentacion.vista.Evento;

public class VVincularProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VVincularProveedor() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(3, 2));
		setBorder(BorderFactory.createTitledBorder("Vincular / Desvincular Producto"));
		JTextField txtProducto = new JTextField();
		JTextField txtProveedor = new JTextField();

		JButton btnVincular = new JButton("Vincular");
		btnVincular.setBackground(new Color(200, 255, 200));
		btnVincular.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProducto.getText());
				int idProveedor = Integer.parseInt(txtProveedor.getText());
				int[] datos = { idProducto, idProveedor };
				ctrl.accion(Evento.VINCULAR_PRODUCTO_PROVEEDOR, datos);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "IDs invalidos.");
			}
		});
		JButton btnDesvincular = new JButton("Desvincular");
		btnDesvincular.setBackground(new Color(200, 255, 200));
		btnDesvincular.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProducto.getText());
				int idProveedor = Integer.parseInt(txtProveedor.getText());
				int[] datos = { idProducto, idProveedor };
				ctrl.accion(Evento.DESVINCULAR_PRODUCTO_PROVEEDOR, datos);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "IDs invalidos.");
			}
		});

		add(new JLabel("ID Producto:"));
		add(txtProducto);
		add(new JLabel("ID Proveedor:"));
		add(txtProveedor);
		add(btnVincular);
		add(btnDesvincular);

	}
}