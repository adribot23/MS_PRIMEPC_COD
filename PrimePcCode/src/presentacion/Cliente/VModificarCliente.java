/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteSocio;
import negocio.Cliente.TClienteNoSocio;
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
public class VModificarCliente extends JFrame implements IGUI {
	
	public VModificarCliente() {
		super("Modificar Cliente");
		initGUI();
	}
	
	public void initGUI() {
		setLayout(new GridLayout(10, 1, 5, 5));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Cliente"));

		JTextField modificarID = new JTextField();
		JTextField modificarNombre = new JTextField();
		JTextField modificarDNI = new JTextField();
		JTextField modificarPuntos = new JTextField();

		JLabel lblPuntos = new JLabel("Puntos / Número de Visitas:");

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modificarID.getText());
				String nombre = modificarNombre.getText();
				String dni = modificarDNI.getText();
				int puntosVisitas = Integer.parseInt(modificarPuntos.getText());
				TCliente cliente = new TCliente(id, nombre, dni, puntosVisitas);

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_CLIENTE, cliente));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y Puntos/Visitas deben ser números.");
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			dispose();
		});

		add(new JLabel("ID cliente:"));
		add(modificarID);
		add(new JLabel("Nombre:"));
		add(modificarNombre);
		add(new JLabel("DNI:"));
		add(modificarDNI);
		add(lblPuntos);
		add(modificarPuntos);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 350);
	    setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
			case VMODIFICAR_CLIENTE:
				this.setVisible(true);
				break;
			case RES_MODIFICAR_CLIENTE_OK:
				JOptionPane.showMessageDialog(null, "Cliente modificado con ID: " + datos);
				break;
			case RES_MODIFICAR_CLIENTE_KO:
				JOptionPane.showMessageDialog(null, "Error al modificar el cliente.");
				break;
			default:
				JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}
