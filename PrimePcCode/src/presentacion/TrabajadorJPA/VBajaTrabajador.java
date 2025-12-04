package presentacion.TrabajadorJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBajaTrabajador extends JFrame implements IGUI {

	public VBajaTrabajador() {
		super("Baja de Trabajador");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Trabajador"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JButton btnBaja = new JButton("Dar de Baja");
		btnBaja.setBackground(new Color(200, 255, 200));

		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_TRABAJADOR, id));
				txtId.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ID inválido.");
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
		add(btnBaja);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VBAJA_TRABAJADOR:
			setVisible(true);
			break;

		case RES_BAJA_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(null, "Trabajador dado de baja correctamente.");
			break;

		case RES_BAJA_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el trabajador.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
