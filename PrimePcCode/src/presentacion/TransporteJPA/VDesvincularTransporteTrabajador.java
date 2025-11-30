package presentacion.TransporteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.TransporteJPA.TTransporteTrabajador;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VDesvincularTransporteTrabajador extends JFrame implements IGUI {

	public VDesvincularTransporteTrabajador() {
		super("Desvincular Transporte de Trabajador");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(3, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Desvincular Transporte"));

		JLabel lblIdT = new JLabel("ID Transporte:");
		JTextField txtIdT = new JTextField();

		JLabel lblIdTrab = new JLabel("ID Trabajador:");
		JTextField txtIdTrab = new JTextField();

		JButton btnDesv = new JButton("Desvincular");
		btnDesv.setBackground(new Color(200, 255, 200));

		btnDesv.addActionListener(e -> {
			try {
				TTransporteTrabajador t = new TTransporteTrabajador();
				t.setId_transporte(Integer.parseInt(txtIdT.getText().trim()));
				t.setId_trabajador(Integer.parseInt(txtIdTrab.getText().trim()));

				Controlador.getInstancia().accion(new Context(Evento.DESVINCULAR_TRANSPORTE_TRABAJADOR, t));

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

		add(lblIdT);
		add(txtIdT);
		add(lblIdTrab);
		add(txtIdTrab);
		add(btnDesv);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VDESVINCULAR_TRANSPORTE_TRABAJADOR:
			setVisible(true);
			break;

		case RES_DESVINCULAR_TRANSPORTE_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(null, "Desvinculación realizada correctamente.");
			break;

		case RES_DESVINCULAR_TRANSPORTE_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(null, "Error al desvincular transporte.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
