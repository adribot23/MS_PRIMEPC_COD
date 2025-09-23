package presentacion.vista.venta;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;

import negocio.transfers.TVenta;
import presentacion.vista.Evento;
import presentacion.vista.IGUI;

public class GUIVenta extends JPanel implements IGUI {
	
	private static final long serialVersionUID = 1L;

	public GUIVenta() {
		initGui();
	}

	private void initGui() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO VENTAS", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel_izquierda
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarVenta());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarVenta());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBajaVenta());
		panelIzquierda.add(Box.createVerticalStrut(10));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaVenta());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VMostrarVenta());
		panelDerecha.add(new VMostrarPorCliente());
		panelDerecha.add(new VMostrarPorEmpleado());
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
		case RES_ALTA_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta cerrada correctamente con id: " + (int) datos);
			break;
		case RES_ALTA_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Error al cerrar venta");
			break;
		case RES_BAJA_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Devolucion tramitada con exito");
			break;
		case RES_BAJA_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Error al hacer la devolucion");
			break;
		case RES_BUSCAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, (TVenta) datos);
			break;
		case RES_BUSCAR_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Venta no encontrada");
			break;
		case RES_MODIFICAR_VENTA_OK:
			JOptionPane.showMessageDialog(this, "Venta modificada");
			break;
		case RES_MODIFICAR_VENTA_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar venta");
			break;
		case RES_MOSTRAR_TODAS_VENTAS_OK:
			mostrarTabla((Collection<TVenta>) datos);
			break;
		case RES_MOSTRAR_TODAS_VENTAS_KO:
			JOptionPane.showMessageDialog(this, "No hay ventas");
			break;
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK:
			mostrarTabla((Collection<TVenta>) datos);
			break;
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO:
			JOptionPane.showMessageDialog(this, "No hay ventas para ese empleado");
			break;
		case RES_MOSTRAR_VENTAS_POR_CLIENTE_OK:
			mostrarTabla((Collection<TVenta>) datos);
			break;
		case RES_MOSTRAR_VENTAS_POR_CLIENTE_KO:
			JOptionPane.showMessageDialog(this, "No hay ventas para ese cliente");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

	private void mostrarTabla(Collection<TVenta> ventas) {
		String[] columnNames = { "ID", "Método de Pago", "Precio", "Descuento", "Importe Total", "ID Empleado",
				"ID Cliente", "Productos (id, cant)", "Activo" };

		Object[][] tableData = new Object[ventas.size()][columnNames.length];

		int i = 0;
		for (TVenta v : ventas) {
			tableData[i][0] = v.getId();
			tableData[i][1] = v.getMetodoPago();
			tableData[i][2] = v.getPrecio();
			tableData[i][3] = v.getDescuento();
			tableData[i][4] = v.getImporteTotal();
			tableData[i][5] = v.getIdEmpleado();
			tableData[i][6] = v.getIdCliente();

			StringBuilder productos = new StringBuilder();
			if (v.getProductos() != null) {
				for (Map.Entry<Integer, Integer> entry : v.getProductos().entrySet()) {
					productos.append("[").append(entry.getKey()).append(" : ").append(entry.getValue()).append("] ");
				}
			}
			tableData[i][7] = productos.toString().trim();

			tableData[i][8] = v.getActivo();
			i++;
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Ventas", JOptionPane.PLAIN_MESSAGE);
	}

}
