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

import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VModificarProveedor extends JPanel {
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
		setLayout(new GridLayout(5, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar Proveedor"));

		JTextField modId = new JTextField();
		JTextField modNombre = new JTextField();
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modId.getText());
				String nombre = modNombre.getText();
				TProveedor p = new TProveedor(id, nombre);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_PROVEEDOR, p));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});
		add(new JLabel("ID:"));
		add(modId);
		add(new JLabel("Nombre:"));
		add(modNombre);
		add(btnModificar);
	}
}