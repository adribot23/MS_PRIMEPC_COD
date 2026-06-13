package presentacion.RutaJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import negocio.RutaJPA.TRuta;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VModificarRuta extends JFrame implements IGUI {

	public VModificarRuta() {
		super("Modificar Ruta");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(5, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Ruta"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JLabel lblOrigen = new JLabel("Origen:");
		JTextField txtOrigen = new JTextField();

		JLabel lblDestino = new JLabel("Destino:");
		JTextField txtDestino = new JTextField();

		JLabel lblDistancia = new JLabel("Distancia (km):");
		JSpinner spDistancia = new JSpinner(new SpinnerNumberModel(1.0, 0.1, Double.MAX_VALUE, 0.5));
		spDistancia.setEditor(new JSpinner.NumberEditor(spDistancia, "0.00"));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));

		btnModificar.addActionListener(e -> {

			try {
				int id = Integer.parseInt(txtId.getText().trim());
				String origen = txtOrigen.getText().trim();
				String destino = txtDestino.getText().trim();
				double distancia = ((Number) spDistancia.getValue()).doubleValue();

				TRuta ruta = new TRuta();
				ruta.set_id(id);
				ruta.set_origen(origen);
				ruta.set_destino(destino);
				ruta.set_distancia(distancia);

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_RUTA, ruta));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Datos inválidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			this.dispose();
		});

		add(lblId);
		add(txtId);
		add(lblOrigen);
		add(txtOrigen);
		add(lblDestino);
		add(txtDestino);
		add(lblDistancia);
		add(spDistancia);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 260);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VMODIFICAR_RUTA:
			setVisible(true);
			break;

		case RES_MODIFICAR_RUTA_OK:
			JOptionPane.showMessageDialog(null, "Ruta modificada correctamente.");
			break;

		case RES_MODIFICAR_RUTA_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar ruta.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
