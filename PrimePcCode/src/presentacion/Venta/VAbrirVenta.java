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

import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAbrirVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField empleadoField = new JTextField();

	public VAbrirVenta() {
		super("Abrir venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Abrir venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id empleado:"));
		add(empleadoField);

		JButton aceptar = new JButton("Abrir venta");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());
		add(aceptar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(360, 160);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idEmpleado = parseEnteroPositivo(empleadoField.getText(), "Id empleado");

			Controlador.getInstancia().accion(new Context(Evento.ABRIR_VENTA, idEmpleado));
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
		empleadoField.setText("");
	}

	private int parseEnteroPositivo(String text, String campo) {
		try {
			int valor = Integer.parseInt(text.trim());
			if (valor <= 0) {
				throw new NumberFormatException();
			}
			return valor;
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(campo + " debe ser un numero entero positivo.");
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
		case VABRIR_VENTA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_ABRIR_VENTA_OK:
			String mensajeOk = "Venta creada correctamente.";
			if (datos != null) {
				try {
					negocio.Venta.TCarrito carrito = (negocio.Venta.TCarrito) datos;
					mensajeOk = "Venta creada correctamente.\nCon ID: " + carrito.getId();
				} catch (ClassCastException e) {
					// Si no es un TCarrito, usar mensaje genérico
				}
			}
			JOptionPane.showMessageDialog(null, mensajeOk, "Venta abierta", JOptionPane.INFORMATION_MESSAGE);
			break;
		case RES_ABRIR_VENTA_KO:
			String mensajeError = datos instanceof String ? (String) datos : "No se pudo abrir la venta.";
			JOptionPane.showMessageDialog(null, mensajeError, "Error al abrir venta", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}