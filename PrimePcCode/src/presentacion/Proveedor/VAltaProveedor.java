/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.GUI.IGUI;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VAltaProveedor extends JPanel  {
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
		setBorder(BorderFactory.createTitledBorder("Alta Proveedor"));

		JTextField altaNombre = new JTextField();
		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			String nombre = altaNombre.getText();
			TProveedor p = new TProveedor(0, nombre);
			Controlador.getInstancia().accion(new Context(Evento.ALTA_PROVEEDOR, p));
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(btnAlta);
	}

	
}