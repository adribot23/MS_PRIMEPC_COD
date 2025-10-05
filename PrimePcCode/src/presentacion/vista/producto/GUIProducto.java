
package presentacion.vista.producto;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import negocio.transfers.TProducto;
import presentacion.vista.Evento;
import presentacion.vista.IGUI;

public class GUIProducto extends JPanel implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIProducto() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO PRODUCTO", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel izquierdo con buscar, mostrar todos y modificar
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarProducto());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VMostrarProducto());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarProducto());
		panelIzquierda.add(Box.createVerticalStrut(10));
		// Panel derecho con baja, mostrar por proveedor/almacén y alta
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaProducto());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VVerProdPorProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VVerProdPorAlmacen());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaProducto());
		panelDerecha.add(Box.createVerticalStrut(10));

		panelCentral.add(panelIzquierda);
		panelCentral.add(panelDerecha);
		add(panelCentral, BorderLayout.CENTER);

		// Boton_Volver
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(30, 200, 100));
		btnVolver.addActionListener(e -> {
			CardLayout cl = (CardLayout) this.getParent().getLayout();
			cl.show(this.getParent(), "Menu");
		});

		add(btnVolver, BorderLayout.SOUTH);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Evento evento, Object datos) {
		switch (evento) {
		case RES_ALTA_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, "Producto dado de alta con ID: " + datos);
			break;
		case RES_ALTA_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el producto.");
			break;
		case RES_BAJA_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, "Producto dado de baja correctamente.");
			break;
		case RES_BAJA_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el producto.");
			break;
		case RES_MODIFICAR_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
			break;
		case RES_MODIFICAR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar producto.");
			break;
		case RES_BUSCAR_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, datos.toString());
			break;
		case RES_BUSCAR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "Producto no encontrado.");
			break;
		case RES_MOSTRAR_TODOS_PRODUCTOS_OK:
			mostrarTabla((Collection<TProducto>) datos);
			break;
		case RES_MOSTRAR_TODOS_PRODUCTOS_KO:
			JOptionPane.showMessageDialog(null, "No hay productos para mostrar.");
			break;
		case RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK:
			mostrarTabla((Collection<TProducto>) datos);
			break;
		case RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "No se encontraron productos para ese proveedor.");
			break;
		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK:
			mostrarTabla((Collection<TProducto>) datos);
			break;
		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "No se encontraron productos para ese almacen.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTabla(Collection<TProducto> productos) {

		String[] columnNames = { "ID", "Marca", "Modelo", "Precio", "Unidades", "ID Proveedor", "ID Almacen",
				"Activo" };
		Object[][] tableData = new Object[productos.size()][columnNames.length];

		int i = 0;
		for (TProducto p : productos) {
			tableData[i][0] = p.getId();
			tableData[i][1] = p.getMarca();
			tableData[i][2] = p.getModelo();
			tableData[i][3] = p.getPrecio();
			tableData[i][4] = p.getUnidades();
			tableData[i][5] = (p.getIdProveedor() == -1) ? "NINGUNO" : p.getIdProveedor();
			tableData[i][6] = (p.getIdAlmacen() == -1) ? "NINGUNO" : p.getIdAlmacen();
			tableData[i][7] = p.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);

		JOptionPane.showMessageDialog(null, scrollPane, "Productos", JOptionPane.PLAIN_MESSAGE);
	}
}
