package presentacion.RutaJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.RutaJPA.TRuta;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarRuta extends JFrame implements IGUI {

	public VBuscarRuta() {
		super("Buscar Ruta");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Ruta"));

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_RUTA, id));
				txtId.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ID inválido.");
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
		add(btnBuscar);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VBUSCAR_RUTA:
			setVisible(true);
			break;

		case RES_BUSCAR_RUTA_OK:
			TRuta ruta = (TRuta) context.getDatos();
			JOptionPane.showMessageDialog(null, "ID: " + ruta.get_id() + "\nOrigen: " + ruta.get_origen()
					+ "\nDestino: " + ruta.get_destino() + "\nDistancia: " + ruta.get_distancia()
					+ " km\nActivo: " + ruta.get_activo());
			break;

		case RES_BUSCAR_RUTA_KO:
			JOptionPane.showMessageDialog(null, "No existe la ruta solicitada.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
