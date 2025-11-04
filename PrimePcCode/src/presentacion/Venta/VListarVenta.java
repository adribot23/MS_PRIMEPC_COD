package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.Venta.TVenta;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VListarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final DefaultTableModel tableModel;

	public VListarVenta() {
		super("Listado de ventas");

		tableModel = new DefaultTableModel(
				new Object[] { "Id", "Empleado", "Cliente", "Metodo pago", "Precio", "Descuento", "Activa" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		getRootPane().setBorder(BorderFactory.createTitledBorder("Listado de ventas"));
		setLayout(new BorderLayout(10, 10));

		JTable tabla = new JTable(tableModel);
		tabla.setFillsViewportHeight(true);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		JButton volverButton = new JButton("Volver");
		volverButton.setBackground(new Color(255, 220, 220));
		volverButton.addActionListener(e -> volver());
		JPanel southPanel = new JPanel();
		southPanel.add(volverButton);
		add(southPanel, BorderLayout.SOUTH);

		setSize(760, 380);
		setLocationRelativeTo(null);
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
	}

	private void actualizarTabla(Object datos) {
		tableModel.setRowCount(0);
		for (TVenta venta : convertirVentas(datos)) {
			Object[] fila = { venta.getId(), venta.getIdEmpleado(), venta.getIdCliente(), venta.getMetodoPago(),
					venta.getPrecio(), venta.getDescuento(), venta.getActivo() == 1 ? "Si" : "No" };
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
			setVisible(true);
			break;
		default:
			break;
		}
	}
}
