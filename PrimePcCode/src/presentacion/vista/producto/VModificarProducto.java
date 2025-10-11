package presentacion.vista.producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.transfers.TProducto;
import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VModificarProducto extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarProducto() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(11, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar Producto"));

		JTextField txtModId = new JTextField();
		JTextField txtModPrecio = new JTextField();
		JTextField txtModModelo = new JTextField();
		JTextField txtModUnidades = new JTextField();
		JTextField txtModMarca = new JTextField();

		add(new JLabel("ID:"));
		add(txtModId);
		add(new JLabel("Marca:"));
		add(txtModMarca);
		add(new JLabel("Modelo:"));
		add(txtModModelo);
		add(new JLabel("Precio:"));
		add(txtModPrecio);
		add(new JLabel("Unidades:"));
		add(txtModUnidades);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtModId.getText());
				double precio = Double.parseDouble(txtModPrecio.getText());
				String modelo = txtModModelo.getText();
				int unidades = Integer.parseInt(txtModUnidades.getText());
				String marca = txtModMarca.getText();

				TProducto modificado = new TProducto(id, precio, modelo, unidades, marca);
				ctrl.accion(new Context(Evento.MODIFICAR_PRODUCTO, modificado));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Datos erroneos.");
			}
		});
		add(btnModificar);
	}

}
