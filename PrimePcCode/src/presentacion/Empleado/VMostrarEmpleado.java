/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
public class VMostrarEmpleado extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public VMostrarEmpleado() {
		super("Mostrar Empleado");
		initGUI();
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void initGUI() {
		setLayout(new GridLayout(1, 1));
		// setBorder(BorderFactory.createTitledBorder("Mostrar empleados"));
		JButton btnMostrarTodos = new JButton("Mostrar todos los empleados");
		btnMostrarTodos.setBackground(new Color(200, 255, 200));
		btnMostrarTodos.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_EMPLEADOS, null));
		});

		add(btnMostrarTodos);
	}

	@Override
	public void actualizar(Context context) {
		// TODO Auto-generated method stub
//		Evento evento = context.getEvento();
//		Object datos = context.getDatos();
//		switch (evento) {
//
//		case VMODIFICAR_EMPLEADO:
//			this.setVisible(true);
//			break;
//		case RES_MODIFICAR_EMPLEADO_OK:
//			JOptionPane.showMessageDialog(null, "Empleado modificado con ID: " + datos);
//			break;
//		case RES_MODIFICAR_EMPLEADO_KO:
//			JOptionPane.showMessageDialog(null, "Error al modificar el empleado.");
//			break;
//		default:
//			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
//		
//	}
	}
}