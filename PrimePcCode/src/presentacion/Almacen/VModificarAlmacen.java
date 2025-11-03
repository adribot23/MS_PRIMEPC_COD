/**
 * 
 */
package presentacion.Almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class VModificarAlmacen extends JPanel implements IGUI {
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
		setBorder(BorderFactory.createTitledBorder("Modificar Almacen"));
		setLayout(new GridLayout(7, 1, 5, 5));

		JTextField idField = new JTextField();
		JTextField nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton modificarButton = new JButton("Modificar");

		modificarButton.setBackground(new Color(200, 255, 200));

		add(new JLabel("ID:"));
		add(idField);
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(modificarButton);

		modificarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText());
				String nombre = nombreField.getText();
				int capacidad = Integer.parseInt(capacidadField.getText());
				TAlmacen almacen = new TAlmacen(id, nombre, capacidad, -1);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_ALMACEN, almacen));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos inválidos.");
			}
		});
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}