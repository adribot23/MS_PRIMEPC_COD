package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.Producto.TProducto;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;
public class VAltaProducto extends JFrame implements IGUI {

	private Controlador ctrl;

	public VAltaProducto() {
		super("Alta de Producto");
		initGUI();
	}

	public void initGUI() {
		// Configuración del layout y borde
		setLayout(new GridLayout(5, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Producto"));

		// Componentes de entrada
		JLabel lblMarca = new JLabel("Marca:");
		JTextField txtMarca = new JTextField();

		JLabel lblModelo = new JLabel("Modelo:");
		JTextField txtModelo = new JTextField();

		JLabel lblPrecio = new JLabel("Precio:");
		JTextField txtPrecio = new JTextField();

		JLabel lblUnidades = new JLabel("Unidades:");
		JTextField txtUnidades = new JTextField();

		// Botones
		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));

		// Acción del botón Alta
		btnAlta.addActionListener(e -> {
			try {
				String marca = txtMarca.getText().trim();
				String modelo = txtModelo.getText().trim();
				double precio = Double.parseDouble(txtPrecio.getText().trim());
				int unidades = Integer.parseInt(txtUnidades.getText().trim());

				if (marca.isEmpty() || modelo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos.");
					return;
				}

				TProducto nuevo = new TProducto(precio, modelo, unidades, marca);
				Controlador.getInstancia().accion(new Context(Evento.ALTA_PRODUCTO, nuevo));

				// Limpiar los campos
				txtMarca.setText("");
				txtModelo.setText("");
				txtPrecio.setText("");
				txtUnidades.setText("");

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos.");
			}
		});

		// Acción del botón Volver
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		});

		// Añadir componentes
		add(lblMarca);
		add(txtMarca);
		add(lblModelo);
		add(txtModelo);
		add(lblPrecio);
		add(txtPrecio);
		add(lblUnidades);
		add(txtUnidades);
		add(btnAlta);
		add(btnVolver);

		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
			case VALTA_PRODUCTO:
				this.setVisible(true);
				break;
			case RES_ALTA_PRODUCTO_OK:
				JOptionPane.showMessageDialog(null, "Producto dado de alta con ID: " + datos);
				break;
			case RES_ALTA_PRODUCTO_KO:
				JOptionPane.showMessageDialog(null, "Error al dar de alta el producto.");
				break;
			default:
				JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}