package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;


import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VMostrarVenta extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarVenta() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(1, 1));
		
		JButton btnMostrarTodos = new JButton("Mostrar todas las Ventas");
		btnMostrarTodos.setBackground(new Color(200, 255, 200));
		btnMostrarTodos.addActionListener(e -> ctrl.accion(Evento.MOSTRAR_TODAS_VENTAS, null));

		add(btnMostrarTodos);

	}
}
