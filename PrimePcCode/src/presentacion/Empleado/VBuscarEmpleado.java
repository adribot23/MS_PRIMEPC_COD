/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class VBuscarEmpleado extends JFrame implements IGUI {
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
		//setBorder(BorderFactory.createTitledBorder("Buscar empleado"));

		JTextField txtBuscarID = new JTextField();
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID empleado:"));
		add(txtBuscarID);
		add(btnBuscar);

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtBuscarID.getText());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_EMPLEADO, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID debe ser un numero.");
			}
		});
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {

		case VBUSCAR_EMPLEADO:
			this.setVisible(true);
			break;
		case RES_BUSCAR_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado buscado con ID: " + datos);	//Esto tiene que eseñar esto?
			break;
		case RES_BUSCAR_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al buscar el empleado.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		
	}
	}

}