/**
 * 
 */
package presentacion.Proveedor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
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
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class GUIProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIProveedor() {
		super("[PROVEEDOR]");
		initGUI();
	}

	private void initGUI() {
		// === PANEL PRINCIPAL ===
		JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		mainPanel.setBackground(Color.WHITE);
		this.setContentPane(mainPanel);

		// === CABECERA ===
		JLabel titulo = new JLabel("GESTIÓN DE PROVEEDORES", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 28));
		titulo.setForeground(new Color(0, 100, 0));
		titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		mainPanel.add(titulo, BorderLayout.NORTH);

		// === PANEL DE BOTONES ===
		JPanel botonesPanel = new JPanel(new GridLayout(3, 3, 20, 20));
		botonesPanel.setBackground(Color.WHITE);

		// Fila 1
		botonesPanel.add(crearBotonVerde("ALTA PROVEEDOR", Evento.VALTA_PROVEEDOR));
		botonesPanel.add(crearBotonVerde("BAJA PROVEEDOR", Evento.VBAJA_PROVEEDOR));
		botonesPanel.add(crearBotonVerde("MODIFICAR PROVEEDOR", Evento.VMODIFICAR_PROVEEDOR));

		// Fila 2
		botonesPanel.add(crearBotonVerde("BUSCAR PROVEEDOR", Evento.VBUSCAR_PROVEEDOR));
		botonesPanel.add(crearBotonVerde("LISTAR TODOS LOS PROVEEDORES", Evento.VMOSTRAR_TODOS_PROVEEDORES));
		botonesPanel.add(crearBotonVerde("LISTAR PROVEEDORES POR PRODUCTO", Evento.VMOSTRAR_PROVEEDORES_POR_PRODUCTO));

		// Fila 3
		botonesPanel
				.add(crearBotonVerde("VINCULAR PRODUCTO A          PROVEEDOR", Evento.VVINCULAR_PRODUCTO_PROVEEDOR));
		botonesPanel.add(crearBotonVerde("DESVINCULAR PRODUCTO DE PROVEEDOR", Evento.VDESVINCULAR_PRODUCTO_PROVEEDOR));
		botonesPanel.add(crearBotonVerde("PROVEEDOR CON MÁS UNIDADES VENDIDAS", Evento.VPROVEEDOR_CON_MAS_UDS));

		mainPanel.add(botonesPanel, BorderLayout.CENTER);

		// === PANEL INFERIOR (VOLVER) ===
		JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
		volver.setFont(new Font("Segoe UI", Font.BOLD, 16));
		volver.setBackground(new Color(255, 255, 255));
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
