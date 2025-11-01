/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VCalcularImporteMasVendido extends JPanel{
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private Controlador ctrl;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void initGUI() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Calcular Importe Más Vendido"));

		JTextField txtBuscarID = new JTextField();
		JButton btnBuscar = new JButton("Calcular");
		btnBuscar.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID producto:"));
		add(txtBuscarID);
		add(btnBuscar);

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtBuscarID.getText());
				Controlador.getInstancia().accion(new Context(Evento.CALCULAR_MAS_VENDIDO, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID debe ser un numero.");
			}
		});
	}
}