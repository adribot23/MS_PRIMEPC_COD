package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	private final JTextField idClienteField = new JTextField(10);
	private final JTextField metodoPagoField = new JTextField(10);
	private final JTextField descuentoField = new JTextField(10);

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

		JPanel northPanel = new JPanel(new BorderLayout());

		JPanel camposPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		camposPanel.setBorder(BorderFactory.createTitledBorder("Datos de la venta"));
		camposPanel.add(new JLabel("ID Cliente:"));
		camposPanel.add(idClienteField);
		camposPanel.add(new JLabel("Metodo de pago:"));
		camposPanel.add(metodoPagoField);
		camposPanel.add(new JLabel("Descuento (EUR):"));
		camposPanel.add(descuentoField);
		northPanel.add(camposPanel, BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);

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

		setSize(550, 500);
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
			try {
				int idCliente = parseEnteroPositivo(idClienteField.getText(), "ID Cliente");
				String metodoPago = parseString(metodoPagoField.getText(), "Metodo de pago");
				double descuento = parseDecimalPositivo(descuentoField.getText(), "Descuento");

				TVenta venta = carrito.getVenta();
				if (venta == null) {
					venta = new TVenta();
				}
				venta.setIdCliente(idCliente);
				venta.setMetodoPago(metodoPago);
				venta.setDescuento(descuento);
				carrito.setVenta(venta);

				Controlador.getInstancia().accion(new Context(Evento.CERRAR_VENTA, carrito));
				dispose();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
	}

	private int parseEnteroPositivo(String value, String campo) {
		try {
			int numero = Integer.parseInt(value.trim());
			if (numero <= 0) {
				throw new NumberFormatException();
			}
			return numero;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(campo + " debe ser un numero entero positivo.");
		}
	}

	private String parseString(String value, String campo) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(campo + " no puede estar vacio.");
		}
		return value.trim();
	}

	private double parseDecimalPositivo(String value, String campo) {
		try {
			double numero = Double.parseDouble(value.trim());
			if (numero < 0) {
				throw new IllegalArgumentException(campo + " debe ser un numero positivo o cero.");
			}
			return numero;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(campo + " debe ser un numero decimal valido.");
		}
	}

	private void limpiarCampos() {
		idClienteField.setText("");
		metodoPagoField.setText("");
		descuentoField.setText("");
	}

	private void cerrarTodasLasVentanasDeEsteipo() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VCerrarVenta && window.isVisible()) {
				window.dispose();
			}
		}
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
			limpiarCampos();
			setVisible(true);
			break;
		case RES_PASAR_CARRITO_A_CERRAR_OK:
			if (datos instanceof TCarrito) {
				carrito = (TCarrito) datos;
				actualizarTabla();
				setVisible(true);
			}
			break;
		case RES_PASAR_CARRITO_A_CERRAR_KO:
			if (datos instanceof TCarrito) {
				carrito = (TCarrito) datos;
				actualizarTabla();
				setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Error en el traspaso del carrito.");
				dispose();
			}
			break;
		case RES_CERRAR_VENTA_OK:
			cerrarTodasLasVentanasDeEsteipo();
			if (datos instanceof TCarrito) {
				String factura = rellenarFactura((TCarrito) datos);
				JOptionPane.showMessageDialog(null, "Venta cerrada." + factura);
			} else {
				JOptionPane.showMessageDialog(null, "Venta cerrada correctamente.");
			}
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			break;
		case RES_CERRAR_VENTA_KO:
			cerrarTodasLasVentanasDeEsteipo();
			JOptionPane.showMessageDialog(null, "Error al cerrar venta.");
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
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
