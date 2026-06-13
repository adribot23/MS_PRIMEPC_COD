package presentacion.RutaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.RutaJPA.TVinculacionRutaTrabajador;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerTrabajadoresPorRuta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnBuscar, btnVolver;
	private JTable tabla;

	public VVerTrabajadoresPorRuta() {
		super("Trabajadores por Ruta");
		initGUI();
	}

	private void initGUI() {

		// Panel superior con botones
		JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
		panelBotones.setBorder(BorderFactory.createTitledBorder("Buscar Trabajadores por Ruta"));

		btnBuscar = new JButton("Buscar trabajadores de la ruta");
		btnBuscar.setBackground(new Color(200, 255, 200));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));




		// Eventos
		btnBuscar.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Introduce ID de la ruta:");
			if (input != null) {
				try {
					int id = Integer.parseInt(input.trim());
					Controlador.getInstancia().accion(new Context(Evento.VER_TRABAJADOR_POR_RUTA, id));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID inválido.");
				}
			}
		});

		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			dispose();
		});
		

		// Layout principal
		panelBotones.add(btnBuscar);
		panelBotones.add(btnVolver);
		add(panelBotones);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VVER_TRABAJADOR_POR_RUTA:
			setVisible(true);
			break;

		case RES_VER_TRABAJADOR_POR_RUTA_OK:
			mostrarTabla((Set<TVinculacionRutaTrabajador>) datos);
			break;

		case RES_VER_TRABAJADOR_POR_RUTA_KO:
			JOptionPane.showMessageDialog(this, "Esta ruta no tiene trabajadores asignados.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}

	private void mostrarTabla(Set<TVinculacionRutaTrabajador> trabajadores) {
		String[] columnas = { "ID_Ruta", "ID_Trabajador", "Hora Salida", "Estado", "Fecha de Asignación" };
		Object[][] datos = new Object[trabajadores.size()][columnas.length];

		int i = 0;
		for (TVinculacionRutaTrabajador t : trabajadores) {
			datos[i][0] = t.get_id_ruta();
			datos[i][1] = t.get_id_trabajador();
			datos[i][2] = t.get_hora_salida();
			datos[i][3] = t.get_estado();
			datos[i][4] = t.get_fecha_asignacion();
			i++;
		}

		JTable table = new JTable(datos, columnas);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Trabajadores por Ruta", JOptionPane.PLAIN_MESSAGE);
	}
}
