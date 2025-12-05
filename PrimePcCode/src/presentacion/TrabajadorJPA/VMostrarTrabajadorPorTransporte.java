package presentacion.TrabajadorJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.TrabajadorJPA.TTrabajador;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarTrabajadorPorTransporte extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	private JButton btnBuscar, btnVolver;
	private JTable tabla;

	public VMostrarTrabajadorPorTransporte() {
		super("Trabajador por Transporte");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		// Panel superior con botones
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(BorderFactory.createTitledBorder("Buscar Trabajadores por Transporte"));

		btnBuscar = new JButton("Buscar trabajador del transporte");
		btnBuscar.setBackground(new Color(200, 255, 200));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));

		panelBotones.add(btnBuscar);
		panelBotones.add(btnVolver);

		// Tabla con scroll
		tabla = new JTable();
		tabla.setFillsViewportHeight(true);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabla.setEnabled(false);
		JScrollPane scroll = new JScrollPane(tabla);

		// Layout principal
		setLayout(new BorderLayout(10, 10));
		add(panelBotones, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		// Eventos
		btnBuscar.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Introduce ID del transporte:");
			if (input != null) {
				try {
					int id = Integer.parseInt(input.trim());
					Controlador.getInstancia().accion(new Context(Evento.VVER_TRABAJADOR_POR_TRANSPORTE, id));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID inválido.");
				}
			}
		});

		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			dispose();
		});
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VVER_TRABAJADOR_POR_TRANSPORTE:
			setVisible(true);
			break;

		case RES_VER_TRABAJADOR_POR_TRANSPORTE_OK:
			mostrarTabla((Set<TTrabajador>) datos);
			break;

		case RES_VER_TRABAJADOR_POR_TRANSPORTE_KO:
			JOptionPane.showMessageDialog(this, "Este transporte no tiene trabajador asignado.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}

	private void mostrarTabla(Set<TTrabajador> trabajadores) {
		String[] columnas = { "ID", "Nombre", "DNI",  "Activo" };
		Object[][] datos = new Object[trabajadores.size()][columnas.length];

		int i = 0;
		for (TTrabajador t : trabajadores) {
			datos[i][0] = t.getId();
			datos[i][1] = t.getNombre();
			datos[i][2] = t.getDNI();
			datos[i][3] = t.isActivo();
			i++;
		}

		tabla.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
	}
}
