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

import negocio.Venta.TCarrito;
import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VCerrarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();

	public VCerrarVenta() {
		super("Cerrar venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Cerrar venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id venta:"));
		add(idVentaField);

		JButton cerrar = new JButton("Cerrar venta");
		cerrar.setBackground(new Color(200, 255, 200));
		cerrar.addActionListener(e -> onAceptar());
		add(cerrar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(360, 160);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");

			TCarrito carrito = new TCarrito();
			carrito.setId(idVenta);

			Controlador.getInstancia().accion(new Context(Evento.CERRAR_VENTA, carrito));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
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

	private void limpiar() {
		idVentaField.setText("");
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
			limpiar();
			setVisible(true);
			break;
		case RES_CERRAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta cerrada correctamente.", "Cerrar venta",
					JOptionPane.INFORMATION_MESSAGE);
			volver();
			break;
		case RES_CERRAR_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo cerrar la venta indicada.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al cerrar venta", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}
}