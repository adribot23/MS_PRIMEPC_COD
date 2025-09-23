
package presentacion.vista.empleado;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.util.Collection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import negocio.transfers.TEmpleado;
import negocio.transfers.TEmpleadoCompleto;
import negocio.transfers.TEmpleadoParcial;
import presentacion.vista.Evento;
import presentacion.vista.IGUI;

public class GUIEmpleado extends JPanel implements IGUI {

	private static final long serialVersionUID = 1L;
	
	public GUIEmpleado() {
		initGui();
	}

	private void initGui() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO EMPLEADO", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel izquierdo con buscar y modificar

		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarEmpleado());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarEmpleado());
		panelIzquierda.add(Box.createVerticalStrut(10));
		// Panel derecho con baja, mostrar y alta

		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaEmpleado());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VMostrarEmpleado());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaEmpleado());
		panelDerecha.add(Box.createVerticalStrut(10));

		panelCentral.add(panelIzquierda);
		panelCentral.add(panelDerecha);
		add(panelCentral, BorderLayout.CENTER);

		// Boton_Volver

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(30, 200, 100));
		btnVolver.addActionListener(e -> {
			CardLayout cl = (CardLayout) this.getParent().getLayout();
			cl.show(this.getParent(), "Menu");
		});

		add(btnVolver, BorderLayout.SOUTH);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Evento evento, Object datos) {
		switch (evento) {
		case RES_ALTA_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado dado de alta con ID: " + datos);
			break;
		case RES_ALTA_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el empleado.");
			break;
		case RES_BAJA_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado dado de baja correctamente.");
			break;
		case RES_BAJA_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el empleado.");
			break;
		case RES_MODIFICAR_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado modificado correctamente.");
			break;
		case RES_MODIFICAR_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar el empleado. Verifica los datos.");
			break;
		case RES_BUSCAR_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, (TEmpleado) datos);
			break;
		case RES_BUSCAR_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
			break;
		case RES_MOSTRAR_TODOS_EMPLEADOS_OK:
			mostrarTabla((Collection<TEmpleado>) datos);
			break;
		case RES_MOSTRAR_TODOS_EMPLEADOS_KO:
			JOptionPane.showMessageDialog(null, "No hay empleados para mostrar.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTabla(Collection<TEmpleado> empleados) {
		String[] columnNames = { "ID", "Nombre", "DNI", "Telefono", "Tipo", "Horas", "Activo" };

		Object[][] data = new Object[empleados.size()][columnNames.length];
		int i = 0;
		for (TEmpleado emp : empleados) {
			data[i][0] = emp.getId();
			data[i][1] = emp.getNombre();
			data[i][2] = emp.getDni();
			data[i][3] = emp.getTelefono();
			data[i][6] = emp.getActivo();
			if (emp instanceof TEmpleadoCompleto) {
				data[i][4] = "Completo";
				data[i][5] = ((TEmpleadoCompleto) emp).getHorasExtra() + " extra";
			} else {
				data[i][4] = "Parcial";
				data[i][5] = ((TEmpleadoParcial) emp).getHorasSemanales() + " semanales";
			}
			i++;
		}

		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);

		JOptionPane.showMessageDialog(null, scrollPane, "Empleados", JOptionPane.PLAIN_MESSAGE);
	}
}
