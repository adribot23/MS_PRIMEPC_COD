package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.Producto.TProducto;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton btnMostrar, btnVolver;

	public VMostrarProducto() {
		super("Mostrar Productos");
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Productos"));

		btnMostrar = new JButton("Mostrar todos los productos");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_PRODUCTOS, null)));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			dispose();
		});

		panel.add(btnMostrar);
		panel.add(btnVolver);

		add(panel);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_TODOS_PRODUCTOS:
			setVisible(true);
			break;

		case RES_MOSTRAR_TODOS_PRODUCTOS_OK:
			mostrarTabla((Set<TProducto>) datos);
			break;

		case RES_MOSTRAR_TODOS_PRODUCTOS_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar los productos.");
			break;

		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}

	private void mostrarTabla(Set<TProducto> productos) {
		if (productos == null || productos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay productos registrados.");
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
		JOptionPane.showMessageDialog(null, scrollPane, "Productos", JOptionPane.PLAIN_MESSAGE);
	}
}
