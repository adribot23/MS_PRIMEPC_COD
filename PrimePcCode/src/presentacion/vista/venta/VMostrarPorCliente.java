package presentacion.vista.venta;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VMostrarPorCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VMostrarPorCliente() {
		ctrl = Controlador.obtenerInstancia();
		initGui();
	}

	private void initGui() {
		setLayout(new GridLayout(1, 1));
		JButton btnPorCliente = new JButton("Por Cliente");
		btnPorCliente.setBackground(new Color(200, 255, 200));
		btnPorCliente.addActionListener(e -> {
			try {
				String id = JOptionPane.showInputDialog("ID Cliente:");
				if (id != null)
					ctrl.accion(Evento.MOSTRAR_VENTAS_POR_CLIENTE, Integer.parseInt(id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos invalidos");
			}
		});
		add(btnPorCliente);

	}

}
