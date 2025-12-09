/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.FacturaJPA.TFactura;
import negocio.FacturaJPA.TFacturaTOA;
import negocio.FacturaJPA.TLineaFactura;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;
import presentacion.Venta.VBuscarVenta;


public class VBuscarFactura extends JFrame implements IGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea resultadoArea = new JTextArea();

	public VBuscarFactura() {
		super("Buscar Factura");
		initGUI();
	}

	private void initGUI() {
		
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Factura"));
		JTextField txtFactura = new JTextField();

		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		topPanel.add(new JLabel("Id factura:"));
		topPanel.add(txtFactura);
		JButton buscarButton = new JButton("Buscar");
		buscarButton.setBackground(new Color(200, 255, 200));
		buscarButton.addActionListener(e -> {try {
			int idFactura = Integer.parseInt(txtFactura.getText().trim());
			if (idFactura <= 0) {
				JOptionPane.showMessageDialog(this, "El Id factura debe ser un numero positivo.", "Datos incorrectos",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Controlador.getInstancia().accion(new Context(Evento.BUSCAR_FACTURA, idFactura));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id factura debe ser un numero entero positivo.", "Datos incorrectos",
					JOptionPane.ERROR_MESSAGE);
		}finally{
			txtFactura.setText("");
			resultadoArea.setText("");}});
		
		topPanel.add(buscarButton);
		add(topPanel, BorderLayout.NORTH);

		resultadoArea.setEditable(false);
		resultadoArea.setLineWrap(true);
		resultadoArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(resultadoArea);
		scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
		add(scroll, BorderLayout.CENTER);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		});
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(btnVolver);
		add(bottomPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private VBuscarFactura obtenerVentanaOriginal() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VBuscarVenta && window.isVisible() && window != this) {
				return (VBuscarFactura) window;
			}
		}
		return null;
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_FACTURA:
			setVisible(true);
			break;
		case RES_BUSCAR_FACTURA_OK:
			VBuscarFactura ventanaOriginal = obtenerVentanaOriginal();
			if (ventanaOriginal != null) {
				String factura = rellenarFactura(datos);
				ventanaOriginal.resultadoArea.setText(factura);
				dispose();
			} else {
				String factura = rellenarFactura(datos);
				resultadoArea.setText(factura);
				setVisible(true);
			}
			break;
		case RES_BUSCAR_FACTURA_KO:
			VBuscarFactura ventanaOriginalKO = obtenerVentanaOriginal();
			if (ventanaOriginalKO != null) {
				dispose();
				JOptionPane.showMessageDialog(ventanaOriginalKO, "Factura no encontrada.");
			} else {
				JOptionPane.showMessageDialog(this, "Factura no encontrada.");
				setVisible(true);
			}
			break;
		default:
			break;
		}
	}

	private String rellenarFactura(Object datos) {
		if (datos == null) {
			return "No hay información disponible.";
		}

		if (datos instanceof TFacturaTOA) {
			return formatearFacturaTOA((TFacturaTOA) datos);
		}

		if (datos instanceof TFactura) {
			return formatearFacturaSimple((TFactura) datos);
		}

		return String.valueOf(datos);
	}

	private String formatearFacturaSimple(TFactura f) {
		if (f == null) {
			return "Factura no disponible.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Factura encontrada con id: ").append(f.get_idFactura()).append("\n");
		sb.append("Remitente: ").append(f.get_idRemitente()).append("\n");
		sb.append("Precio Total: ").append(f.get_precioTotal()).append("\n");
		sb.append("Activa: ").append(f.get_activo() == 1 ? "Si" : "No");
		return sb.toString();
	}

	private String formatearFacturaTOA(TFacturaTOA toa) {
		TFactura factura = toa.get_tFactura();
		TRemitente remitente = toa.get_tRemitente();
		Set<TLineaFactura> lineasFactura = toa.get_tLineasFactura();

		String cabecera = rellenarCabecera(factura, remitente);
		String lineas = rellenarLineasFactura(lineasFactura);

		return cabecera + lineas;
	}

	private String rellenarCabecera(TFactura tFactura, TRemitente tRemitente) {
		StringBuilder builder = new StringBuilder();

		if (tFactura != null) {
			builder.append("Se ha encontrado factura con id: ").append(tFactura.get_idFactura()).append("\n");
		}

		if (tRemitente != null) {
			builder.append("Remitente: ").append(tRemitente.getId());
			builder.append(", Nombre: ").append(tRemitente.getNombre() != null ? tRemitente.getNombre() : "-");
			builder.append(", Direccion: ").append(tRemitente.getDireccion() != null ? tRemitente.getDireccion() : "-");
			builder.append(", Telefono: ").append(tRemitente.getTelefono() != null ? tRemitente.getTelefono() : "-");
			builder.append(", activo: ").append(tRemitente.getActivo() == 1 ? "Si" : "No").append("\n");
		}

		if (tFactura != null) {
			builder.append("Total factura: ").append(tFactura.get_precioTotal()).append(" EUR\n");
			builder.append("-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}

	private String rellenarLineasFactura(Set<TLineaFactura> lineas) {
		StringBuilder builder = new StringBuilder();

		if (lineas == null || lineas.isEmpty()) {
			return builder.toString();
		}

		Iterator<TLineaFactura> itLineas = lineas.iterator();

		while (itLineas.hasNext()) {
			TLineaFactura linea = itLineas.next();
			builder.append("id_producto: ").append(linea.get_idPaquete());
			builder.append(", precio: ").append(linea.get_precioTotal()).append(" EUR");
			builder.append(", devuelto: ").append(linea.get_devuelto());
				
			builder.append("-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}
}