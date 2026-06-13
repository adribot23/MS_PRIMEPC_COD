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

public class VAltaCliente extends JFrame implements IGUI {

	public VAltaCliente() {
		super("Alta de Cliente");
		initGUI();
	}

	public void initGUI() {
		setLayout(new GridLayout(5, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Cliente"));

		// setBorder(BorderFactory.createTitledBorder("Alta Cliente"));

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
				String nombre = altaNombre.getText().trim();
				String dni = altaDNI.getText().trim();

				if (!nombre.isEmpty() && !dni.isEmpty()) {
					TCliente cliente;
					if (rdbSocio.isSelected()) {
						int puntos = Integer.parseInt(altaVisitas.getText().trim());
						cliente = new TClienteSocio(-1, nombre, dni, puntos);
					} else {
						int numVisitas = Integer.parseInt(altaVisitas.getText().trim());
						cliente = new TClienteNoSocio(nombre, dni, numVisitas);
					}

					Controlador.getInstancia().accion(new Context(Evento.ALTA_CLIENTE, cliente));

					rdbSocio.setSelected(true);
					lblVisitas.setText("Puntos:");
					altaNombre.setText("");
					altaDNI.setText("");
					altaVisitas.setText("");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los campos numéricos deben ser válidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(new JLabel("DNI:"));
		add(altaDNI);
		add(lblVisitas);
		add(altaVisitas);
		add(new JLabel("Tipo de Cliente:"));
		add(altaTipoPanel);
		add(btnAlta);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 250);
		setLocationRelativeTo(null);
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