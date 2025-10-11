package presentacion.vista.proveedor;

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

import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.factoria.Evento;
import presentacion.vista.IGUI;

public class GUIProveedor extends JPanel implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIProveedor() {
		initGui();
	}

	private void initGui() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO PROVEEDOR", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel_Izquierda
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VVerPorProducto());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VMostrarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));

		// Panel_Derecha
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VVincularProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaProveedor());
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
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
		case RES_ALTA_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Proveedor dado de alta con ID: " + datos);
			break;
		case RES_ALTA_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el proveedor.");
			break;
		case RES_BAJA_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Proveedor dado de baja correctamente.");
			break;
		case RES_BAJA_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el proveedor.");
			break;
		case RES_MODIFICAR_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Proveedor modificado correctamente.");
			break;
		case RES_MODIFICAR_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar proveedor.");
			break;
		case RES_BUSCAR_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, datos.toString());
			break;
		case RES_BUSCAR_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Proveedor no encontrado.");
			break;
		case RES_MOSTRAR_TODOS_PROVEEDORES_OK:
			mostrarTabla((Collection<TProveedor>) datos);
			break;
		case RES_MOSTRAR_TODOS_PROVEEDORES_KO:
			JOptionPane.showMessageDialog(null, "No hay proveedores para mostrar.");
			break;
		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK:
			JOptionPane.showMessageDialog(null, "Producto suministrado por " + (TProveedor) datos);
			break;
		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "No se encontro proveedor para ese producto.");
			break;
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Producto vinculado al proveedor correctamente.");
			break;
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "No se pudo vincular el producto al proveedor.");
			break;
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Producto desvinculado al proveedor correctamente.");
			break;
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "No se pudo desvincular el producto del proveedor.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTabla(Collection<TProveedor> proveedores) {

		String[] columnNames = { "ID", "Nombre", "Activo" };
		Object[][] tableData = new Object[proveedores.size()][columnNames.length];

		int i = 0;
		for (TProveedor p : proveedores) {
			tableData[i][0] = p.getId();
			tableData[i][1] = p.getNombre();
			tableData[i][2] = p.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Proveedores", JOptionPane.PLAIN_MESSAGE);

	}
}