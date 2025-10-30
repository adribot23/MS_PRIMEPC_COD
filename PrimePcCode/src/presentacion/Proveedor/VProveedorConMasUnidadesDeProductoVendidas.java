/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

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
public class VProveedorConMasUnidadesDeProductoVendidas extends JPanel implements IGUI{
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
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar Proveedores"));
		JButton btnMostrar = new JButton("Mostrar todos");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(e -> Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR_CON_MAS_UDS, null)));

		add(btnMostrar);
	}

	@Override
	public void actualizar(Context context) {
		// TODO Apéndice de método generado automáticamente
		
	}
}