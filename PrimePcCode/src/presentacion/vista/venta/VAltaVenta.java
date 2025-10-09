package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import negocio.transfers.TVenta;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VAltaVenta extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	private JTextField txtProductoID;
	private JTextField txtCantidad;

	private JButton btnAbrirVenta;
	private JButton btnAddProducto;
	private JButton btnRemoveProducto;
	private JButton btnCerrarVenta;
	private JButton btnInfoSocios;
	private JButton btnVerCarrito;

	private Map<Integer, Integer> productosVenta;

	public VAltaVenta() {
		ctrl = Controlador.obtenerInstancia();
		productosVenta = new HashMap<>();
		initGui();
	}

	private void initGui() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Abrir Venta
		JPanel abrirVentaPanel = new JPanel(new GridLayout(1, 1));
		abrirVentaPanel.setBorder(BorderFactory.createTitledBorder("Abrir Venta"));
		btnAbrirVenta = new JButton("Abrir Venta");
		btnAbrirVenta.setBackground(new Color(200, 255, 200));
		btnAbrirVenta.addActionListener(e -> {
			productosVenta.clear();
			setModoEdicion(true);

			JOptionPane.showMessageDialog(this, "Venta abierta ");

		});
		abrirVentaPanel.add(btnAbrirVenta);

		// Gestionar Productos
		JPanel productoPanel = new JPanel(new GridLayout(4, 2));
		productoPanel.setBorder(BorderFactory.createTitledBorder("Gestion Productos"));
		txtProductoID = new JTextField();
		txtCantidad = new JTextField();
		btnAddProducto = new JButton("Agregar Producto");
		btnRemoveProducto = new JButton("Quitar Producto");
		btnAddProducto.setBackground(new Color(200, 255, 200));
		btnRemoveProducto.setBackground(new Color(200, 255, 200));
		btnAddProducto.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProductoID.getText());
				int cantidad = Integer.parseInt(txtCantidad.getText());
				productosVenta.put(idProducto, productosVenta.getOrDefault(idProducto, 0) + cantidad);
				JOptionPane.showMessageDialog(this, "Producto agregado o actualizado");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y cantidad deben ser num�ricos");
			}
		});

		btnRemoveProducto.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(txtProductoID.getText());
				int cantidad = Integer.parseInt(txtCantidad.getText());
				if (productosVenta.containsKey(idProducto)) {
					int nuevaCantidad = productosVenta.get(idProducto) - cantidad;
					if (nuevaCantidad <= 0)
						productosVenta.remove(idProducto);
					else
						productosVenta.put(idProducto, nuevaCantidad);
					JOptionPane.showMessageDialog(this, "Producto actualizado/eliminado");
				} else {
					JOptionPane.showMessageDialog(this, "Producto no esta en la venta");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y cantidad deben ser numericos");
			}
		});

		btnInfoSocios = new JButton("Info Socios");
		btnInfoSocios.setBackground(new Color(200, 255, 200));
		btnInfoSocios.addActionListener(e -> {
			String mensaje = "  Sistema de Puntos para Socios:\n\n" + "- Por cada euro que gastas, acumulas 1 punto.\n"
					+ "- Al llegar a 100 puntos, se canjean automaticamente por un 10% de descuento en tu próxima compra.\n"
					+ "- Si tienes menos de 100 puntos, acumularás puntos con el importe total de tu compra.\n"
					+ "- Después de usar 100 puntos para un descuento, se restan de tu saldo de puntos.\n\n"
					+ "¡Ser socio tiene ventajas!";

			JOptionPane.showMessageDialog(this, mensaje, "Información para Socios", JOptionPane.INFORMATION_MESSAGE);
		});

		btnVerCarrito = new JButton("Ver Carrito");
		btnVerCarrito.setBackground(new Color(200, 255, 200));
		btnVerCarrito.addActionListener(e -> {
			if (productosVenta.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El carrito esta vacio.");
				return;
			}

			String[] columnas = { "ID Producto", "Cantidad" };
			Object[][] datos = new Object[productosVenta.size()][2];

			int i = 0;
			for (Map.Entry<Integer, Integer> entry : productosVenta.entrySet()) {
				datos[i][0] = entry.getKey();
				datos[i][1] = entry.getValue();
				i++;
			}

			JTable tabla = new JTable(datos, columnas);
			JScrollPane scroll = new JScrollPane(tabla);

			JOptionPane.showMessageDialog(this, scroll, "Productos en el Carrito", JOptionPane.PLAIN_MESSAGE);

		});
		productoPanel.add(new JLabel("ID Producto:"));
		productoPanel.add(txtProductoID);
		productoPanel.add(new JLabel("Cantidad:"));
		productoPanel.add(txtCantidad);
		productoPanel.add(btnAddProducto);
		productoPanel.add(btnRemoveProducto);
		productoPanel.add(btnInfoSocios);
		productoPanel.add(btnVerCarrito);

		// Cerrar Venta
		JPanel cerrarVentaPanel = new JPanel(new GridLayout(7, 1));

		JTextField altaPago = new JTextField();
		JTextField altaIdEmpleado = new JTextField();
		JTextField altaIdCliente = new JTextField();

		cerrarVentaPanel.add(new JLabel("Metodo de Pago:"));
		cerrarVentaPanel.add(altaPago);
		cerrarVentaPanel.add(new JLabel("ID Empleado:"));
		cerrarVentaPanel.add(altaIdEmpleado);
		cerrarVentaPanel.add(new JLabel("ID Cliente:"));
		cerrarVentaPanel.add(altaIdCliente);
		btnCerrarVenta = new JButton("Cerrar Venta");
		btnCerrarVenta.setBackground(new Color(200, 255, 200));
		btnCerrarVenta.addActionListener(e -> {
			try {
				String metodoPago = altaPago.getText();
				int idEmpleado = Integer.parseInt(altaIdEmpleado.getText());
				int idCliente = Integer.parseInt(altaIdCliente.getText());
				TVenta venta = new TVenta(-1, metodoPago, -1, -1, idEmpleado, idCliente, productosVenta);
				ctrl.accion(Evento.ALTA_VENTA, venta);
				productosVenta.clear();
				setModoEdicion(false);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos invalidos");
			}

		});

		cerrarVentaPanel.add(btnCerrarVenta);

		add(abrirVentaPanel);
		add(Box.createVerticalStrut(10));
		add(productoPanel);
		add(Box.createVerticalStrut(10));
		add(cerrarVentaPanel);
		setModoEdicion(false);
	}

	private void setModoEdicion(boolean activo) {
		txtProductoID.setEnabled(activo);
		txtCantidad.setEnabled(activo);
		btnAddProducto.setEnabled(activo);
		btnRemoveProducto.setEnabled(activo);
		btnCerrarVenta.setEnabled(activo);
		btnVerCarrito.setEnabled(activo);
		btnAbrirVenta.setEnabled(!activo);
	}
}
