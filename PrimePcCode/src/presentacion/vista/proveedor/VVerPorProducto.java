package presentacion.vista.proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VVerPorProducto extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VVerPorProducto() {
		ctrl = Controlador.obtenerInstancia();
		init();
	}

	private void init() {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Mostrar Proveedor por Producto"));

		JTextField mostrarProductoId = new JTextField();
		JButton btnMostrarPorProducto = new JButton("Mostrar Proveedor");
		btnMostrarPorProducto.setBackground(new Color(200, 255, 200));
		btnMostrarPorProducto.addActionListener(e -> {
			try {
				int idProducto = Integer.parseInt(mostrarProductoId.getText());
				ctrl.accion(new Context(Evento.MOSTRAR_PROVEEDORES_POR_PRODUCTO, idProducto));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});

		add(new JLabel("ID Producto:"));
		add(mostrarProductoId);
		add(btnMostrarPorProducto);
	}
}
