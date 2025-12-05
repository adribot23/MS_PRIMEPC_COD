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

public class VVincularRutaTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VVincularRutaTrabajador() {
		super("Vincular Ruta con Trabajador");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(6, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Vincular Ruta - Trabajador"));

		JLabel lblRuta = new JLabel("ID Ruta:");
		JTextField txtRuta = new JTextField();

		JLabel lblTrabajador = new JLabel("ID Trabajador:");
		JTextField txtTrabajador = new JTextField();

		JLabel lblHora = new JLabel("Hora de salida:");
		JTextField txtHora = new JTextField();

		JLabel lblEstado = new JLabel("Estado:");
		JTextField txtEstado = new JTextField();

		JLabel lblFecha = new JLabel("Fecha asignación:");
		JTextField txtFecha = new JTextField();

		JButton btnVincular = new JButton("Vincular");
		btnVincular.setBackground(new Color(200, 255, 200));
		btnVincular.addActionListener(e -> {
			try {
				int idRuta = Integer.parseInt(txtRuta.getText().trim());
				int idTrabajador = Integer.parseInt(txtTrabajador.getText().trim());
				String hora = txtHora.getText().trim();
				String estado = txtEstado.getText().trim();
				String fecha = txtFecha.getText().trim();

				if (idRuta <= 0 || idTrabajador <= 0) {
					JOptionPane.showMessageDialog(this, "Los identificadores deben ser positivos.");
					return;
				}

				if (hora.isEmpty() || estado.isEmpty() || fecha.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Rellena todos los campos.");
					return;
				}

				TVinculacionRutaTrabajador vinculacion = new TVinculacionRutaTrabajador();
				vinculacion.setIdRuta(idRuta);
				vinculacion.setIdTrabajador(idTrabajador);
				vinculacion.setHoraSalida(hora);
				vinculacion.setEstado(estado);
				vinculacion.setFechaAsignacion(fecha);

				Controlador.getInstancia().accion(
						new Context(Evento.VINCULAR_RUTA_TRABAJADOR, vinculacion));

				txtRuta.setText("");
				txtTrabajador.setText("");
				txtHora.setText("");
				txtEstado.setText("");
				txtFecha.setText("");

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
		add(lblHora);
		add(txtHora);
		add(lblEstado);
		add(txtEstado);
		add(lblFecha);
		add(txtFecha);
		add(btnVincular);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(420, 280);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VAVINCULAR_RUTA_TRABAJADOR:
			setVisible(true);
			break;
		case RES_VINCULAR_RUTA_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(this, "Vinculación realizada correctamente.");
			break;
		case RES_VINCULAR_RUTA_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(this, "No se pudo realizar la vinculación.");
			break;
		default:
			break;
		}
	}
}
