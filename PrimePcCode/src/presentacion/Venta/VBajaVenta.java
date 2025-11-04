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

public class VBajaVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JTextField idVentaField = new JTextField();

	public VBajaVenta() {
		super("Dar de baja venta");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Dar de baja venta"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id venta:"));
		add(idVentaField);

		JButton aceptar = new JButton("Dar de baja");
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
			int idVenta = Integer.parseInt(idVentaField.getText().trim());
			if (idVenta <= 0) {
				JOptionPane.showMessageDialog(this, "El Id venta debe ser un numero positivo.", "Datos incorrectos",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Controlador.getInstancia().accion(new Context(Evento.BAJA_VENTA, idVenta));
			idVentaField.setText("");

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id venta debe ser un numero entero positivo.",
					"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
		dispose();
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();

		switch (evento) {
		case VBAJA_VENTA:
			idVentaField.setText("");
			setVisible(true);
			break;
		case RES_BAJA_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Éxito al dar de baja venta.");
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			dispose();
			break;
		case RES_BAJA_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Error al dar de baja venta.");
			Controlador.getInstancia().accion(new Context(Evento.VENTA, null));
			dispose();
			break;
		default:
			break;
		}
	}
}
