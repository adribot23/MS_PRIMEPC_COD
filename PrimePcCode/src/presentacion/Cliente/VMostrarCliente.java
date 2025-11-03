/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

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
public class VMostrarCliente extends JPanel implements IGUI {
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
		setBorder(BorderFactory.createTitledBorder("Mostrar clientes"));
		JButton btnMostrarTodos = new JButton("Mostrar todos los clientes");
		btnMostrarTodos.setBackground(new Color(200, 255, 200));
		btnMostrarTodos.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_CLIENTES, null));
		});

		add(btnMostrarTodos);
	}

	@Override
	public void actualizar(Context context) {
		// TODO Auto-generated method stub
		
	}
}