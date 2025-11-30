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

	private static final long serialVersionUID = 1L;

	public VModificarRuta() {
		super("Modificar Ruta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(4, 2, 10, 10));
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

				if (origen.isEmpty() || destino.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Rellena todos los campos.");
					return;
				}

				double distancia = ((Number) spDistancia.getValue()).doubleValue();
				if (distancia <= 0) {
					JOptionPane.showMessageDialog(this, "La distancia debe ser positiva.");
					return;
				}

				TRuta ruta = new TRuta();
				ruta.setId(id);
				ruta.setOrigen(origen);
				ruta.setDestino(destino);
				ruta.setDistancia(distancia);

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_RUTA, ruta));

				txtId.setText("");
				txtOrigen.setText("");
				txtDestino.setText("");
				spDistancia.setValue(1.0);
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
		add(lblOrigen);
		add(txtOrigen);
		add(lblDestino);
		add(txtDestino);
		add(lblDistancia);
		add(spDistancia);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(420, 240);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VMODIFICAR_RUTA:
			setVisible(true);
			break;
		case RES_MODIFICAR_RUTA_OK:
			JOptionPane.showMessageDialog(this, "Ruta modificada correctamente.");
			break;
		case RES_MODIFICAR_RUTA_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar la ruta.");
			break;
		default:
			break;
		}
	}
}
