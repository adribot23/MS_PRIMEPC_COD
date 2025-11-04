package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.Proveedor.TProveedorProducto;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VDesvincularProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField txtProducto;
	private JTextField txtProveedor;

	public VDesvincularProveedor() {
		super("Desvincular Producto de Proveedor");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Desvincular Producto"));

		JLabel lblProducto = new JLabel("ID Producto:");
		txtProducto = new JTextField();

		JLabel lblProveedor = new JLabel("ID Proveedor:");
		txtProveedor = new JTextField();

		JButton btnDesvincular = new JButton("Desvincular");
		btnDesvincular.setBackground(new Color(255, 255, 200));
		btnDesvincular.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProducto.getText().trim());
				int idProveedor = Integer.parseInt(txtProveedor.getText().trim());
				TProveedorProducto tp = new TProveedorProducto();
				tp.setIdProducto(idProducto);
				tp.setIdProveedor(idProveedor);

				Controlador.getInstancia().accion(new Context(Evento.DESVINCULAR_PRODUCTO_PROVEEDOR, tp));
				txtProducto.setText("");
				txtProveedor.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "IDs inválidos. Introduce números enteros.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});

		add(lblProducto);
		add(txtProducto);
		add(lblProveedor);
		add(txtProveedor);
		add(btnDesvincular);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);

	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VDESVINCULAR_PRODUCTO_PROVEEDOR:
			this.setVisible(true);
			break;
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(this, "Producto desvinculado correctamente del proveedor.");
			break;
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(this, "Error al desvincular el producto del proveedor.");
			break;
		default:
			break;
		}
	}
}