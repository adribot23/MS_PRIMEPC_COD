package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel cardsPanel;

	public MainWindow() {
		super("PRIME PC");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.setBackground(new Color(200, 255, 200));
		messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.LIGHT_GRAY));

		JLabel logo = new JLabel(new ImageIcon(
				new ImageIcon("resources/logo.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		logo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		messagePanel.add(logo, BorderLayout.WEST);

		JLabel logo1 = new JLabel(new ImageIcon(
				new ImageIcon("resources/logo.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		logo1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		messagePanel.add(logo1, BorderLayout.EAST);

		JLabel titulo = new JLabel("PRIME PC", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 40));

		messagePanel.add(titulo, BorderLayout.CENTER);

		this.add(messagePanel, BorderLayout.PAGE_START);

		cardsPanel = new JPanel(new CardLayout());
		cardsPanel.setBackground(Color.WHITE);
		mainPanel.add(cardsPanel, BorderLayout.CENTER);

		JPanel menuPanel = new JPanel(new GridLayout(2, 3, 30, 30));
		menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 60, 60));
		menuPanel.setBackground(Color.WHITE);

		menuPanel.add(createStyledButton("Cliente", "resources/clientes.png", "Clientes View"));
		menuPanel.add(createStyledButton("Venta", "resources/ventas.png", "Ventas View"));
		menuPanel.add(createStyledButton("Empleado", "resources/empleados.png", "Empleados View"));
		menuPanel.add(createStyledButton("Proveedor", "resources/proveedores.png", "Proveedores View"));
		menuPanel.add(createStyledButton("Producto", "resources/productos.png", "Productos View"));
		menuPanel.add(createStyledButton("Almacen", "resources/almacenes.png", "Almacenes View"));

		cardsPanel.add(menuPanel, "Menu");

		cardsPanel.add(new GUICliente(), "Clientes View");
		cardsPanel.add(new GUIVenta(), "Ventas View");
		cardsPanel.add(new GUIEmpleado(), "Empleados View");
		cardsPanel.add(new GUIAlmacen(), "Almacenes View");
		cardsPanel.add(new GUIProveedor(), "Proveedores View");
		cardsPanel.add(new GUIProducto(), "Productos View");
		cardsPanel.add(new GUIAlmacen(), "Almacenes View");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.pack();
		this.setMinimumSize(new Dimension(800, 700));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private JButton createStyledButton(String text, String iconPath, String viewName) {
		JButton button = new JButton(text);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2)); // Borde
																					// verde
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.BOLD, 18));

		ImageIcon icon = new ImageIcon(
				new ImageIcon(iconPath).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		button.setIcon(icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);

		// Hover effect
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(200, 255, 200)); // Fondo verde
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.WHITE);
			}
		});

		button.addActionListener(e -> {
			CardLayout cl = (CardLayout) (cardsPanel.getLayout());
			cl.show(cardsPanel, viewName);
		});

		return button;
	}

}
