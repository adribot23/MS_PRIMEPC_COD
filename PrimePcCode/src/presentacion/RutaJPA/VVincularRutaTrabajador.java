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

	public VVincularRutaTrabajador() {
		super("Vincular Ruta a Trabajador");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(6, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Vincular Ruta"));

		JLabel lblIdR = new JLabel("ID Ruta:");
		JTextField txtIdR = new JTextField();

		JLabel lblIdTrab = new JLabel("ID Trabajador:");
		JTextField txtIdTrab = new JTextField();

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
				TVinculacionRutaTrabajador t = new TVinculacionRutaTrabajador();
				t.set_id_ruta(Integer.parseInt(txtIdR.getText().trim()));
				t.set_id_trabajador(Integer.parseInt(txtIdTrab.getText().trim()));
				t.set_hora_salida(txtHora.getText().trim());
				t.set_estado(txtEstado.getText().trim());
				t.set_fecha_asignacion(txtFecha.getText().trim());

				Controlador.getInstancia().accion(new Context(Evento.VINCULAR_RUTA_TRABAJADOR, t));

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

		add(lblIdR);
		add(txtIdR);
		add(lblIdTrab);
		add(txtIdTrab);
		add(lblHora);
		add(txtHora);
		add(lblEstado);
		add(txtEstado);
		add(lblFecha);
		add(txtFecha);
		add(btnVincular);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(350, 280);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {

		switch (context.getEvento()) {

		case VAVINCULAR_RUTA_TRABAJADOR:
			setVisible(true);
			break;

		case RES_VINCULAR_RUTA_TRABAJADOR_OK:
			JOptionPane.showMessageDialog(null, "Vinculación realizada correctamente.");
			break;

		case RES_VINCULAR_RUTA_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(null, "Error al vincular ruta.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}
