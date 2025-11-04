package presentacion.Venta;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.Venta.TLineaVenta;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAñadirProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();
	private final JTextField idProductoField = new JTextField();
	private final JTextField unidadesField = new JTextField();
	private final JTextField precioUnidadField = new JTextField();

	public VAñadirProducto() {
		super("Añadir producto a venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(5, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Añadir producto a venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id venta:"));
		add(idVentaField);

		add(new JLabel("Id producto:"));
		add(idProductoField);

		add(new JLabel("Unidades:"));
		add(unidadesField);

		add(new JLabel("Precio por unidad (EUR):"));
		add(precioUnidadField);

		JButton aceptar = new JButton("Añadir");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());
		add(aceptar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(380, 230);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			int idProducto = parseEnteroPositivo(idProductoField.getText(), "Id producto");
			int unidades = parseEnteroPositivo(unidadesField.getText(), "Unidades");
			double precioUnidad = parseDoubleNoNegativo(precioUnidadField.getText(), "Precio por unidad");

			TLineaVenta lineaVenta = new TLineaVenta();
			lineaVenta.set_venta(idVenta);
			lineaVenta.set_producto(idProducto);
			lineaVenta.set_num_unidades(unidades);
			lineaVenta.set_precio_unidades(precioUnidad);

			Controlador.getInstancia().accion(new Context(Evento.INSERTAR_PRODUCTO_VENTA, lineaVenta));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
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

	private double parseDoubleNoNegativo(String valor, String campo) {
		if (valor == null || valor.trim().isEmpty()) {
			return 0.0;
		}

		try {
			double numero = Double.parseDouble(valor.trim());
			if (numero < 0) {
				throw new NumberFormatException();
			}
			return numero;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número válido mayor o igual que cero.");
		}
	}

	private void limpiarCampos() {
		idVentaField.setText("");
		idProductoField.setText("");
		unidadesField.setText("");
		precioUnidadField.setText("");
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VINSERTAR_PRODUCTO_VENTA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_INSERTAR_PRODUCTO_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Producto añadido correctamente a la venta.", "Línea agregada",
					JOptionPane.INFORMATION_MESSAGE);
			volver();
			break;
		case RES_INSERTAR_PRODUCTO_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo añadir el producto indicado.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al añadir producto", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}
}
