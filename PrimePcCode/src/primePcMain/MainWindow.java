package primePcMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class MainWindow extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public MainWindow() {
		super("PRIME PC");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		// === CABECERA ===
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(new Color(200, 255, 200));
		header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.LIGHT_GRAY));

		JLabel logoIzq = new JLabel(new ImageIcon(
				new ImageIcon("resources/logo.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		logoIzq.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		header.add(logoIzq, BorderLayout.WEST);

		JLabel logoDer = new JLabel(new ImageIcon(
				new ImageIcon("resources/logo.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		logoDer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		header.add(logoDer, BorderLayout.EAST);

		JLabel titulo = new JLabel("PRIME PC", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 40));
		header.add(titulo, BorderLayout.CENTER);

		mainPanel.add(header, BorderLayout.PAGE_START);

		// === CONTENEDOR VERTICAL PARA LOS DOS BLOQUES DE BOTONES ===
		JPanel centerContainer = new JPanel(new GridLayout(2, 1));
		centerContainer.setBackground(Color.WHITE);

		// === BLOQUE 1: botones originales ===
		JPanel menuPanel1 = new JPanel(new GridLayout(2, 3, 30, 30));
		menuPanel1.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
		menuPanel1.setBackground(Color.WHITE);

		// Panel 1 - DAO

		menuPanel1.setBackground(Color.WHITE);
		menuPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 30), "DAO",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 18), Color.DARK_GRAY));

		menuPanel1.add(createStyledButton("Cliente", "resources/clientes.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		}));

		menuPanel1.add(createStyledButton("Venta", "resources/ventas.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		}));

		menuPanel1.add(createStyledButton("Empleado", "resources/empleados.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();
		}));

		menuPanel1.add(createStyledButton("Proveedor", "resources/proveedores.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		}));

		menuPanel1.add(createStyledButton("Producto", "resources/productos.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}));

		menuPanel1.add(createStyledButton("Almacén", "resources/almacenes.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		}));

		mainPanel.add(menuPanel1, BorderLayout.NORTH);

		// === BLOQUE 2: nuevas entidades ===
		JPanel menuPanel2 = new JPanel(new GridLayout(2, 3, 30, 30));
		menuPanel2.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
		menuPanel2.setBackground(Color.LIGHT_GRAY);

		// Panel 2 - JPA

		menuPanel2.setBackground(Color.WHITE);
		menuPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 30), "JPA",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 18), Color.DARK_GRAY));

		menuPanel2.add(createStyledButton("Paquete", "resources/paquete.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
			this.dispose();
		}));

		menuPanel2.add(createStyledButton("Remitente", "resources/remitente.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.REMITENTE, null));
			this.dispose();
		}));

		menuPanel2.add(createStyledButton("Factura", "resources/factura.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		}));

		menuPanel2.add(createStyledButton("Ruta", "resources/ruta.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.RUTA, null));
			this.dispose();
		}));

		menuPanel2.add(createStyledButton("Trabajador", "resources/trabajador.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		}));

		menuPanel2.add(createStyledButton("Transporte", "resources/transporte.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
			this.dispose();
		}));

		// Añadir ambos al contenedor vertical
		centerContainer.add(menuPanel1);
		centerContainer.add(menuPanel2);

		// Añadir al frame
		mainPanel.add(centerContainer, BorderLayout.CENTER);

		// === CIERRE ===
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(950, 900));
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Confirmación al cerrar
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				int n = JOptionPane.showOptionDialog(MainWindow.this, "¿Seguro que quieres salir?", "Salir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
	}

	private JButton createStyledButton(String text, String iconPath, ActionListener listener) {
		JButton button = new JButton(text);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.BOLD, 18));

		ImageIcon icon = new ImageIcon(
				new ImageIcon(iconPath).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		button.setIcon(icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(200, 255, 200));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.WHITE);
			}
		});

		button.addActionListener(listener);

		// TAMAÑO FIJO
		button.setPreferredSize(new Dimension(180, 180));

		return button;
	}

	@Override
	public void actualizar(Context context) {
		setVisible(true);
	}
}
