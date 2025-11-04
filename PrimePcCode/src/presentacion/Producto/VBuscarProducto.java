package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField buscarId;

	public VBuscarProducto() {
		super("Buscar Producto");
		initGUI();
	}

	private void initGUI() {
		// Configuración general
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Producto"));

		JLabel lblId = new JLabel("ID del producto:");
		buscarId = new JTextField();

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));
		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(buscarId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_PRODUCTO, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Introduce un número entero.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		});

		add(lblId);
		add(buscarId);
		add(btnBuscar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_PRODUCTO:
			this.setVisible(true);
			break;
		case RES_BUSCAR_PRODUCTO_OK:
			JOptionPane.showMessageDialog(this, datos.toString());
			break;
		case RES_BUSCAR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(this, "Producto no encontrado.");
			break;
		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}
}
