/**
 * 
 */
package presentacion.Almacen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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
public class GUIAlmacen extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    public GUIAlmacen() {
        super("[ALMACÉN]");
        initGUI();
    }

    private void initGUI() {
        // === PANEL PRINCIPAL ===
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);
        this.setContentPane(mainPanel);

        // === CABECERA ===
        JLabel titulo = new JLabel("GESTIÓN DE ALMACENES", SwingConstants.CENTER);
        titulo.setFont(new Font("Cambria", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 100, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // === PANEL DE BOTONES ===
        JPanel botonesPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        botonesPanel.setBackground(Color.WHITE);

        // Fila 1
        botonesPanel.add(crearBotonVerde("ALTA ALMACÉN", Evento.VALTA_ALMACEN));
        botonesPanel.add(crearBotonVerde("BAJA ALMACÉN", Evento.VBAJA_ALMACEN));
        botonesPanel.add(crearBotonVerde("MODIFICAR ALMACÉN", Evento.VMODIFICAR_ALMACEN));

        // Fila 2
        botonesPanel.add(crearBotonVerde("BUSCAR ALMACÉN", Evento.VBUSCAR_ALMACEN));
        botonesPanel.add(crearBotonVerde("LISTAR TODOS LOS ALMACENES", Evento.VMOSTRAR_TODOS_ALMACENES));
        botonesPanel.add(new JLabel()); // espacio vacío para estética

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
		case RES_ALTA_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, "Almacen dado de alta con ID: " + (int) datos);
			break;
		case RES_ALTA_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el almacen.");
			break;
		case RES_BAJA_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, "Almacen dado de baja correctamente.");
			break;
		case RES_BAJA_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el almacen.");
			break;
		case RES_MODIFICAR_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, "Almacen modificado correctamente.");
			break;
		case RES_MODIFICAR_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar el almacen. Verifica los datos.");
			break;
		case RES_BUSCAR_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, (TAlmacen) datos);
			break;
		case RES_BUSCAR_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Almacen no encontrado.");
			break;
		case RES_MOSTRAR_TODOS_ALMACENES_OK:
			mostrarTablaAlmacenes((Collection<TAlmacen>) datos);
			break;
		case RES_MOSTRAR_TODOS_ALMACENES_KO:
			JOptionPane.showMessageDialog(null, "No hay almacenes para mostrar.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTablaAlmacenes(Collection<TAlmacen> almacenes) {
		String[] columnNames = { "ID", "Nombre", "Capacidad Máxima", "Ocupación", "Activo" };
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
	*/

    