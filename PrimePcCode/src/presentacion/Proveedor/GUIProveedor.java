/**
 * 
 */
package presentacion.Proveedor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.Proveedor.VAltaProveedor;
import presentacion.Proveedor.VBajaProveedor;
import presentacion.Proveedor.VBuscarProveedor;
import presentacion.Proveedor.VModificarProveedor;
import presentacion.Proveedor.VMostrarProveedor;
import presentacion.Proveedor.VVerPorProducto;
import presentacion.Proveedor.VVincularProveedor;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class GUIProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIProveedor() {
		super("[PROVEEDOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(800, 300));

		JPanel controlPanel = new JPanel();
		mainPanel.add(controlPanel);
		rellenaControlPanel(controlPanel);

		JPanel volverPanel = new JPanel();
		mainPanel.add(volverPanel, BorderLayout.PAGE_END);
		rellenarVolverPanel(volverPanel);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void rellenarVolverPanel(JPanel volverPanel) {
		JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
		volver.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});
		volverPanel.add(volver, BorderLayout.SOUTH);
	}

	private void rellenaControlPanel(JPanel controlPanel) {
		controlPanel.setLayout(new BorderLayout());

		JPanel panelNorte = new JPanel();
		JPanel panelSur = new JPanel();

		// ----- Botones principales -----

		JButton alta = new JButton("ALTA PROVEEDOR");
		alta.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VALTA_PROVEEDOR, null));
			this.dispose();
		});
		panelNorte.add(alta);

		JButton baja = new JButton("BAJA PROVEEDOR");
		baja.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VBAJA_PROVEEDOR, null));
			this.dispose();
		});
		panelNorte.add(baja);

		JButton modificar = new JButton("MODIFICAR PROVEEDOR");
		modificar.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VMODIFICAR_PROVEEDOR, null));
			this.dispose();
		});
		panelNorte.add(modificar);

		JButton buscar = new JButton("BUSCAR PROVEEDOR");
		buscar.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VBUSCAR_PROVEEDOR, null));
			this.dispose();
		});
		panelNorte.add(buscar);

		JButton listarTodos = new JButton("LISTAR TODOS LOS PROVEEDORES");
		listarTodos.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VMOSTRAR_TODOS_PROVEEDORES, null));
			this.dispose();
		});
		panelNorte.add(listarTodos);

		JButton listarPorProducto = new JButton("LISTAR PROVEEDORES POR PRODUCTO");
		listarPorProducto.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VMOSTRAR_PROVEEDORES_POR_PRODUCTO, null));
			this.dispose();
		});
		panelNorte.add(listarPorProducto);

		// ----- Vincular / Desvincular -----

		JButton vincular = new JButton("VINCULAR PRODUCTO A PROVEEDOR");
		vincular.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VVINCULAR_PRODUCTO_PROVEEDOR, null));
			this.dispose();
		});
		panelSur.add(vincular);

		JButton desvincular = new JButton("DESVINCULAR PRODUCTO DE PROVEEDOR");
		desvincular.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VDESVINCULAR_PRODUCTO_PROVEEDOR, null));
			this.dispose();
		});
		panelSur.add(desvincular);

		controlPanel.add(panelNorte, BorderLayout.NORTH);
		controlPanel.add(panelSur, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		setVisible(true);
	}
}
	/*
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
	*/
