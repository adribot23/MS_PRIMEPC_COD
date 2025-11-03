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

public class VEliminarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();
	private final JTextField idProductoField = new JTextField();
	private final JTextField unidadesField = new JTextField();

	public VEliminarProducto() {
		super("Eliminar producto de venta");
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

		datos.add(new JLabel("Unidades a quitar:"));
		datos.add(unidadesField);

		add(datos, BorderLayout.CENTER);

		JButton aceptar = new JButton("Eliminar");
		aceptar.addActionListener(e -> onAceptar());

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> dispose());

		JPanel acciones = new JPanel();
		acciones.add(aceptar);
		acciones.add(cancelar);
		add(acciones, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(340, 200));
		pack();
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			int idProducto = parseEnteroPositivo(idProductoField.getText(), "Id producto");
			int unidades = parseEnteroPositivo(unidadesField.getText(), "Unidades a quitar");

			TLineaVenta lineaVenta = new TLineaVenta();
			lineaVenta.set_venta(idVenta);
			lineaVenta.set_producto(idProducto);
			lineaVenta.set_num_unidades(unidades);

			Controlador.getInstancia().accion(new Context(Evento.QUITAR_PRODUCTO_VENTA, lineaVenta));
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

	private void limpiarCampos() {
		idVentaField.setText("");
		idProductoField.setText("");
		unidadesField.setText("");
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
		case RES_QUITAR_PRODUCTO_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Producto eliminado de la venta.", "Línea actualizada",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
			break;
		case RES_QUITAR_PRODUCTO_VENTA_KO:
			String mensaje = datos instanceof String ? (String) datos : "No se pudo eliminar el producto indicado.";
			JOptionPane.showMessageDialog(this, mensaje, "Error al eliminar producto", JOptionPane.ERROR_MESSAGE);
			setVisible(true);
			break;
		default:
			break;
		}
	}
}
