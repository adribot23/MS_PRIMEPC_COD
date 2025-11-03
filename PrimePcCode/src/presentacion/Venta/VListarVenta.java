package presentacion.Venta;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.Venta.TVenta;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VListarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final DefaultTableModel tableModel;

	public VListarVenta() {
		super("Listado de ventas");

		tableModel = new DefaultTableModel(
				new Object[] { "Id", "Empleado", "Cliente", "Método de pago", "Precio", "Descuento", "Activo" }, 0) {
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
		setLayout(new BorderLayout());

		JTable tabla = new JTable(tableModel);
		tabla.setFillsViewportHeight(true);

		add(new JScrollPane(tabla), BorderLayout.CENTER);

		setSize(720, 360);
		setLocationRelativeTo(null);
	}

	private void actualizarTabla(Object datos) {
		tableModel.setRowCount(0);
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
			List<TVenta> lista = new ArrayList<>();
			for (Object elemento : (Collection<Object>) datos) {
				if (elemento instanceof TVenta) {
					lista.add((TVenta) elemento);
				}
			}
			return lista;
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
		case VMOSTRAR_TODAS_VENTAS:
			tableModel.setRowCount(0);
			setVisible(true);
			break;
		case RES_MOSTRAR_TODAS_VENTAS_OK:
			actualizarTabla(datos);
			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "No hay ventas registradas.", "Listado de ventas",
						JOptionPane.INFORMATION_MESSAGE);
			}
			setVisible(true);
			break;
		case RES_MOSTRAR_TODAS_VENTAS_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo recuperar el listado de ventas.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al listar ventas", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}
