package presentacion.TrabajadorJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.TrabajadorJPA.TTrabajador;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaTrabajador extends JFrame implements IGUI{

	public VAltaTrabajador() {
		super("Alta de Trabajador");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Trabajador"));

		JLabel lblNombre = new JLabel("Nombre:");
		JTextField txtNombre = new JTextField();

		JLabel lblDNI = new JLabel("DNI:");
		JTextField txtDNI = new JTextField();

		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));

		btnAlta.addActionListener(e -> {

			 try {
			String nombre = txtNombre.getText().trim();
			String DNI = txtDNI.getText().trim();

			if (nombre.isEmpty() || DNI.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
				return;
			}

			TTrabajador t = new TTrabajador(nombre, DNI,1);
			Controlador.getInstancia().accion(new Context(Evento.ALTA_TRABAJADOR, t));

			txtNombre.setText("");
			txtDNI.setText("");

			 } catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Datos inválidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		});

		add(lblNombre);
		add(txtNombre);
		add(lblDNI);
		add(txtDNI);
		add(btnAlta);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {

		case VALTA_TRABAJADOR:
			setVisible(true);
			break;

		case RES_ALTA_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(null, "Trabajador dado de alta con ID: " + context.getDatos());
			break;

		case RES_ALTA_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el trabajador.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
