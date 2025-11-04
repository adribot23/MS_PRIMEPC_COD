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
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VModificarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField modId;
	private JTextField modMarca;
	private JTextField modModelo;
	private JTextField modPrecio;
	private JTextField modUnidades;
	private JTextField modAlmacen ;
	private JButton btnModificar, btnVolver;

	public VModificarProducto() {
		super("Modificar Producto");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(7, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Producto"));

		modId = new JTextField();
		modMarca = new JTextField();
		modModelo = new JTextField();
		modPrecio = new JTextField();
		modUnidades = new JTextField();
		modAlmacen = new JTextField();
		
		add(new JLabel("ID:"));
		add(modId);
		add(new JLabel("Marca:"));
		add(modMarca);
		add(new JLabel("Modelo:"));
		add(modModelo);
		add(new JLabel("Precio:"));
		add(modPrecio);
		add(new JLabel("Unidades:"));
		add(modUnidades);
		add(new JLabel("ID Almacen:"));
		add(modAlmacen);

	
	
		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modId.getText().trim());
				String marca = modMarca.getText().trim();
				String modelo = modModelo.getText().trim();
				double precio = Double.parseDouble(modPrecio.getText().trim());
				int unidades = Integer.parseInt(modUnidades.getText().trim());
				int idAlmacen = Integer.parseInt(modAlmacen.getText().trim());

				if (marca.isEmpty() || modelo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Marca y modelo no pueden estar vacíos.");
					return;
				}

				TProducto producto = new TProducto(id, precio, modelo, unidades, marca, idAlmacen);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_PRODUCTO, producto));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Datos inválidos. Revisa los campos numéricos.");
			}
		});

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			dispose();
		});

		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();

		switch (evento) {
		case VMODIFICAR_PRODUCTO:
			this.setVisible(true);
			break;

		case RES_MODIFICAR_PRODUCTO_OK:
			JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");
			limpiarCampos();
			break;

		case RES_MODIFICAR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar el producto. Verifica el ID o los datos.");
			break;

		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}

	private void limpiarCampos() {
		modId.setText("");
		modMarca.setText("");
		modModelo.setText("");
		modPrecio.setText("");
		modUnidades.setText("");
	}
}
