package presentacion.vista.almacen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VMostrarAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarAlmacen() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Mostrar almacenes"));
		setLayout(new GridLayout(1, 1));

		JButton btnMostrarTodos = new JButton("Mostrar todos");
		btnMostrarTodos.setBackground(new Color(200, 255, 200)); // Verde
		btnMostrarTodos.addActionListener(e -> ctrl.accion(new Context(Evento.MOSTRAR_TODOS_ALMACENES, null)));

		add(btnMostrarTodos, BorderLayout.CENTER);
	}

}
