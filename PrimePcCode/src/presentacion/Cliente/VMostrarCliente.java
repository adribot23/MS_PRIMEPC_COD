/**
 * 
 */
package presentacion.Cliente;

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

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.Proveedor.TProveedor;
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
public class VMostrarCliente extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	private JButton btnMostrar, btnVolver;
	
	public VMostrarCliente() {
		super("Mostrar Clientes");
		initGUI();
	}
	
	public void initGUI() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Mostrar Clientes"));
		
		btnMostrar = new JButton("Mostrar todos los clientes");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(e ->
			Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_CLIENTES, null))
		);

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
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

		case VMOSTRAR_TODOS_CLIENTES:
			this.setVisible(true);
			break;
		case RES_MOSTRAR_TODOS_CLIENTES_OK:
			mostrarTabla((Set<TCliente> )datos);
			break;
		case RES_MOSTRAR_TODOS_CLIENTES_KO:
			JOptionPane.showMessageDialog(this, "No se pudieron mostrar los clientes.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
			
		}
	}
	
	private void mostrarTabla(Set<TCliente> clientes) {
		String[] columnNames = { "ID", "Nombre", "DNI", "Tipo", "Numero Socio", "Puntos de Socio", "Numero Visitas", "Activo" };

		Object[][] data = new Object[clientes.size()][columnNames.length];
		int i = 0;
		for (TCliente cli : clientes) {
			data[i][0] = cli.getId();
			data[i][1] = cli.getNombre();
			data[i][2] = cli.getDni();
			data[i][7] = cli.getActivo();
			if (cli instanceof TClienteSocio) {
				data[i][3] = "Socio";
				data[i][4] = ((TClienteSocio) cli).getNumeroDeSocio();
				data[i][5] = ((TClienteSocio) cli).getPuntos();
				data[i][6] = "---";
			} else {
				data[i][3] = "No Socio";
				data[i][4] = "---";
				data[i][5] = "---";
				data[i][6] = ((TClienteNoSocio) cli).getNumVisitas();
			}
			i++;
		}

		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);

		JOptionPane.showMessageDialog(null, scrollPane, "Clientes", JOptionPane.PLAIN_MESSAGE);
	}
}