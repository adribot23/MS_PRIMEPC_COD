package presentacion.Proveedor;
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
import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/**
 * * <!-- begin-UML-doc --> <!-- end-UML-doc --> * * @author adria * @generated
 * "UML a Java *
 * (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VMostrarProveedor extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JButton btnMostrar, btnVolver;

	public VMostrarProveedor() {
		super("Mostrar Proveedores");
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Proveedores"));
		btnMostrar = new JButton("Mostrar todos los proveedores");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_PROVEEDORES, null)));
		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			dispose();
		});
		panel.add(btnMostrar);
		panel.add(btnVolver);
		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
		case VMOSTRAR_TODOS_PROVEEDORES:
			setVisible(true);
			break;
		case RES_MOSTRAR_TODOS_PROVEEDORES_OK:
			mostrarTabla((Set<TProveedor>) datos);
			break;
		case RES_MOSTRAR_TODOS_PROVEEDORES_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar los proveedores.");
			break;
		default:
			break;
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