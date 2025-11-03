package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		JPanel datos = new JPanel(new java.awt.GridLayout(0, 2, 10, 8));
		datos.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 10, 15));

		datos.add(new JLabel("Id venta:"));
		datos.add(idVentaField);

		datos.add(new JLabel("Id producto:"));
		datos.add(idProductoField);

		datos.add(new JLabel("Unidades:"));
		datos.add(unidadesField);

		datos.add(new JLabel("Precio por unidad (€):"));
		datos.add(precioUnidadField);

		add(datos, BorderLayout.CENTER);

		JButton aceptar = new JButton("Añadir");
		aceptar.addActionListener(e -> onAceptar());

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> dispose());

		JPanel botones = new JPanel();
		botones.add(aceptar);
		botones.add(cancelar);
		add(botones, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(360, 220));
		pack();
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
			dispose();
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
