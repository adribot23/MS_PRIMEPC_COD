/**
 * 
 */
package presentacion.Empleado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class GUIEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIEmpleado() {
		super("[EMPLEADO]");
		initGUI();
	}

	private void initGUI() {
		// === PANEL PRINCIPAL ===
		JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		mainPanel.setBackground(Color.WHITE);
		this.setContentPane(mainPanel);

		// === CABECERA ===
		JLabel titulo = new JLabel("GESTIÓN DE EMPLEADOS", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 28));
		titulo.setForeground(new Color(0, 100, 0));
		titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		mainPanel.add(titulo, BorderLayout.NORTH);

		// === PANEL DE BOTONES ===
		JPanel botonesPanel = new JPanel(new GridLayout(2, 3, 20, 20));
		botonesPanel.setBackground(Color.WHITE);

		// Fila 1
		botonesPanel.add(crearBotonVerde("ALTA EMPLEADO", Evento.VALTA_EMPLEADO));
		botonesPanel.add(crearBotonVerde("BAJA EMPLEADO", Evento.VBAJA_EMPLEADO));
		botonesPanel.add(crearBotonVerde("MODIFICAR EMPLEADO", Evento.VMODIFICAR_EMPLEADO));

		// Fila 2
		botonesPanel.add(crearBotonVerde("BUSCAR EMPLEADO", Evento.VBUSCAR_EMPLEADO));
		botonesPanel.add(crearBotonVerde("LISTAR TODOS LOS EMPLEADOS", Evento.VMOSTRAR_TODOS_EMPLEADOS));
		botonesPanel.add(crearBotonVerde("IMPORTE TOTAL DEL EMPLEADO QUE MAS HA VENDIDO", Evento.VCALCULAR_MAS_VENDIDO));

		mainPanel.add(botonesPanel, BorderLayout.CENTER);

		// === PANEL INFERIOR (VOLVER) ===
		JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
		volver.setFont(new Font("Segoe UI", Font.BOLD, 16));
		volver.setBackground(Color.WHITE);
		volver.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
		volver.setFocusPainted(false);
		volver.setPreferredSize(new Dimension(300, 40));

		volver.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});

		JPanel volverPanel = new JPanel();
		volverPanel.setBackground(Color.WHITE);
		volverPanel.add(volver);
		mainPanel.add(volverPanel, BorderLayout.SOUTH);

		// === CONFIGURACIÓN FRAME ===
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(900, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JButton crearBotonVerde(String texto, Evento evento) {
		JButton boton = new JButton("<html><center>" + texto + "</center></html>");
		boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boton.setFocusPainted(false);
		boton.setBackground(Color.WHITE);
		boton.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));
		boton.setOpaque(true);
		boton.setPreferredSize(new Dimension(250, 100));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Efecto hover
		boton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				boton.setBackground(new Color(220, 255, 220));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				boton.setBackground(Color.WHITE);
			}
		});

		// Acción del botón
		boton.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(evento, null));
			this.dispose();
		});

		return boton;
	}

	@Override
	public void actualizar(Context context) {
		setVisible(true);
	}

}
/*
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
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
*/