package presentacion.RutaJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.RutaJPA.TVinculacionRutaTrabajador;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VDesvincularRutaTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VDesvincularRutaTrabajador() {
		super("Desvincular Ruta y Trabajador");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Desvincular Ruta - Trabajador"));

		JLabel lblRuta = new JLabel("ID Ruta:");
		JTextField txtRuta = new JTextField();

		JLabel lblTrabajador = new JLabel("ID Trabajador:");
		JTextField txtTrabajador = new JTextField();

		JButton btnDesvincular = new JButton("Desvincular");
		btnDesvincular.setBackground(new Color(200, 255, 200));
		btnDesvincular.addActionListener(e -> {
			try {
				int idRuta = Integer.parseInt(txtRuta.getText().trim());
				int idTrabajador = Integer.parseInt(txtTrabajador.getText().trim());

				if (idRuta <= 0 || idTrabajador <= 0) {
					JOptionPane.showMessageDialog(this, "Los identificadores deben ser positivos.");
					return;
				}

				TVinculacionRutaTrabajador vinculacion = new TVinculacionRutaTrabajador();
				vinculacion.setIdRuta(idRuta);
				vinculacion.setIdTrabajador(idTrabajador);

				Controlador.getInstancia().accion(
						new Context(Evento.DESVINCULAR_RUTA_TRABAJADOR, vinculacion));

				txtRuta.setText("");
				txtTrabajador.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los IDs deben ser números enteros.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			dispose();
		});

		add(lblRuta);
		add(txtRuta);
		add(lblTrabajador);
		add(txtTrabajador);
		add(btnDesvincular);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(360, 180);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VDESVINCULAR_RUTA_TRABAJADOR:
			setVisible(true);
			break;
		case RES_DESVINCULAR_RUTA_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(this, "Desvinculación realizada correctamente.");
			break;
		case RES_DESVINCULAR_RUTA_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(this, "No se pudo realizar la desvinculación.");
			break;
		default:
			break;
		}
	}
}
