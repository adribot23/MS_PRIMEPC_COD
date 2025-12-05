package presentacion.RutaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import negocio.RutaJPA.TRuta;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerRutasPorTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idTrabajadorField = new JTextField();
	private final DefaultTableModel tableModel = new DefaultTableModel();

	public VVerRutasPorTrabajador() {
		super("Rutas por Trabajador");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));
		getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel filtroPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		filtroPanel.setBorder(BorderFactory.createTitledBorder("Filtrar por trabajador"));

		filtroPanel.add(new JLabel("ID Trabajador:"));
		filtroPanel.add(idTrabajadorField);

		JButton buscarBtn = new JButton("Buscar");
		buscarBtn.setBackground(new Color(200, 255, 200));
		buscarBtn.addActionListener(e -> buscar());
		filtroPanel.add(buscarBtn);

		add(filtroPanel, BorderLayout.NORTH);

		tableModel.setColumnIdentifiers(new String[] { "ID Ruta", "Origen", "Destino", "Distancia (km)" });
		JTable tabla = new JTable(tableModel);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		JButton volverBtn = new JButton("Volver");
		volverBtn.setBackground(new Color(255, 220, 220));
		volverBtn.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			dispose();
		});

		JPanel sur = new JPanel();
		sur.add(volverBtn);
		add(sur, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void buscar() {
		try {
			int id = Integer.parseInt(idTrabajadorField.getText().trim());
			if (id <= 0) {
				JOptionPane.showMessageDialog(this, "El identificador debe ser positivo.");
				return;
			}
			Controlador.getInstancia().accion(new Context(Evento.VER_RUTA_POR_TRABAJADOR, id));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Introduce un identificador válido.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VVER_RUTA_POR_TRABAJADOR:
			setVisible(true);
			break;
		case RES_VER_RUTA_POR_TRABAJADOR_OK:
			mostrarTabla((Set<TRuta>) context.getDatos());
			break;
		case RES_VER_RUTA_POR_TRABAJADOR_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron recuperar las rutas del trabajador.");
			break;
		default:
			break;
		}
	}

	private void mostrarTabla(Set<TRuta> rutas) {
		tableModel.setRowCount(0);
		if (rutas == null || rutas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El trabajador no tiene rutas asociadas.");
			return;
		}
		for (TRuta ruta : rutas) {
			tableModel.addRow(new Object[] { ruta.getId(), ruta.getOrigen(), ruta.getDestino(), ruta.getDistancia() });
		}
	}
}
