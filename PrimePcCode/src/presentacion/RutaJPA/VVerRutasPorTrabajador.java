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

import negocio.RutaJPA.TRuta;
import negocio.RutaJPA.TVinculacionRutaTrabajador;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerRutasPorTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnBuscar, btnVolver;
	private JTable tabla;

	public VVerRutasPorTrabajador() {
		super("Rutas por Trabajador");
		initGUI();
	}

	private void initGUI() {

		// Panel superior con botones
		JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
		panelBotones.setBorder(BorderFactory.createTitledBorder("Buscar Rutas por Trabajador"));

		btnBuscar = new JButton("Buscar rutas del trabajador");
		btnBuscar.setBackground(new Color(200, 255, 200));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		
		// Eventos
		btnBuscar.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Introduce ID del trabajador:");
			if (input != null) {
				try {
					int id = Integer.parseInt(input.trim());
					Controlador.getInstancia().accion(new Context(Evento.VER_RUTA_POR_TRABAJADOR, id));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID inválido.");
				}
			}
		});

		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
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
		case VVER_RUTA_POR_TRABAJADOR:
			setVisible(true);
			break;

		case RES_VER_RUTA_POR_TRABAJADOR_OK:
			mostrarTabla((Set<TVinculacionRutaTrabajador>) datos);
			break;

		case RES_VER_RUTA_POR_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(this, "Este trabajador no tiene rutas asignadas.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}

	private void mostrarTabla(Set<TVinculacionRutaTrabajador> rutas) {
		String[] columnas = { "ID_Ruta", "ID_Trabajador", "Hora Salida", "Estado", "Fecha de Asignación"  };
		Object[][] datos = new Object[rutas.size()][columnas.length];

		int i = 0;
		for (TVinculacionRutaTrabajador ruta : rutas) {
			datos[i][0] = ruta.get_id_ruta();
			datos[i][1] = ruta.get_id_trabajador();
			datos[i][2] = ruta.get_hora_salida();
			datos[i][3] = ruta.get_estado();
			datos[i][4] = ruta.get_fecha_asignacion();
			i++;
		}

		JTable table = new JTable(datos, columnas);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Rutas por Trabajador", JOptionPane.PLAIN_MESSAGE);
	}
}
