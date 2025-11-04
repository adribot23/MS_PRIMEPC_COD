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

public class VBajaProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField bajaId;

	public VBajaProducto() {
		super("Baja de Producto");
		initGUI();
	}

	private void initGUI() {
		// Configuración de la ventana
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Producto"));

		// Componentes
		JLabel lblId = new JLabel("ID Producto:");
		bajaId = new JTextField();
		JButton btnBaja = new JButton("Dar de Baja");
		btnBaja.setBackground(new Color(200, 255, 200));

		// Acción del botón
		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_PRODUCTO, id));
				bajaId.setText("");

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

		// Añadir componentes
		add(lblId);
		add(bajaId);
		add(btnBaja);
		add(btnVolver);

		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBAJA_PRODUCTO:
			this.setVisible(true);
			break;
		case RES_BAJA_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, "Producto dado de baja correctamente.");
			break;
		case RES_BAJA_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el producto.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}
