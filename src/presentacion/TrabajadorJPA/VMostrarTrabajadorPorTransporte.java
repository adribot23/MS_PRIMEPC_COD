package presentacion.TrabajadorJPA;

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

import negocio.TrabajadorJPA.TTrabajador;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarTrabajadorPorTransporte extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnBuscar, btnVolver;
	private JTable tabla;

	public VMostrarTrabajadorPorTransporte() {
		super("Trabajador por Transporte");
		initGUI();
	}

	private void initGUI() {

		// Panel superior con botones
		JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
		panelBotones.setBorder(BorderFactory.createTitledBorder("Buscar Trabajadores por Transporte"));

		btnBuscar = new JButton("Buscar trabajador del transporte");
		btnBuscar.setBackground(new Color(200, 255, 200));
		btnBuscar.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Introduce ID del transporte:");
			if (input != null) {
				try {
					int id = Integer.parseInt(input.trim());
					Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TRABAJADOR_POR_TRANSPORTE, id));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID inválido.");
				}
			}
		});

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			dispose();
		});

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
		case VVER_TRABAJADOR_POR_TRANSPORTE:
			setVisible(true);
			break;

		case RES_MOSTRAR_TRABAJADOR_POR_TRANSPORTE_OK:
			mostrarTabla((Set<TTrabajador>) datos);
			break;

		case RES_MOSTRAR_TRABAJADOR_POR_TRANSPORTE_KO:
			JOptionPane.showMessageDialog(null, "Este transporte no tiene trabajador asignado.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}

	private void mostrarTabla(Set<TTrabajador> trabajadores) {
		String[] columnas = { "ID", "Nombre", "DNI", "Activo" };
		Object[][] datos = new Object[trabajadores.size()][columnas.length];

		int i = 0;
		for (TTrabajador t : trabajadores) {
			datos[i][0] = t.getId();
			datos[i][1] = t.getNombre();
			datos[i][2] = t.getDNI();
			datos[i][3] = t.isActivo();
			i++;
		}

		JTable table = new JTable(datos, columnas);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Trabajadores del Transporte", JOptionPane.PLAIN_MESSAGE);
	}
}
