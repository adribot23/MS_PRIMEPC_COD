/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VVincularProveedor extends JPanel implements IGUI {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private Controlador ctrl;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void initGUI() {
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
				ctrl.accion(new Context(Evento.VINCULAR_PRODUCTO_PROVEEDOR, datos));
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
				Controlador.getInstancia().accion(new Context(Evento.DESVINCULAR_PRODUCTO_PROVEEDOR, datos));
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

	@Override
	public void actualizar(Context context) {
		// TODO Auto-generated method stub
		
	}
}