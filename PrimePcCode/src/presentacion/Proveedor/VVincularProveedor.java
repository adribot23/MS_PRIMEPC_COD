/**
 * 
 */
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

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VVincularProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField txtProducto;
	private JTextField txtProveedor;

	public VVincularProveedor() {
		super("Vincular Producto con Proveedor");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Vincular Producto"));

		JLabel lblProducto = new JLabel("ID Producto:");
		txtProducto = new JTextField();

		JLabel lblProveedor = new JLabel("ID Proveedor:");
		txtProveedor = new JTextField();

		JButton btnVincular = new JButton("Vincular");
		btnVincular.setBackground(new Color(200, 255, 200));
		btnVincular.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProducto.getText().trim());
				int idProveedor = Integer.parseInt(txtProveedor.getText().trim());
				TProveedorProducto tp = new TProveedorProducto();
				tp.setIdProducto(idProducto);
				tp.setIdProveedor(idProveedor);

				Controlador.getInstancia().accion(new Context(Evento.VINCULAR_PRODUCTO_PROVEEDOR, tp));
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
		add(btnVincular);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);

	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VVINCULAR_PRODUCTO_PROVEEDOR:
			this.setVisible(true);
			break;
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(this, "Producto vinculado correctamente al proveedor.");
			break;
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(this, "Error al vincular el producto con el proveedor.");
			break;
		default:
			break;
		}
	}

}