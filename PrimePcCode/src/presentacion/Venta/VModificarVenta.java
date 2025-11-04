package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
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

	public VModificarVenta() {
		super("Modificar venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
		formPanel.setOpaque(false);

		GridBagConstraints base = new GridBagConstraints();
		base.insets = new Insets(6, 8, 6, 8);
		base.anchor = GridBagConstraints.WEST;

		addRow(formPanel, base, 0, "Id venta:", idVentaField);
		addRow(formPanel, base, 1, "Id empleado:", empleadoField);
		addRow(formPanel, base, 2, "Id cliente:", clienteField);
		addRow(formPanel, base, 3, "Metodo de pago:", metodoPagoField);
		addRow(formPanel, base, 4, "Importe total (EUR):", precioField);
		addRow(formPanel, base, 5, "Descuento (EUR):", descuentoField);

		add(formPanel, BorderLayout.CENTER);

		JButton aceptar = new JButton("Guardar cambios");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());

		JPanel botonesPanel = new JPanel(new GridBagLayout());
		botonesPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
		botonesPanel.setOpaque(false);

		GridBagConstraints btnLeft = new GridBagConstraints();
		btnLeft.gridx = 0;
		btnLeft.gridy = 0;
		btnLeft.anchor = GridBagConstraints.WEST;
		btnLeft.insets = new Insets(0, 0, 0, 10);
		botonesPanel.add(volver, btnLeft);

		GridBagConstraints btnRight = new GridBagConstraints();
		btnRight.gridx = 1;
		btnRight.gridy = 0;
		btnRight.anchor = GridBagConstraints.EAST;
		botonesPanel.add(aceptar, btnRight);

		add(botonesPanel, BorderLayout.SOUTH);

		pack();
		if (getWidth() < 480 || getHeight() < 290) {
			setSize(Math.max(getWidth(), 480), Math.max(getHeight(), 290));
		}
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

			Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_VENTA, venta));
			limpiarCampos();
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
			JOptionPane.showMessageDialog(null, "Venta modificada correctamente.", "Modificar venta",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		case RES_MODIFICAR_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo modificar la venta indicada.";
			JOptionPane.showMessageDialog(null, mensaje, "Error al modificar venta", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

	private void addRow(JPanel panel, GridBagConstraints base, int fila, String etiqueta, JComponent componente) {
		GridBagConstraints gbcLabel = (GridBagConstraints) base.clone();
		gbcLabel.gridx = 0;
		gbcLabel.gridy = fila;
		gbcLabel.weightx = 0;
		gbcLabel.fill = GridBagConstraints.NONE;
		panel.add(new JLabel(etiqueta), gbcLabel);

		GridBagConstraints gbcField = (GridBagConstraints) base.clone();
		gbcField.gridx = 1;
		gbcField.gridy = fila;
		gbcField.weightx = 1;
		gbcField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(componente, gbcField);
	}
}
