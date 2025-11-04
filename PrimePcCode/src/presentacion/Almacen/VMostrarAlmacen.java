/**
 * 
 */
package presentacion.Almacen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.Almacen.TAlmacen;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VMostrarAlmacen extends JFrame implements IGUI {

	public VMostrarAlmacen() {
		super("Listar Almacenes");
		initGUI();
	}
	public void initGUI() {
		getRootPane().setBorder(BorderFactory.createTitledBorder("Listar Almacenes"));
		setLayout(new GridLayout(3, 1, 10, 10));

		JButton btnMostrarTodos = new JButton("Listar todos");
		btnMostrarTodos.setBackground(new Color(200, 255, 200)); // Verde
		btnMostrarTodos.addActionListener(e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_ALMACENES, null)));

		add(btnMostrarTodos, BorderLayout.NORTH);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		});
		
		add(btnVolver, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
	}

	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
			case VMOSTRAR_TODOS_ALMACENES:
				setVisible(true);
				break;

			case RES_MOSTRAR_TODOS_ALMACENES_OK:
				mostrarTabla((Set<TAlmacen>) datos);
				break;

			case RES_MOSTRAR_TODOS_ALMACENES_KO:
				JOptionPane.showMessageDialog(this, "No se pudieron mostrar los almacenes.");
				break;

			default:
				break;
		}
	}
	
	private void mostrarTabla(Set<TAlmacen> almacenes) {

		String[] columnNames = { "ID", "Nombre", "Capacidad", "Ocupación", "Activo" };
		Object[][] tableData = new Object[almacenes.size()][columnNames.length];

		int i = 0;
		for (TAlmacen a : almacenes) {
			tableData[i][0] = a.getId();
			tableData[i][1] = a.getNombre();
			tableData[i][2] = a.getCapacidadMaxima();
			tableData[i][3] = a.getOcupacion();
			tableData[i][4] = a.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Almacenes", JOptionPane.PLAIN_MESSAGE);

	}
}