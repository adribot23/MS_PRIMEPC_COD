package presentacion.RutaJPA;

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

public class VBajaRuta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VBajaRuta() {
		super("Baja de Ruta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Ruta"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JButton btnBaja = new JButton("Dar de Baja");
		btnBaja.setBackground(new Color(200, 255, 200));
		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_RUTA, id));
				txtId.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			dispose();
		});

		add(lblId);
		add(txtId);
		add(btnBaja);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(320, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VBAJA_RUTA:
			setVisible(true);
			break;
		case RES_BAJA_RUTA_OK:
			JOptionPane.showMessageDialog(this, "Ruta dada de baja correctamente.");
			break;
		case RES_BAJA_RUTA_KO:
			JOptionPane.showMessageDialog(this, "No se pudo dar de baja la ruta.");
			break;
		default:
			break;
		}
	}
}
