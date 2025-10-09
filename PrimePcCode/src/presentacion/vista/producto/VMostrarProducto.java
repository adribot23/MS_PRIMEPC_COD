package presentacion.vista.producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VMostrarProducto extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarProducto() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar Productos"));
		JButton btnMostrar = new JButton("Mostrar todos");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(e -> ctrl.accion(Evento.MOSTRAR_TODOS_PRODUCTOS, null));

		add(btnMostrar);
	}
}
