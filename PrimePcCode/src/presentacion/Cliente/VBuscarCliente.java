/**
 * 
 */
package presentacion.Cliente;

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
public class VBuscarCliente extends JFrame implements IGUI {
	
	public VBuscarCliente() {
		super("Buscar Cliente");
		initGUI();
		setSize(400, 200);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	public void initGUI() {
		setLayout(new GridLayout(3, 1));
		//setBorder(BorderFactory.createTitledBorder("Buscar cliente"));

		JTextField txtBuscarID = new JTextField();
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID cliente:"));
		add(txtBuscarID);
		add(btnBuscar);

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtBuscarID.getText());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_CLIENTE, id));
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

		case VBUSCAR_CLIENTE:
			this.setVisible(true);
			break;
		case RES_BUSCAR_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, "Cliente encontrado con ID: " + datos);
			break;
		case RES_BUSCAR_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al buscar el cliente.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
			
		}
	}
}