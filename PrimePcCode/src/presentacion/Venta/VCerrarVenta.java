package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		setPreferredSize(new Dimension(360, 160));

		JPanel inputPanel = new JPanel(new java.awt.GridLayout(1, 2, 10, 10));
		inputPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

		inputPanel.add(new JLabel("Id venta:"));
		inputPanel.add(idVentaField);

		add(inputPanel, BorderLayout.CENTER);

		JButton aceptar = new JButton("Cerrar venta");
		aceptar.addActionListener(e -> onAceptar());

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> dispose());

		JPanel acciones = new JPanel();
		acciones.add(aceptar);
		acciones.add(cancelar);
		add(acciones, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idVenta = parseEnteroPositivo(idVentaField.getText(), "Id venta");
			Controlador.getInstancia().accion(new Context(Evento.CERRAR_VENTA, idVenta));
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int parseEnteroPositivo(String value, String campo) {
		try {
			int numero = Integer.parseInt(value.trim());
			if (numero <= 0) {
				throw new NumberFormatException();
			}
			return numero;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(campo + " debe ser un número entero positivo.");
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
		case CERRAR_VENTA:
			limpiar();
			setVisible(true);
			break;
		case RES_CERRAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta cerrada correctamente.", "Cerrar venta",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
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
