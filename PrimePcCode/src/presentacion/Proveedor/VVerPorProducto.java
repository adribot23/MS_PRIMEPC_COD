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

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VVerPorProducto extends JPanel {
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
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar Proveedor por Producto"));

		JTextField mostrarProductoId = new JTextField();
		JButton btnMostrarPorProducto = new JButton("Mostrar Proveedor");
		btnMostrarPorProducto.setBackground(new Color(200, 255, 200));
		btnMostrarPorProducto.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(mostrarProductoId.getText());
				Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_PROVEEDORES_POR_PRODUCTO, idProducto));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});

		add(new JLabel("ID Producto:"));
		add(mostrarProductoId);
		add(btnMostrarPorProducto);
	}
}