/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
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
public class VAltaCliente extends JFrame implements IGUI {

	public VAltaCliente() {
		super("Alta de Cliente");
		initGUI();
		setSize(400, 400);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	public void initGUI() {
		setLayout(new GridLayout(10, 1));
		//setBorder(BorderFactory.createTitledBorder("Alta Cliente"));

		JTextField altaNombre = new JTextField();
		JTextField altaDNI = new JTextField();
		JTextField altaVisitas = new JTextField();

		JLabel lblVisitas = new JLabel("Puntos:");

		JRadioButton rdbSocio = new JRadioButton("Socio");
		JRadioButton rdbNoSocio = new JRadioButton("No Socio");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbSocio);
		grupoTipo.add(rdbNoSocio);
		rdbSocio.setSelected(true);
		
		rdbSocio.addActionListener(e -> lblVisitas.setText("Puntos:"));
		rdbNoSocio.addActionListener(e -> lblVisitas.setText("Numero de visitas:"));

		JPanel altaTipoPanel = new JPanel(new GridLayout(1, 2));
		altaTipoPanel.add(rdbSocio);
		altaTipoPanel.add(rdbNoSocio);

		JButton btnAlta = new JButton("Dar de alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {
				String nombre = altaNombre.getText();
				String dni = altaDNI.getText();
				TCliente cliente;

				if (rdbSocio.isSelected()) {
					int puntos = Integer.parseInt(altaVisitas.getText());
					cliente = new TClienteSocio(-1, nombre, dni, puntos);
				} else {
					int numVisitas = Integer.parseInt(altaVisitas.getText());
					cliente = new TClienteNoSocio(nombre, dni, numVisitas);
				}

				Controlador.getInstancia().accion(new Context(Evento.ALTA_CLIENTE, cliente));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los campos numéricos deben ser válidos.");
			}
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(new JLabel("DNI:"));
		add(altaDNI);
		add(altaTipoPanel);
		add(lblVisitas);
		add(altaVisitas);
		add(btnAlta);
	}

	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
		case VALTA_CLIENTE:
			this.setVisible(true);
			break;
		case RES_ALTA_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, "Cliente dado de alta con ID: " + datos);
			break;
		case RES_ALTA_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el cliente.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}