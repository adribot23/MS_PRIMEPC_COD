/**
 * 
 */
package presentacion.Almacen;

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

import negocio.Almacen.TAlmacen;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;
import presentacion.Almacen.VAltaAlmacen;
import presentacion.Almacen.VBajaAlmacen;
import presentacion.Almacen.VBuscarAlmacen;
import presentacion.Almacen.VModificarAlmacen;
import presentacion.Almacen.VMostrarAlmacen;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class GUIAlmacen extends JPanel implements IGUI {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private void initGui() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO ALMACEN", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);
		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel_Izquierda
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarAlmacen());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VMostrarAlmacen());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarAlmacen());
		panelIzquierda.add(Box.createVerticalStrut(10));

		// Panel_Derecha
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaAlmacen());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaAlmacen());
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
}