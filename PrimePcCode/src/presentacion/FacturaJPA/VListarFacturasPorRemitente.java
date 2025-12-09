package presentacion.FacturaJPA;

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

import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VListarFacturasPorRemitente extends JFrame implements IGUI{
	
	public VListarFacturasPorRemitente(){
		super("Listar Por Remitente");
		initGUI();
	}
	
	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Abrir Factura"));

		JLabel lblRemitente = new JLabel("Id Remitente");
		JTextField txtRemitente = new JTextField();

		JButton btnAbrir = new JButton("Listar Facturas Por Remitente");
		btnAbrir.setBackground(new Color(200, 255, 200));

		btnAbrir.addActionListener(e -> {

			try {
				
				if (txtRemitente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena el id del remitente.");
					return;
				}
				
				Integer rem = Integer.parseInt(txtRemitente.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.VER_FACTURAS_POR_REMITENTE, rem));

				txtRemitente.setText("");

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Datos inválidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		});

		add(lblRemitente);
		add(txtRemitente);
		add(btnAbrir);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
	}

	private void mostrarTabla(Set<TFactura> facturas) {
		String[] columnNames = { "ID Factura", "ID Remitente", "Precio Total" }; 
		Object[][] tableData = new Object[facturas.size()][columnNames.length];

		int i = 0;
		for (TFactura f : facturas) {
			tableData[i][0] = f.get_idFactura();
			tableData[i][1] = f.get_idRemitente();
			tableData[i][2] = f.get_precioTotal();
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
		case VVER_FACTURAS_POR_REMITENTE:
			setVisible(true);
			break;
		case RES_VER_FACTURAS_POR_REMITENTE_OK:
			mostrarTabla((Set<TFactura>) datos);
			break;
		case RES_VER_FACTURAS_POR_REMITENTE_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar las facturas.");
			break;
		default:
			break;
		}
	}

}
