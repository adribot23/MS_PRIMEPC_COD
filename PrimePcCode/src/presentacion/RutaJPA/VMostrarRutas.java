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
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarRutas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnMostrar, btnVolver;
	private JTable tabla;

	public VMostrarRutas() {
		super("Mostrar Rutas");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		// Panel superior con botones
		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
		panelBotones.setBorder(BorderFactory.createTitledBorder("Rutas"));

		btnMostrar = new JButton("Mostrar todas las rutas");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODAS_RUTAS, null)));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			dispose();
		});

		panelBotones.add(btnMostrar);
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
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_TODAS_RUTAS:
			setVisible(true);
			break;

		case RES_MOSTRAR_TODAS_RUTAS_OK:
			mostrarTabla((Set<TRuta>) datos);
			break;

		case RES_MOSTRAR_TODAS_RUTAS_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar las rutas.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}

	private void mostrarTabla(Set<TRuta> rutas) {
		String[] columnas = { "ID", "Origen", "Destino", "Distancia (km)", "Activo" };
		Object[][] datos = new Object[rutas.size()][columnas.length];

		int i = 0;
		for (TRuta ruta : rutas) {
			datos[i][0] = ruta.get_id();
			datos[i][1] = ruta.get_origen();
			datos[i][2] = ruta.get_destino();
			datos[i][3] = ruta.get_distancia();
			datos[i][4] = ruta.get_activo();
			i++;
		}

		JTable table = new JTable(datos, columnas);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Rutas", JOptionPane.PLAIN_MESSAGE);
	}
}
