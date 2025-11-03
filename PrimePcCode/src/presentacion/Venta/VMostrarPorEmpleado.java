package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import negocio.Venta.TVenta;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarPorEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idEmpleadoField = new JTextField();
	private final DefaultTableModel tableModel;

	public VMostrarPorEmpleado() {
		super("Ventas por empleado");

		tableModel = new DefaultTableModel(
				new Object[] { "Id venta", "Empleado", "Cliente", "Método pago", "Precio", "Descuento", "Activa" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		JPanel filtro = new JPanel(new java.awt.GridLayout(1, 3, 10, 10));
		filtro.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 0, 15));

		filtro.add(new JLabel("Id empleado:"));
		filtro.add(idEmpleadoField);

		JButton buscar = new JButton("Buscar");
		buscar.addActionListener(e -> onBuscar());
		filtro.add(buscar);

		add(filtro, BorderLayout.PAGE_START);

		JTable tabla = new JTable(tableModel);
		tabla.setFillsViewportHeight(true);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		setPreferredSize(new Dimension(720, 360));
		pack();
		setLocationRelativeTo(null);
	}

	private void onBuscar() {
		try {
			int idEmpleado = parseEnteroPositivo(idEmpleadoField.getText(), "Id empleado");
			Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_VENTAS_POR_EMPLEADO, idEmpleado));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int parseEnteroPositivo(String valor, String campo) {
		try {
			int numero = Integer.parseInt(valor.trim());
			if (numero <= 0) {
				throw new NumberFormatException();
			}
			return numero;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número entero positivo.");
		}
	}

	private void limpiarTabla() {
		tableModel.setRowCount(0);
	}

	private void actualizarTabla(Object datos) {
		limpiarTabla();
		for (TVenta venta : convertirVentas(datos)) {
			Object[] fila = { venta.getId(), venta.getIdEmpleado(), venta.getIdCliente(), venta.getMetodoPago(),
					venta.getPrecio(), venta.getDescuento(), venta.getActivo() == 1 ? "Sí" : "No" };
			tableModel.addRow(fila);
		}
	}

	@SuppressWarnings("unchecked")
	private Collection<TVenta> convertirVentas(Object datos) {
		if (datos == null) {
			return Collections.emptyList();
		}

		if (datos instanceof Collection<?>) {
			return (Collection<TVenta>) datos;
		}

		if (datos instanceof TVenta) {
			return Collections.singletonList((TVenta) datos);
		}

		return Collections.emptyList();
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_VENTAS_POR_EMPLEADO:
			limpiarTabla();
			setVisible(true);
			break;
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK:
			actualizarTabla(datos);
			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "El empleado no tiene ventas registradas.", "Ventas por empleado",
						JOptionPane.INFORMATION_MESSAGE);
			}
			setVisible(true);
			break;
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO:
			String mensaje = datos instanceof String ? (String) datos
					: "No se pudieron recuperar las ventas del empleado indicado.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al consultar ventas", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}
}
