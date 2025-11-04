package presentacion.Venta;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		setLayout(new GridLayout(7, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id venta:"));
		add(idVentaField);

		add(new JLabel("Id empleado:"));
		add(empleadoField);

		add(new JLabel("Id cliente:"));
		add(clienteField);

		add(new JLabel("Metodo de pago:"));
		add(metodoPagoField);

		add(new JLabel("Importe total (EUR):"));
		add(precioField);

		add(new JLabel("Descuento (EUR):"));
		add(descuentoField);

		add(new JLabel("Estado:"));
		activoCheck.setOpaque(false);
		activoCheck.setSelected(true);
		add(activoCheck);

		JButton aceptar = new JButton("Guardar cambios");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());
		add(aceptar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(440, 320);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			int idEmpleado = parseEnteroPositivo(empleadoField.getText(), "Id empleado");
			int idCliente = parseEnteroPositivo(clienteField.getText(), "Id cliente");
			String metodoPago = metodoPagoField.getText().trim();

			if (metodoPago.isEmpty()) {
				throw new IllegalArgumentException("El metodo de pago no puede estar vacio.");
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

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
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

	private int parseEnteroPositivo(String texto, String campo) {
		try {
			int valor = Integer.parseInt(texto.trim());
			if (valor <= 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un numero entero positivo.");
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
			throw new IllegalArgumentException(campo + " debe ser un numero valido mayor o igual que cero.");
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
		case VMODIFICAR_VENTA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_MODIFICAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta modificada correctamente.", "Modificar venta",
					JOptionPane.INFORMATION_MESSAGE);
			volver();
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
