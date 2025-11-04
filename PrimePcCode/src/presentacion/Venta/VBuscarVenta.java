package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;

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

		setSize(500, 340);
		setLocationRelativeTo(null);
	}

	private void onBuscar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			Controlador.getInstancia().accion(new Context(Evento.BUSCAR_VENTA, idVenta));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
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
			resultadoArea.setText(formatearResultado(datos));
			setVisible(true);
			break;
		case RES_BUSCAR_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se encontró la venta solicitada.";
			JOptionPane.showMessageDialog(this, mensaje, "Venta no encontrada", JOptionPane.WARNING_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}

	private String formatearResultado(Object datos) {
		if (datos == null) {
			return "No hay información disponible.";
		}

		if (datos instanceof Collection<?>) {
			StringBuilder builder = new StringBuilder();
			Iterator<?> it = ((Collection<?>) datos).iterator();
			while (it.hasNext()) {
				Object elemento = it.next();
				builder.append(formatearResultado(elemento));
				if (it.hasNext()) {
					builder.append(System.lineSeparator()).append(System.lineSeparator());
				}
			}
			return builder.toString();
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
		sb.append("Venta #").append(venta.getId()).append(System.lineSeparator());
		sb.append("Empleado: ").append(venta.getIdEmpleado()).append(System.lineSeparator());
		sb.append("Cliente: ").append(venta.getIdCliente()).append(System.lineSeparator());
		sb.append("Metodo de pago: ").append(textoSeguro(venta.getMetodoPago())).append(System.lineSeparator());
		sb.append("Importe: ").append(venta.getPrecio()).append(" EUR").append(System.lineSeparator());
		sb.append("Descuento: ").append(venta.getDescuento()).append(" EUR").append(System.lineSeparator());
		sb.append("Activa: ").append(venta.getActivo() == 1 ? "Si" : "No");
		return sb.toString();
	}

	private String formatearVentaTOA(TVentaTOA toa) {
		StringBuilder sb = new StringBuilder();
		TVenta venta = toa.get_venta();
		if (venta != null) {
			sb.append(formatearVentaSimple(venta)).append(System.lineSeparator());
		}

		TEmpleado empleado = toa.get_empleado();
		if (empleado != null) {
			sb.append("Empleado asignado: ").append(textoSeguro(empleado.getNombre()));
			sb.append(" (id ").append(empleado.getId()).append(")").append(System.lineSeparator());
		}

		Set<TLineaVenta> lineas = toa.get_lista_lineasVenta();
		if (lineas != null && !lineas.isEmpty()) {
			sb.append("Lineas de venta:").append(System.lineSeparator());
			for (TLineaVenta linea : lineas) {
				sb.append("  - Producto ").append(linea.get_producto());
				sb.append(" - unidades: ").append(linea.get_num_unidades());
				sb.append(" - precio unidad: ").append(linea.get_precio_unidades()).append(" EUR");
				sb.append(System.lineSeparator());
			}
		}

		Set<TProducto> productos = toa.get_lista_producto();
		if (productos != null && !productos.isEmpty()) {
			StringJoiner joiner = new StringJoiner(", ");
			for (TProducto producto : productos) {
				String etiqueta = nombreProducto(producto);
				joiner.add(etiqueta + " (id " + producto.getId() + ")");
			}
			sb.append("Productos vendidos: ").append(joiner.toString());
		}

		return sb.toString();
	}

	private String nombreProducto(TProducto producto) {
		if (producto == null) {
			return "Producto";
		}
		String marca = producto.getMarca() != null ? producto.getMarca() : "";
		String modelo = producto.getModelo() != null ? producto.getModelo() : "";
		String combinado = (marca + " " + modelo).trim();
		return combinado.isEmpty() ? "Producto" : combinado;
	}

	private String textoSeguro(String value) {
		return value == null || value.isEmpty() ? "-" : value;
	}
}
