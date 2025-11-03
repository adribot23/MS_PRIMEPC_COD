package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class VModificarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();
	private final JTextField empleadoField = new JTextField();
	private final JTextField clienteField = new JTextField();
	private final JTextField metodoPagoField = new JTextField();
	private final JTextField precioField = new JTextField();
	private final JTextField descuentoField = new JTextField();
	private final JCheckBox activoCheck = new JCheckBox("Venta activa");

	public VModificarVenta() {
		super("Modificar venta");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(420, 320));
		setLayout(new BorderLayout(10, 10));

		JPanel datos = new JPanel(new java.awt.GridLayout(0, 2, 10, 10));
		datos.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

		datos.add(new JLabel("Id venta:"));
		datos.add(idVentaField);

		datos.add(new JLabel("Id empleado:"));
		datos.add(empleadoField);

		datos.add(new JLabel("Id cliente:"));
		datos.add(clienteField);

		datos.add(new JLabel("Método de pago:"));
		datos.add(metodoPagoField);

		datos.add(new JLabel("Importe total (€):"));
		datos.add(precioField);

		datos.add(new JLabel("Descuento (€):"));
		datos.add(descuentoField);

		datos.add(new JLabel(" "));
		datos.add(activoCheck);

		add(datos, BorderLayout.CENTER);

		JButton aceptar = new JButton("Guardar cambios");
		aceptar.addActionListener(e -> onAceptar());

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> dispose());

		JPanel acciones = new JPanel();
		acciones.add(aceptar);
		acciones.add(cancelar);
		add(acciones, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			int idEmpleado = parseEnteroPositivo(empleadoField.getText(), "Id empleado");
			int idCliente = parseEnteroPositivo(clienteField.getText(), "Id cliente");
			String metodoPago = metodoPagoField.getText().trim();

			if (metodoPago.isEmpty()) {
				throw new IllegalArgumentException("El método de pago no puede estar vacío.");
			}

			double precio = parseDoubleNoNegativo(precioField.getText(), "Importe total");
			double descuento = parseDoubleNoNegativo(descuentoField.getText(), "Descuento");

			TVenta venta = new TVenta();
			venta.setId(idVenta);
			venta.setIdEmpleado(idEmpleado);
			venta.setIdCliente(idCliente);
			venta.setMetodoPago(metodoPago);
			venta.setPrecio(precio);
			venta.setDescuento(descuento);
			venta.setActivo(activoCheck.isSelected() ? 1 : 0);

			Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_VENTA, venta));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int parseEnteroPositivo(String texto, String campo) {
		try {
			int valor = Integer.parseInt(texto.trim());
			if (valor <= 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número entero positivo.");
		}
	}

	private double parseDoubleNoNegativo(String texto, String campo) {
		if (texto == null || texto.trim().isEmpty()) {
			return 0.0;
		}
		try {
			double valor = Double.parseDouble(texto.trim());
			if (valor < 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un número válido mayor o igual que cero.");
		}
	}

	private void limpiarCampos() {
		idVentaField.setText("");
		empleadoField.setText("");
		clienteField.setText("");
		metodoPagoField.setText("");
		precioField.setText("");
		descuentoField.setText("");
		activoCheck.setSelected(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMODIFICAR_VENTA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_MODIFICAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta modificada correctamente.", "Modificar venta",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
			break;
		case RES_MODIFICAR_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo modificar la venta indicada.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al modificar venta", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}
}