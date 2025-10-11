package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VMostrarPorEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarPorEmpleado() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(1, 1));
		JButton btnPorEmpleado = new JButton("Por Empleado");
		btnPorEmpleado.setBackground(new Color(200, 255, 200));
		btnPorEmpleado.addActionListener(e -> {
			try {
				String id = JOptionPane.showInputDialog("ID Empleado:");
				if (id != null)
					ctrl.accion(new Context(Evento.MOSTRAR_VENTAS_POR_EMPLEADO, Integer.parseInt(id)));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos invalidos");
			}
		});

		add(btnPorEmpleado);

	}
}
