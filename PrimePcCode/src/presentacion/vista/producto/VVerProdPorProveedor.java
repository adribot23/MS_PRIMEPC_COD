package presentacion.vista.producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VVerProdPorProveedor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VVerProdPorProveedor() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(1, 2));
		setBorder(BorderFactory.createTitledBorder("Productos por Proveedor"));

		JTextField txtIdProveedor = new JTextField();
		JButton btnPorProveedor = new JButton("Mostrar por Proveedor");
		btnPorProveedor.setBackground(new Color(200, 255, 200));
		add(txtIdProveedor);
		add(btnPorProveedor);

		btnPorProveedor.addActionListener(e -> {
			try {
				int idProveedor = Integer.parseInt(txtIdProveedor.getText());
				ctrl.accion(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR, idProveedor);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID erroneo.");
			}
		});
	}
}
