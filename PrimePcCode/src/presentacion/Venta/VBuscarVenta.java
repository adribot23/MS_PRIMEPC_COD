package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.Empleado.TEmpleado;
import negocio.Producto.TProducto;
import negocio.Venta.TLineaVenta;
import negocio.Venta.TVenta;
import negocio.Venta.TVentaTOA;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();
	private final JTextArea resultadoArea = new JTextArea();

	public VBuscarVenta() {
		super("Buscar venta");
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

		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar venta"));
		setLayout(new BorderLayout(10, 10));

		JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		topPanel.add(new JLabel("Id venta:"));
		topPanel.add(idVentaField);
		JButton buscarButton = new JButton("Buscar");
		buscarButton.setBackground(new Color(200, 255, 200));
		buscarButton.addActionListener(e -> onBuscar());
		topPanel.add(buscarButton);
		add(topPanel, BorderLayout.NORTH);

		resultadoArea.setEditable(false);
		resultadoArea.setLineWrap(true);
		resultadoArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(resultadoArea);
		scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
		add(scroll, BorderLayout.CENTER);

		JButton volverButton = new JButton("Volver");
		volverButton.setBackground(new Color(255, 220, 220));
		volverButton.addActionListener(e -> volver());
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(volverButton);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void onBuscar() {
		try {
			int idVenta = Integer.parseInt(idVentaField.getText().trim());
			if (idVenta <= 0) {
				JOptionPane.showMessageDialog(this, "El Id venta debe ser un numero positivo.", "Datos incorrectos",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Controlador.getInstancia().accion(new Context(Evento.BUSCAR_VENTA, idVenta));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id venta debe ser un numero entero positivo.",
					"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
	}

	private void limpiar() {
		idVentaField.setText("");
		resultadoArea.setText("");
	}

	private VBuscarVenta obtenerVentanaOriginal() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VBuscarVenta && window.isVisible() && window != this) {
				return (VBuscarVenta) window;
			}
		}
		return null;
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_VENTA:
			limpiar();
			setVisible(true);
			break;
		case RES_BUSCAR_VENTA_OK:
			VBuscarVenta ventanaOriginal = obtenerVentanaOriginal();
			if (ventanaOriginal != null) {
				String factura = rellenarFactura(datos);
				ventanaOriginal.resultadoArea.setText(factura);
				dispose();
			} else {
				String factura = rellenarFactura(datos);
				resultadoArea.setText(factura);
				setVisible(true);
			}
			break;
		case RES_BUSCAR_VENTA_KO:
			VBuscarVenta ventanaOriginalKO = obtenerVentanaOriginal();
			if (ventanaOriginalKO != null) {
				dispose();
				JOptionPane.showMessageDialog(ventanaOriginalKO, "Venta no encontrada.");
			} else {
				JOptionPane.showMessageDialog(this, "Venta no encontrada.");
				setVisible(true);
			}
			break;
		default:
			break;
		}
	}

	private String rellenarFactura(Object datos) {
		if (datos == null) {
			return "No hay información disponible.";
		}

		if (datos instanceof TVentaTOA) {
			return formatearVentaTOA((TVentaTOA) datos);
		}

		if (datos instanceof TVenta) {
			return formatearVentaSimple((TVenta) datos);
		}

		return String.valueOf(datos);
	}

	private String formatearVentaSimple(TVenta venta) {
		if (venta == null) {
			return "Venta no disponible.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Venta encontrada con id: ").append(venta.getId()).append("\n");
		sb.append("Empleado: ").append(venta.getIdEmpleado()).append("\n");
		sb.append("Cliente: ").append(venta.getIdCliente()).append("\n");
		sb.append("Método de pago: ").append(venta.getMetodoPago() != null ? venta.getMetodoPago() : "-")
				.append("\n");
		sb.append("Precio total: ").append(venta.getPrecio()).append(" EUR\n");
		sb.append("Descuento: ").append(venta.getDescuento()).append(" EUR\n");
		sb.append("Activa: ").append(venta.getActivo() == 1 ? "Si" : "No");
		return sb.toString();
	}

	private String formatearVentaTOA(TVentaTOA toa) {
		StringBuilder builder = new StringBuilder();

		TVenta venta = toa.get_venta();
		TEmpleado empleado = toa.get_empleado();
		Set<TLineaVenta> lineasVenta = toa.get_lista_lineasVenta();
		Set<TProducto> productos = toa.get_lista_producto();

		String cabecera = rellenarCabecera(venta, empleado);
		String lineas = rellenarLineasVenta(lineasVenta, productos);

		return cabecera + lineas;
	}

	private String rellenarCabecera(TVenta venta, TEmpleado empleado) {
		StringBuilder builder = new StringBuilder();

		if (venta != null) {
			builder.append("Se ha encontrado venta con id: ").append(venta.getId()).append("\n");
		}

		if (empleado != null) {
			builder.append("Empleado: ").append(empleado.getId());
			builder.append(", DNI: ").append(empleado.getDni() != null ? empleado.getDni() : "-");
			builder.append(", nombre: ").append(empleado.getNombre() != null ? empleado.getNombre() : "-");
			builder.append(", activo: ").append(empleado.getActivo() == 1 ? "Si" : "No").append("\n");
		}

		if (venta != null) {
			builder.append("Total factura: ").append(venta.getPrecio()).append(" EUR\n");
			builder.append(
					"-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}

	private String rellenarLineasVenta(Set<TLineaVenta> lineasVenta, Set<TProducto> productos) {
		StringBuilder builder = new StringBuilder();

		if (lineasVenta == null || lineasVenta.isEmpty()) {
			return builder.toString();
		}

		Iterator<TLineaVenta> itLineas = lineasVenta.iterator();
		Iterator<TProducto> itProductos = productos != null ? productos.iterator() : null;

		while (itLineas.hasNext()) {
			TLineaVenta linea = itLineas.next();
			builder.append("id_producto: ").append(linea.get_producto());
			builder.append(", cantidad: ").append(linea.get_num_unidades());
			builder.append(", precio: ").append(linea.get_precio_unidades()).append(" EUR");
			builder.append(", activo: ").append(linea.get_activo()).append("\n");

			if (itProductos != null && itProductos.hasNext()) {
				TProducto producto = itProductos.next();
				builder.append("     Datos actuales del producto:\n");
				builder.append("          id_producto: ").append(producto.getId());
				builder.append(", marca: ").append(producto.getMarca() != null ? producto.getMarca() : "-");
				builder.append(", modelo: ").append(producto.getModelo() != null ? producto.getModelo() : "-");
				builder.append(", stock: ").append(producto.getUnidades());
				builder.append(", precio actual: ").append(producto.getPrecio()).append(" EUR");
				builder.append(", activo: ").append(producto.getActivo()).append("\n");
			}

			builder.append(
					"-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}
}
