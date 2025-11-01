/**
 * 
 */
package presentacion.Cliente;

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

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteSocio;
import negocio.Cliente.TClienteNoSocio;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;
import presentacion.Cliente.VAltaCliente;
import presentacion.Cliente.VBajaCliente;
import presentacion.Cliente.VBuscarCliente;
import presentacion.Cliente.VModificarCliente;
import presentacion.Cliente.VMostrarCliente;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class GUICliente extends JPanel implements IGUI {
	private static final long serialVersionUID = 1L;

	public GUICliente() {
		initGui();
	}
	
	private void initGui() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO CLIENTE", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel izquierdo con buscar y modificar

		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarCliente());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarCliente());
		panelIzquierda.add(Box.createVerticalStrut(10));
		// Panel derecho con baja, mostrar y alta

		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaCliente());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VMostrarCliente());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaCliente());
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
		case RES_ALTA_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, "Cliente dado de alta con ID: " + datos);
			break;
		case RES_ALTA_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el cliente.");
			break;
		case RES_BAJA_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, "Cliente dado de baja correctamente.");
			break;
		case RES_BAJA_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el cliente.");
			break;
		case RES_MODIFICAR_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
			break;
		case RES_MODIFICAR_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar el cliente. Verifica los datos.");
			break;
		case RES_BUSCAR_CLIENTE_OK:
			JOptionPane.showMessageDialog(null, (TCliente) datos);
			break;
		case RES_BUSCAR_CLIENTE_KO:
			JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
			break;
		case RES_MOSTRAR_TODOS_CLIENTES_OK:
			mostrarTabla((Collection<TCliente>) datos);
			break;
		case RES_MOSTRAR_TODOS_CLIENTES_KO:
			JOptionPane.showMessageDialog(null, "No hay clientes para mostrar.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
	
	private void mostrarTabla(Collection<TCliente> clientes) {
		String[] columnNames = { "ID", "Nombre", "DNI", "Tipo", "Numero Socio", "Puntos de Socio", "Numero Visitas", "Activo" };

		Object[][] data = new Object[clientes.size()][columnNames.length];
		int i = 0;
		for (TCliente cli : clientes) {
			data[i][0] = cli.getId();
			data[i][1] = cli.getNombre();
			data[i][2] = cli.getDni();
			data[i][7] = cli.getActivo();
			if (cli instanceof TClienteSocio) {
				data[i][4] = "Socio";
				data[i][5] = ((TClienteSocio) cli).getNumeroDeSocio();
				data[i][6] = ((TClienteSocio) cli).getPuntos();
				data[i][7] = "---";
			} else {
				data[i][4] = "No Socio";
				data[i][5] = "---";
				data[i][6] = "---";
				data[i][7] = ((TClienteNoSocio) cli).getNumVisitas();
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