package presentacion.TransporteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VModificarTransporte extends JFrame implements IGUI {

	public VModificarTransporte() {
		super("Modificar Transporte");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(5, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Transporte"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JLabel lblNombre = new JLabel("Nombre:");
		JTextField txtNombre = new JTextField();

		JLabel lblCapacidad = new JLabel("Capacidad:");
		JTextField txtCapacidad = new JTextField();

		JLabel lblMatricula = new JLabel("Matrícula:");
		JTextField txtMatricula = new JTextField();

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));

		btnModificar.addActionListener(e -> {

			try {
				int id = Integer.parseInt(txtId.getText().trim());
				String nombre = txtNombre.getText().trim();
				int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
				String matricula = txtMatricula.getText().trim();

				TTransporte t = new TTransporte();
				t.setId(id);
				t.setNombre(nombre);
				t.setCapacidad(capacidad);
				t.setMatricula(matricula);

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_TRANSPORTE, t));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Datos inválidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
			this.dispose();
		});

		add(lblId);
		add(txtId);
		add(lblNombre);
		add(txtNombre);
		add(lblCapacidad);
		add(txtCapacidad);
		add(lblMatricula);
		add(txtMatricula);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 260);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VMODIFICAR_TRANSPORTE:
			setVisible(true);
			break;

		case RES_MODIFICAR_TRANSPORTE_OK:
			JOptionPane.showMessageDialog(null, "Transporte modificado correctamente.");
			break;

		case RES_MODIFICAR_TRANSPORTE_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar transporte.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}