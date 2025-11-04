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

public class VEliminarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idProductoField = new JTextField();
	private final JTextField cantidadField = new JTextField();
	private TCarrito carrito;

	public VEliminarProducto() {
		super("Eliminar producto de venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Eliminar producto de venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id producto:"));
		add(idProductoField);

		add(new JLabel("Cantidad:"));
		add(cantidadField);

		JButton aceptar = new JButton("Eliminar producto");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());
		add(aceptar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(400, 200);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idProducto = Integer.parseInt(idProductoField.getText().trim());
			int cantidad = Integer.parseInt(cantidadField.getText().trim());

			if (idProducto <= 0 || cantidad <= 0) {
				JOptionPane.showMessageDialog(this, "El Id producto y la cantidad deben ser numeros positivos.",
						"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (carrito != null) {
				carrito.setidProducto(idProducto);
				carrito.setcantidadProducto(cantidad);
				Controlador.getInstancia().accion(new Context(Evento.QUITAR_PRODUCTO_VENTA, carrito));
				limpiarCampos();
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id producto y la cantidad deben ser numeros enteros positivos.",
					"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, carrito));
		} else {
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		}
		dispose();
	}

	private void limpiarCampos() {
		idProductoField.setText("");
		cantidadField.setText("");
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VQUITAR_PRODUCTO_VENTA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_PASAR_CARRITO_A_ELIMINAR_OK:
			if (datos instanceof TCarrito) {
				this.carrito = (TCarrito) datos;
				setVisible(true);
			}
			break;
		case RES_PASAR_CARRITO_A_ELIMINAR_KO:
			JOptionPane.showMessageDialog(this, "Error en el traspaso del carrito.");
			dispose();
			break;
		case RES_QUITAR_PRODUCTO_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Producto eliminado del carrito.");
			if (datos instanceof TCarrito) {
				Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, datos));
			}
			dispose();
			break;
		case RES_QUITAR_PRODUCTO_VENTA_KO:
			JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto del carrito.");
			if (datos instanceof TCarrito) {
				Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, datos));
			}
			dispose();
			break;
		default:
			break;
		}
	}
}
