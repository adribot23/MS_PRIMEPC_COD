package presentacion.TransporteJPA;

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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarTransporte extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnMostrar, btnVolver;

	public VMostrarTransporte() {
		super("Mostrar Transportes");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Transportes"));

		btnMostrar = new JButton("Mostrar todos los transportes");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_TRANSPORTES, null)));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
			dispose();
		});

		panel.add(btnMostrar);
		panel.add(btnVolver);
		add(panel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_TODOS_TRANSPORTES:
			setVisible(true);
			break;
		case RES_MOSTRAR_TODOS_TRANSPORTES_OK:
			mostrarTabla((Set<TTransporte>) datos);
			break;
		case RES_MOSTRAR_TODOS_TRANSPORTES_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar los transportes.");
			break;
		default:
			break;
		}
	}

	private void mostrarTabla(Set<TTransporte> transportes) {
		String[] columnNames = { "ID", "Nombre", "Capacidad", "Matrícula", "Activo" };
		Object[][] tableData = new Object[transportes.size()][columnNames.length];

		int i = 0;
		for (TTransporte t : transportes) {
			tableData[i][0] = t.getId();
			tableData[i][1] = t.getNombre();
			tableData[i][2] = t.getCapacidad();
			tableData[i][3] = t.getMatricula();
			tableData[i][4] = t.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Transportes", JOptionPane.PLAIN_MESSAGE);
	}
}