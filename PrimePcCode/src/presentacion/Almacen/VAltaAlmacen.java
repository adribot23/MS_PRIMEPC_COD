/**
 * 
 */
package presentacion.Almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Almacen.TAlmacen;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VAltaAlmacen extends JPanel implements IGUI {
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
	public void iniGUI() {
		setBorder(BorderFactory.createTitledBorder("Alta Almacen"));
		setLayout(new GridLayout(5, 1, 5, 5));

		JTextField nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton altaButton = new JButton("Dar de alta");
		altaButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(altaButton);

		altaButton.addActionListener(e -> {
			try {
				String nombre = nombreField.getText().trim();
				int capacidad = Integer.parseInt(capacidadField.getText().trim());
				TAlmacen almacen = new TAlmacen(nombre, capacidad, 0);
				Controlador.getInstancia().accion(new Context(Evento.ALTA_ALMACEN, almacen));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Capacidad y ocupacion deben ser numeros.");
			}
		});
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}