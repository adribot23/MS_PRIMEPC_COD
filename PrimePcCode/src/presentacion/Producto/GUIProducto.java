/**
 * 
 */
package presentacion.Producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
public class GUIProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIProducto() {
		super("[PRODUCTO]");
		initGUI();
	}

	private void initGUI() {
		// === PANEL PRINCIPAL ===
		JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		mainPanel.setBackground(Color.WHITE);
		this.setContentPane(mainPanel);

		// === CABECERA ===
		JLabel titulo = new JLabel("GESTIÓN DE PRODUCTOS", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 28));
		titulo.setForeground(new Color(0, 100, 0));
		titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		mainPanel.add(titulo, BorderLayout.NORTH);

		// === PANEL DE BOTONES ===
		JPanel botonesPanel = new JPanel(new GridLayout(3, 3, 20, 20));
		botonesPanel.setBackground(Color.WHITE);

		// Fila 1
		botonesPanel.add(crearBotonVerde("ALTA PRODUCTO", Evento.VALTA_PRODUCTO));
		botonesPanel.add(crearBotonVerde("BAJA PRODUCTO", Evento.VBAJA_PRODUCTO));
		botonesPanel.add(crearBotonVerde("MODIFICAR PRODUCTO", Evento.VMODIFICAR_PRODUCTO));

		// Fila 2
		botonesPanel.add(crearBotonVerde("BUSCAR PRODUCTO", Evento.VBUSCAR_PRODUCTO));
		botonesPanel.add(crearBotonVerde("LISTAR TODOS LOS PRODUCTOS", Evento.VMOSTRAR_TODOS_PRODUCTOS));
		botonesPanel.add(crearBotonVerde("LISTAR PRODUCTOS POR PROVEEDOR", Evento.VMOSTRAR_PRODUCTOS_POR_PROVEEDOR));

		// Fila 3
		botonesPanel.add(crearBotonVerde("LISTAR PRODUCTOS POR ALMACÉN", Evento.VMOSTRAR_PRODUCTOS_POR_ALMACEN));
	

		mainPanel.add(botonesPanel, BorderLayout.CENTER);

		// === PANEL INFERIOR (VOLVER) ===
		JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
		volver.setFont(new Font("Segoe UI", Font.BOLD, 16));
		volver.setBackground(Color.WHITE);
		volver.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
		volver.setFocusPainted(false);
		volver.setPreferredSize(new Dimension(300, 40));

		volver.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});

		JPanel volverPanel = new JPanel();
		volverPanel.setBackground(Color.WHITE);
		volverPanel.add(volver);
		mainPanel.add(volverPanel, BorderLayout.SOUTH);

		// === CONFIGURACIÓN FRAME ===
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(900, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JButton crearBotonVerde(String texto, Evento evento) {
		JButton boton = new JButton("<html><center>" + texto + "</center></html>");
		boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boton.setFocusPainted(false);
		boton.setBackground(Color.WHITE);
		boton.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));
		boton.setOpaque(true);
		boton.setPreferredSize(new Dimension(250, 100));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Efecto hover
		boton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				boton.setBackground(new Color(220, 255, 220));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				boton.setBackground(Color.WHITE);
			}
		});

		// Acción del botón
		boton.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(evento, null));
			this.dispose();
		});

		return boton;
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
*/