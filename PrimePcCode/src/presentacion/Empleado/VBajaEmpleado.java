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
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VBajaEmpleado extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public VBajaEmpleado() {
		super("Baja Empleado");
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
		setLayout(new GridLayout(3, 1));
		// setBorder(BorderFactory.createTitledBorder("Baja Empleado"));

		JTextField bajaID = new JTextField();
		JButton btnBaja = new JButton("Dar de baja");
		btnBaja.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID empleado:"));
		add(bajaID);
		add(btnBaja);

		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaID.getText());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_EMPLEADO, id));
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

		case VBAJA_EMPLEADO:
			this.setVisible(true);
			break;
		case RES_BAJA_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado dado de baja con ID: " + datos);
			break;
		case RES_ALTA_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el empleado.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);

		}

	}
}