package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Venta.TVenta;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAbrirVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField empleadoField = new JTextField();
	private final JTextField clienteField = new JTextField();
	private final JTextField metodoPagoField = new JTextField();
	private final JTextField precioField = new JTextField();
	private final JTextField descuentoField = new JTextField();

	public VAbrirVenta() {
		super("Abrir venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel datosPanel = new JPanel();
		datosPanel.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
		datosPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

		datosPanel.add(new JLabel("Id empleado:"));
		datosPanel.add(empleadoField);

		datosPanel.add(new JLabel("Id cliente:"));
		datosPanel.add(clienteField);

		datosPanel.add(new JLabel("Método de pago:"));
		datosPanel.add(metodoPagoField);

		datosPanel.add(new JLabel("Importe total (€):"));
		datosPanel.add(precioField);

		datosPanel.add(new JLabel("Descuento (€):"));
		datosPanel.add(descuentoField);

		add(datosPanel, BorderLayout.CENTER);

		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(e -> onAceptar());

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> dispose());

		JPanel acciones = new JPanel();
		acciones.add(aceptar);
		acciones.add(cancelar);
		add(acciones, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(400, 280));
		pack();
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idEmpleado = parseEnteroPositivo(empleadoField.getText(), "Id empleado");
			int idCliente = parseEnteroPositivo(clienteField.getText(), "Id cliente");
			String metodoPago = metodoPagoField.getText().trim();

			if (metodoPago.isEmpty()) {
				throw new IllegalArgumentException("El método de pago no puede estar vacío.");
			}

			double precio = parseDoubleNoNegativo(precioField.getText(), "Importe total");
			double descuento = parseDoubleNoNegativo(descuentoField.getText(), "Descuento");

			TVenta venta = new TVenta();
			venta.setIdEmpleado(idEmpleado);
			venta.setIdCliente(idCliente);
			venta.setMetodoPago(metodoPago);
			venta.setPrecio(precio);
			venta.setDescuento(descuento);
			venta.setActivo(1);

			Controlador.getInstancia().accion(new Context(Evento.ABRIR_VENTA, venta));

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiarCampos() {
		empleadoField.setText("");
		clienteField.setText("");
		metodoPagoField.setText("");
		precioField.setText("");
		descuentoField.setText("");
	}

	private int parseEnteroPositivo(String text, String campo) {
		try {
			int valor = Integer.parseInt(text.trim());
			if (valor <= 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número entero positivo.");
		}
	}

	private double parseDoubleNoNegativo(String text, String campo) {
		if (text == null || text.trim().isEmpty()) {
			return 0.0;
		}
		try {
			double valor = Double.parseDouble(text.trim());
			if (valor < 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número válido mayor o igual que cero.");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == null) {
			return;
		}

		switch (evento) {
		case ABRIR_VENTA:
			limpiarCampos();
			setVisible(true);
			break;

		case RES_ABRIR_VENTA_OK:
			String mensajeOk = datos instanceof Number ? "Venta creada con id " + datos : "Venta creada correctamente.";
			JOptionPane.showMessageDialog(this, mensajeOk, "Venta abierta", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			break;

		case RES_ABRIR_VENTA_KO:
			String mensajeError = datos instanceof String ? (String) datos : "No se pudo abrir la venta.";
			JOptionPane.showMessageDialog(this, mensajeError, "Error al abrir venta", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;

		default:
			break;
		}
	}
}
