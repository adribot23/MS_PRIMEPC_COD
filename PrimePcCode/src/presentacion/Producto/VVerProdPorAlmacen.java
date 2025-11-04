package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import negocio.Producto.TProducto;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerProdPorAlmacen extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idAlmacen;

	public VVerProdPorAlmacen() {
		super("Mostrar Productos por Almacén");
		initGUI();
	}

	private void initGUI() {
		// Configuración general
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Mostrar productos de un almacén"));

		// Componentes
		JLabel lblId = new JLabel("ID Almacén:");
		idAlmacen = new JTextField();

		JButton btnMostrar = new JButton("Mostrar Productos");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idAlmacen.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_PRODUCTOS_POR_ALMACEN, id));
				idAlmacen.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Introduce un número entero.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		});

		// Añadir componentes
		add(lblId);
		add(idAlmacen);
		add(btnMostrar);
		add(btnVolver);

		// Configuración final
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_PRODUCTOS_POR_ALMACEN:
			this.setVisible(true);
			break;

		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK:
			mostrarTabla((Set<TProducto>) datos);
			break;

		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "No se encontraron productos para ese almacén.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTabla(Set<TProducto> productos) {

		if (productos == null || productos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay productos en este almacén.");
			return;
		}

		String[] columnNames = { "ID", "Marca", "Modelo", "Precio", "Unidades", "Activo" };
		Object[][] tableData = new Object[productos.size()][columnNames.length];

		int i = 0;
		for (TProducto p : productos) {
			tableData[i][0] = p.getId();
			tableData[i][1] = p.getMarca();
			tableData[i][2] = p.getModelo();
			tableData[i][3] = p.getPrecio();
			tableData[i][4] = p.getUnidades();
			tableData[i][5] = p.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Productos del almacén", JOptionPane.PLAIN_MESSAGE);
	}
}
