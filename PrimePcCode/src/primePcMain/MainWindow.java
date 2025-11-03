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

public class MainWindow extends JFrame {

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

		// === BOTONES ===
		JPanel menuPanel = new JPanel(new GridLayout(2, 3, 30, 30));
		menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 60, 60));
		menuPanel.setBackground(Color.WHITE);

		menuPanel.add(createStyledButton("Cliente", "resources/clientes.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		}));

		menuPanel.add(createStyledButton("Venta", "resources/ventas.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		}));

		menuPanel.add(createStyledButton("Empleado", "resources/empleados.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();
		}));

		menuPanel.add(createStyledButton("Proveedor", "resources/proveedores.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		}));

		menuPanel.add(createStyledButton("Producto", "resources/productos.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}));

		menuPanel.add(createStyledButton("Almacén", "resources/almacenes.png", e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		}));

		mainPanel.add(menuPanel, BorderLayout.CENTER);

		// === CIERRE ===
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 700));
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

		// Efecto hover
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(200, 255, 200));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.WHITE);
			}
		});

		button.addActionListener(listener);
		return button;
	}

}
