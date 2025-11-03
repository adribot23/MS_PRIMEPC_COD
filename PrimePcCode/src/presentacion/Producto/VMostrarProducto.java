/**
 * 
 */
package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VMostrarProducto extends JPanel implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Controlador ctrl;

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void initGUI() {
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar Productos"));
		JButton btnMostrar = new JButton("Mostrar todos");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_PRODUCTOS, null)));

		add(btnMostrar);
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}