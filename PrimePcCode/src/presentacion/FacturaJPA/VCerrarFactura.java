/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import negocio.FacturaJPA.TCarritoFactura;
import negocio.FacturaJPA.TFactura;
import negocio.FacturaJPA.TLineaFactura;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;


public class VCerrarFactura extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private final DefaultTableModel tableModel;
	private TCarritoFactura carrito;


	public VCerrarFactura() {
		super("Cerrar factura");

		tableModel = new DefaultTableModel(new Object[] { "Id paquete", "precio" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		getRootPane().setBorder(BorderFactory.createTitledBorder("Cerrar factura"));
		setLayout(new BorderLayout(10, 10));

		// === PANEL NORTE: Información ===
		JLabel infoLabel = new JLabel(
				"<html><center>Ha entrado en el proceso de factura<br>CERRAR FACTURA para finalizar - VOLVER para salir</center></html>");
		infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(infoLabel, BorderLayout.NORTH);

		// === PANEL CENTRO: Tabla de productos + Botones añadir/eliminar ===
		JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

		// Tabla de productos
		JTable tabla = new JTable(tableModel);
		tabla.setFillsViewportHeight(true);
		JScrollPane scrollTabla = new JScrollPane(tabla);
		scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de paquetes"));
		centerPanel.add(scrollTabla, BorderLayout.CENTER);

		// Botones de añadir/eliminar paquete
		JPanel botonesPaquetePanel = new JPanel(new GridLayout(1, 2, 10, 10));
		botonesPaquetePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JButton anadirButton = new JButton("Añadir paquete");
		anadirButton.setBackground(new Color(200, 255, 200));
		anadirButton.addActionListener(e -> onAnadir());
		botonesPaquetePanel.add(anadirButton);

		JButton eliminarButton = new JButton("Eliminar paquete");
		eliminarButton.setBackground(new Color(255, 200, 200));
		eliminarButton.addActionListener(e -> onEliminar());
		botonesPaquetePanel.add(eliminarButton);

		centerPanel.add(botonesPaquetePanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		// === PANEL SUR: Datos de la venta + Botones cerrar/volver ===
		JPanel southPanel = new JPanel(new BorderLayout(5, 5));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Botones de cerrar venta y volver
		JPanel botonesFinalesPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		botonesFinalesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

		JButton cerrarButton = new JButton("Cerrar factura");
		cerrarButton.setBackground(new Color(200, 255, 200));
		cerrarButton.addActionListener(e -> onCerrar());
		botonesFinalesPanel.add(cerrarButton);

		JButton volverButton = new JButton("Volver");
		volverButton.setBackground(new Color(255, 220, 220));
		volverButton.addActionListener(e -> volver());
		botonesFinalesPanel.add(volverButton);

		southPanel.add(botonesFinalesPanel, BorderLayout.SOUTH);

		add(southPanel, BorderLayout.SOUTH);

		setSize(550, 600);
		setLocationRelativeTo(null);
	}

	private void onAnadir() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_INSERTAR, carrito));
			dispose();
		}
	}

	private void onEliminar() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_ELIMINAR, carrito));
			dispose();
		}
	}

	private void onCerrar() {
		if (carrito != null) {
			try {

				TFactura factura = carrito.get_tFactura();
				if (factura == null) {
					factura = new TFactura();
				}
				carrito.set_tFactura(factura);

				Controlador.getInstancia().accion(new Context(Evento.CERRAR_FACTURA, carrito));
				dispose();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
		dispose();
	}

	

	private void cerrarTodasLasVentanasDeEsteipo() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VCerrarFactura && window.isVisible()) {
				window.dispose();
			}
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VCERRAR_FACTURA:
			tableModel.setRowCount(0);
			setVisible(true);
			break;
		case RES_PASAR_CARRITOFACTURA_A_CERRAR_OK:
			if (datos instanceof TCarritoFactura) {
				carrito = (TCarritoFactura) datos;
				actualizarTabla();
				setVisible(true);
			}
			break;
		case RES_PASAR_CARRITOFACTURA_A_CERRAR_KO:
			if (datos instanceof TCarritoFactura) {
				carrito = (TCarritoFactura) datos;
				actualizarTabla();
				setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Error en el traspaso del carrito.");
				dispose();
			}
			break;
		case RES_CERRAR_FACTURA_OK:
			cerrarTodasLasVentanasDeEsteipo();
			if (datos instanceof TCarritoFactura) {
				String factura = rellenarFactura((TCarritoFactura) datos);
				JOptionPane.showMessageDialog(null, "Factura cerrada." + factura);
			} else {
				JOptionPane.showMessageDialog(null, "Factura cerrada correctamente.");
			}
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			break;
		case RES_CERRAR_FACTURA_KO:
			cerrarTodasLasVentanasDeEsteipo();
			JOptionPane.showMessageDialog(null, "Error al cerrar factura.");
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			break;
		default:
			break;
		}
	}

	private void actualizarTabla() {
		tableModel.setRowCount(0);
		if (carrito != null && carrito.get_tLineasFactura() != null) {
			for (TLineaFactura linea : carrito.get_tLineasFactura()) {
				Object[] fila = { linea.get_idPaquete(), linea.get_precioTotal() };
				tableModel.addRow(fila);
			}
		}
	}

	private String rellenarFactura(TCarritoFactura carrito) {
		TFactura factura = carrito.get_tFactura();
		java.util.Set<TLineaFactura> lineasFactura = carrito.get_tLineasFactura();

		StringBuilder builder = new StringBuilder();
		if (factura != null) {
			builder.append("\n\nFactura finalizada - ID: ").append(factura.get_idFactura());
			builder.append("\nRemitente: ").append(factura.get_idRemitente());
			builder.append("\nPrecio total: ").append(factura.get_precioTotal()).append(" EUR");
			builder.append("\n--------------------");
		}

		if (lineasFactura != null && !lineasFactura.isEmpty()) {
			for (TLineaFactura linea : lineasFactura) {
				builder.append("\nPaquete: ").append(linea.get_idPaquete());
				builder.append(" - Precio: ").append(linea.get_precioTotal()).append(" EUR");
				builder.append("\n--------------------");
			}
		}

		return builder.toString();
	}
}