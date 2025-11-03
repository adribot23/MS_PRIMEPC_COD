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
public class VCalcularImporteMasVendido extends JFrame implements IGUI{
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public VCalcularImporteMasVendido() {
		super("Calcular Importe Total Del Empleado Que Mas Productos Ha Vendido");
		initGUI();
	}
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void initGUI() {
		setLayout(new GridLayout(3, 1));
		//setBorder(BorderFactory.createTitledBorder("Calcular Importe Más Vendido"));

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

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {

		case VCALCULAR_MAS_VENDIDO:
			this.setVisible(true);
			break;
		case RES_CALCULAR_MAS_VENDIDO_OK:
			JOptionPane.showMessageDialog(null, "Empleado que mas ha vendido con ID: " + datos);
			break;
		case RES_CALCULAR_MAS_VENDIDO_KO:
			JOptionPane.showMessageDialog(null, "Error al encontrar el empleado.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		
	}
	}
}