/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.FacturaJPA.TFactura;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarFactura extends JFrame implements IGUI {
	

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "ID Factura", "ID Remitente", "Precio Total", "Activo" };

	private DefaultTableModel _table; // TODO REVISAR IMPORT

	public VMostrarFactura() {
		super("Mostrar Facturas");
		initGUI();
	}

	private void initGUI() {	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Facturas"));

		JButton btnMostrar = new JButton("Mostrar todas las facturas");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODAS_FACTURAS, null)));

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			dispose();
		});

		panel.add(btnMostrar);
		panel.add(btnVolver);
		add(panel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
	}
	
	private void mostrarTabla(Set<TFactura> facturas) {
		String[] columnNames = { "ID Factura", "ID Remitente", "Precio Total", "Activo" }; //hace falta num paquetes?
		Object[][] tableData = new Object[facturas.size()][columnNames.length];

		int i = 0;
		for (TFactura f : facturas) {
			tableData[i][0] = f.get_idFactura();
			tableData[i][1] = f.get_idRemitente();
			tableData[i][2] = f.get_precioTotal();
			tableData[i][3] = f.get_activo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Facturas", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VMOSTRAR_TODAS_FACTURAS:
			setVisible(true);
			break;
		case RES_MOSTRAR_TODAS_FACTURAS_OK:
			mostrarTabla((Set<TFactura>) datos);
			break;
		case RES_MOSTRAR_TODAS_FACTURAS_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar las facturas.");
			break;
		default:
			break;
		}
	}
}