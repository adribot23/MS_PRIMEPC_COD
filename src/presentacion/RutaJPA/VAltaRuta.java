package presentacion.RutaJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import negocio.RutaJPA.TRuta;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaRuta extends JFrame implements IGUI {

	public VAltaRuta() {
		super("Alta de Ruta");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Ruta"));

		JLabel lblOrigen = new JLabel("Origen:");
		JTextField txtOrigen = new JTextField();

		JLabel lblDestino = new JLabel("Destino:");
		JTextField txtDestino = new JTextField();

		JLabel lblDistancia = new JLabel("Distancia (km):");
		JSpinner spDistancia = new JSpinner(new SpinnerNumberModel(1.0, 0.1, Double.MAX_VALUE, 0.5));
		spDistancia.setEditor(new JSpinner.NumberEditor(spDistancia, "0.00"));

		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));

		btnAlta.addActionListener(e -> {

			try {
				String origen = txtOrigen.getText().trim();
				String destino = txtDestino.getText().trim();

				if (origen.isEmpty() || destino.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
					return;
				}

				double distancia = ((Number) spDistancia.getValue()).doubleValue();
				if (distancia <= 0) {
					JOptionPane.showMessageDialog(null, "La distancia debe ser positiva.");
					return;
				}

				TRuta ruta = new TRuta();
				ruta.set_origen(origen);
				ruta.set_destino(destino);
				ruta.set_distancia(distancia);

				Controlador.getInstancia().accion(new Context(Evento.ALTA_RUTA, ruta));

				txtOrigen.setText("");
				txtDestino.setText("");
				spDistancia.setValue(1.0);

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

		add(lblOrigen);
		add(txtOrigen);
		add(lblDestino);
		add(txtDestino);
		add(lblDistancia);
		add(spDistancia);
		add(btnAlta);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {

		case VALTA_RUTA:
			setVisible(true);
			break;

		case RES_ALTA_RUTA_OK:
			JOptionPane.showMessageDialog(null, "Ruta dada de alta con ID: " + context.getDatos());
			break;

		case RES_ALTA_RUTA_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta la ruta.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
