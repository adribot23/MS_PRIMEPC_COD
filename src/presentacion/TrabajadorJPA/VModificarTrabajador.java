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

public class VModificarTrabajador extends JFrame implements IGUI {

	public VModificarTrabajador() {
		super("Modificar Trabajador");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Trabajdor"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JLabel lblNombre = new JLabel("Nombre:");
		JTextField txtNombre = new JTextField();

		JLabel lblDNI = new JLabel("DNI:");
		JTextField txtDNI = new JTextField();

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));

		btnModificar.addActionListener(e -> {

			try {
				int id = Integer.parseInt(txtId.getText().trim());
				String nombre = txtNombre.getText().trim();
				String DNI = txtDNI.getText().trim();

				TTrabajador t = new TTrabajador();
				t.setId(id);
				t.setNombre(nombre);
				t.setDNI(DNI);

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_TRABAJADOR, t));

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

		add(lblId);
		add(txtId);
		add(lblNombre);
		add(txtNombre);
		add(lblDNI);
		add(txtDNI);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 260);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VMODIFICAR_TRABAJADOR:
			setVisible(true);
			break;

		case RES_MODIFICAR_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(null, "Trabajador modificado correctamente.");
			break;

		case RES_MODIFICAR_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar trabjador.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
