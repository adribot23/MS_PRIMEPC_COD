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
import javax.swing.table.DefaultTableModel;

import negocio.RutaJPA.TRuta;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarRutas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTable tabla;

	public VMostrarRutas() {
		super("Listado de Rutas");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
		panelBotones.setBorder(BorderFactory.createTitledBorder("Rutas"));

		JButton btnMostrar = new JButton("Mostrar todas las rutas");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODAS_RUTAS, null)));

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			dispose();
		});

		panelBotones.add(btnMostrar);
		panelBotones.add(btnVolver);

		tabla = new JTable();
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(tabla);

		setLayout(new BorderLayout(10, 10));
		add(panelBotones, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VMOSTRAR_TODAS_RUTAS:
			setVisible(true);
			break;
		case RES_MOSTRAR_TODAS_RUTAS_OK:
			mostrarTabla((Set<TRuta>) context.getDatos());
			break;
		case RES_MOSTRAR_TODAS_RUTAS_KO:
			JOptionPane.showMessageDialog(this, "No hay rutas para mostrar.");
			break;
		default:
			break;
		}
	}

	private void mostrarTabla(Set<TRuta> rutas) {
		String[] columnas = { "ID", "Origen", "Destino", "Distancia (km)", "Activo" };
		Object[][] datos = new Object[rutas.size()][columnas.length];
		int i = 0;
		for (TRuta ruta : rutas) {
			datos[i][0] = ruta.getId();
			datos[i][1] = ruta.getOrigen();
			datos[i][2] = ruta.getDestino();
			datos[i][3] = ruta.getDistancia();
			datos[i][4] = ruta.getActivo();
			i++;
		}
		tabla.setModel(new DefaultTableModel(datos, columnas));
	}
}
