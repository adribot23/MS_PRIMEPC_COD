package presentacion.Empleado;

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

import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton btnMostrar, btnVolver;

	public VMostrarEmpleado() {
		super("Mostrar Empleados");
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Empleados"));

		btnMostrar = new JButton("Mostrar todos los empleados");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_EMPLEADOS, null)));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			dispose();
		});

		panel.add(btnMostrar);
		panel.add(btnVolver);

		add(panel);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_TODOS_EMPLEADOS:
			setVisible(true);
			break;

		case RES_MOSTRAR_TODOS_EMPLEADOS_OK:
			mostrarTabla((Set<TEmpleado>) datos);
			break;

		case RES_MOSTRAR_TODOS_EMPLEADOS_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar los empleados.");
			break;

		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}

	private void mostrarTabla(Set<TEmpleado> empleados) {
		if (empleados == null || empleados.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay empleados registrados.");
			return;
		}

		String[] columnNames = { "ID", "Nombre", "DNI", "Teléfono", "Tipo", "Horas", "Activo" };
		Object[][] tableData = new Object[empleados.size()][columnNames.length];

		int i = 0;
		for (TEmpleado e : empleados) {
			tableData[i][0] = e.getId();
			tableData[i][1] = e.getNombre();
			tableData[i][2] = e.getDni();
			tableData[i][3] = e.getTelefono();

			if (e instanceof TEmpleadoCompleto) {
				tableData[i][4] = "Completo";
				tableData[i][5] = ((TEmpleadoCompleto) e).getHorasExtra();
			} else if (e instanceof TEmpleadoParcial) {
				tableData[i][4] = "Parcial";
				tableData[i][5] = ((TEmpleadoParcial) e).getHorasSemanales();
			} else {
				tableData[i][4] = "Desconocido";
				tableData[i][5] = "-";
			}

			tableData[i][6] = e.getActivo(); 
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Empleados", JOptionPane.PLAIN_MESSAGE);
	}
}
