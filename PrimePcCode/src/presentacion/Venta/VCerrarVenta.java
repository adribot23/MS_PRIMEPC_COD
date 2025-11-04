package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.Venta.TCarrito;
import negocio.Venta.TLineaVenta;
import negocio.Venta.TVenta;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VCerrarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final DefaultTableModel tableModel;
	private TCarrito carrito;

	public VCerrarVenta() {
		super("Cerrar venta");

		tableModel = new DefaultTableModel(new Object[] { "Id producto", "Cantidad" }, 0) {
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

		getRootPane().setBorder(BorderFactory.createTitledBorder("Cerrar venta"));
		setLayout(new BorderLayout(10, 10));

		JLabel infoLabel = new JLabel(
				"<html><center>Ha entrado en el proceso de compra<br>CERRAR VENTA para finalizar - VOLVER para salir</center></html>");
		add(infoLabel, BorderLayout.NORTH);

		JTable tabla = new JTable(tableModel);
		tabla.setFillsViewportHeight(true);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 10, 10));

		JButton anadirButton = new JButton("Añadir producto");
		anadirButton.setBackground(new Color(200, 255, 200));
		anadirButton.addActionListener(e -> onAnadir());
		botonesPanel.add(anadirButton);

		JButton eliminarButton = new JButton("Eliminar producto");
		eliminarButton.setBackground(new Color(255, 200, 200));
		eliminarButton.addActionListener(e -> onEliminar());
		botonesPanel.add(eliminarButton);

		JButton cerrarButton = new JButton("Cerrar venta");
		cerrarButton.setBackground(new Color(200, 255, 200));
		cerrarButton.addActionListener(e -> onCerrar());
		botonesPanel.add(cerrarButton);

		JButton volverButton = new JButton("Volver");
		volverButton.setBackground(new Color(255, 220, 220));
		volverButton.addActionListener(e -> volver());
		botonesPanel.add(volverButton);

		add(botonesPanel, BorderLayout.SOUTH);

		setSize(500, 400);
		setLocationRelativeTo(null);
	}

	private void onAnadir() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_INSERTAR, carrito));
			dispose();
		}
	}

	private void onEliminar() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_ELIMINAR, carrito));
			dispose();
		}
	}

	private void onCerrar() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.CERRAR_VENTA, carrito));
			dispose();
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VCERRAR_VENTA:
			tableModel.setRowCount(0);
			setVisible(true);
			break;
		case RES_PASAR_CARRITO_A_CERRAR_OK:
			if (datos instanceof TCarrito) {
				carrito = (TCarrito) datos;
				actualizarTabla();
			}
			break;
		case RES_PASAR_CARRITO_A_CERRAR_KO:
			JOptionPane.showMessageDialog(this, "Error en el traspaso del carrito.");
			dispose();
			break;
		case RES_CERRAR_VENTA_OK:
			if (datos instanceof TCarrito) {
				String factura = rellenarFactura((TCarrito) datos);
				JOptionPane.showMessageDialog(this, "Venta cerrada." + factura);
			} else {
				JOptionPane.showMessageDialog(this, "Venta cerrada correctamente.");
			}
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			dispose();
			break;
		case RES_CERRAR_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Error al cerrar venta.");
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			dispose();
			break;
		default:
			break;
		}
	}

	private void actualizarTabla() {
		tableModel.setRowCount(0);
		if (carrito != null && carrito.getLineasVenta() != null) {
			for (TLineaVenta linea : carrito.getLineasVenta()) {
				Object[] fila = { linea.get_producto(), linea.get_num_unidades() };
				tableModel.addRow(fila);
			}
		}
	}

	private String rellenarFactura(TCarrito carrito) {
		TVenta venta = carrito.getVenta();
		java.util.Set<TLineaVenta> lineasVenta = carrito.getLineasVenta();

		StringBuilder builder = new StringBuilder();
		if (venta != null) {
			builder.append("\n\nVenta finalizada - ID: ").append(venta.getId());
			builder.append("\nEmpleado: ").append(venta.getIdEmpleado());
			builder.append("\nCliente: ").append(venta.getIdCliente());
			builder.append("\nPrecio total: ").append(venta.getPrecio()).append(" EUR");
			if (venta.getDescuento() != null && venta.getDescuento() > 0) {
				builder.append("\nDescuento: ").append(venta.getDescuento()).append(" EUR");
			}
			builder.append("\n--------------------");
		}

		if (lineasVenta != null && !lineasVenta.isEmpty()) {
			for (TLineaVenta linea : lineasVenta) {
				builder.append("\nProducto: ").append(linea.get_producto());
				builder.append(" - Cantidad: ").append(linea.get_num_unidades());
				builder.append(" - Precio: ").append(linea.get_precio_unidades()).append(" EUR");
				builder.append("\n--------------------");
			}
		}

		return builder.toString();
	}
}
