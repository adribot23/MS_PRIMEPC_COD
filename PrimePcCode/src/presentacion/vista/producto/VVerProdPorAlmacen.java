package presentacion.vista.producto;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VVerProdPorAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VVerProdPorAlmacen() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(1, 2));
		setBorder(BorderFactory.createTitledBorder("Productos por Almacén"));

		JTextField txtIdAlmacen = new JTextField();
		JButton btnPorAlmacen = new JButton("Mostrar por Almacén");
		btnPorAlmacen.setBackground(new Color(200, 255, 200));
		add(txtIdAlmacen);
		add(btnPorAlmacen);

		btnPorAlmacen.addActionListener(e -> {
			try {
				int idAlmacen = Integer.parseInt(txtIdAlmacen.getText());
				ctrl.accion(Evento.MOSTRAR_PRODUCTOS_POR_ALMACEN, idAlmacen);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID erroneo.");
			}
		});
	}
}
