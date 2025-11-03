/**
 * 
 */
package presentacion.Proveedor;

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

import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VVerPorProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idProducto;

	public VVerPorProducto() {
		super("Mostrar Proveedores por Producto");
		initGUI();
	}

	private void initGUI() {
		// Configuración general
		setLayout(new GridLayout(4, 1, 100, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Mostrar proveedores que venden un producto"));

		// Componentes
		JLabel lblId = new JLabel("ID Producto:");
		idProducto = new JTextField();

		JButton btnMostrar = new JButton("Mostrar Proveedores");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idProducto.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_PROVEEDORES_POR_PRODUCTO, id));
				idProducto.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Introduce un número entero.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});

		// Añadir componentes
		add(lblId);
		add(idProducto);
		add(btnMostrar);
		add(btnVolver);

		// Configuración final
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_PROVEEDORES_POR_PRODUCTO:
			this.setVisible(true);
			break;

		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK:
			mostrarTabla((Set<TProveedor>) datos) ;
			break;

		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO:
			JOptionPane.showMessageDialog(null, "No se encontraron proveedores para ese producto.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
	private void mostrarTabla(Set<TProveedor> proveedores) {

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
