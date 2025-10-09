package presentacion.vista.cliente;

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

import negocio.transfers.TCliente;
import negocio.transfers.TClienteNoSocio;
import negocio.transfers.TClienteSocio;
import presentacion.factoria.Evento;
import presentacion.vista.IGUI;

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

		// Panel derecho con baja y alta
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
	public void actualizar(Evento evento, Object datos) {
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
			mostrarTablaClientes((Collection<TCliente>) datos);
			break;
		case RES_MOSTRAR_TODOS_CLIENTES_KO:
			JOptionPane.showMessageDialog(null, "No hay clientes para mostrar.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTablaClientes(Collection<TCliente> clientes) {
		String[] columnNames = { "ID", "Nombre", "DNI", "Tipo", "Puntos/Visitas", "Num Socio", "Activo" };
		Object[][] tableData = new Object[clientes.size()][columnNames.length];

		int i = 0;
		for (TCliente cli : clientes) {
			tableData[i][0] = cli.getId();
			tableData[i][1] = cli.getNombre();
			tableData[i][2] = cli.getDni();
			tableData[i][6] = cli.getActivo();

			if (cli instanceof TClienteSocio) {
				tableData[i][3] = "Socio";
				tableData[i][4] = ((TClienteSocio) cli).getPuntos() + " puntos";
				tableData[i][5] = ((TClienteSocio) cli).getNumeroDeSocio();
			} else {
				tableData[i][3] = "No Socio";
				tableData[i][4] = ((TClienteNoSocio) cli).getNumVisitas() + " visitas";
				tableData[i][5] = "-";
			}

			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);

		JOptionPane.showMessageDialog(null, scrollPane, "Clientes", JOptionPane.PLAIN_MESSAGE);
	}
}