package presentacion.vista.empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VMostrarEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarEmpleado() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar empleados"));
		JButton btnMostrarTodos = new JButton("Mostrar todos los empleados");
		btnMostrarTodos.setBackground(new Color(200, 255, 200));
		btnMostrarTodos.addActionListener(e -> {
			ctrl.accion(new Context(Evento.MOSTRAR_TODOS_EMPLEADOS, null));
		});

		add(btnMostrarTodos);
	}
}
