package presentacion.vista.cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VMostrarCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarCliente() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar almacenes"));

		JButton btnMostrarTodos = new JButton("Mostrar todos los clientes");
		btnMostrarTodos.setBackground(new Color(200, 255, 200));
		btnMostrarTodos.addActionListener(e -> {
			ctrl.accion(Evento.MOSTRAR_TODOS_CLIENTES, null);
		});

		add(btnMostrarTodos);
	}
}
