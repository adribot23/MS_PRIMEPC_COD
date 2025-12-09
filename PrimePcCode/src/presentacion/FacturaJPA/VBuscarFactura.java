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
import negocio.RemitenteJPA.TEmpresa;
import negocio.RemitenteJPA.TParticular;
import negocio.RemitenteJPA.TRemitente;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;



public class VBuscarFactura extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private final JTextField idFacturaField = new JTextField();
	private final JTextArea resultadoArea = new JTextArea();

	public VBuscarFactura() {
		super("Buscar factura");
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

		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar factura"));
		setLayout(new BorderLayout(10, 10));

		JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		topPanel.add(new JLabel("Id venta:"));
		topPanel.add(idFacturaField);
		JButton buscarButton = new JButton("Buscar");
		buscarButton.setBackground(new Color(200, 255, 200));
		buscarButton.addActionListener(e -> onBuscar());
		topPanel.add(buscarButton);
		add(topPanel, BorderLayout.NORTH);

		resultadoArea.setEditable(false);
		resultadoArea.setLineWrap(true);
		resultadoArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(resultadoArea);
		scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
		add(scroll, BorderLayout.CENTER);

		JButton volverButton = new JButton("Volver");
		volverButton.setBackground(new Color(255, 220, 220));
		volverButton.addActionListener(e -> volver());
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(volverButton);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void onBuscar() {
		try {
			int idFactura = Integer.parseInt(idFacturaField.getText().trim());
			if (idFactura <= 0) {
				JOptionPane.showMessageDialog(this, "El Id factura debe ser un numero positivo.", "Datos incorrectos",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Controlador.getInstancia().accion(new Context(Evento.BUSCAR_FACTURA, idFactura));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id factura debe ser un numero entero positivo.", "Datos incorrectos",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
		dispose();
	}

	private void limpiar() {
		idFacturaField.setText("");
		resultadoArea.setText("");
	}

	private VBuscarFactura obtenerVentanaOriginal() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VBuscarFactura && window.isVisible() && window != this) {
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
			limpiar();
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

	private String formatearFacturaSimple(TFactura factura) {
		if (factura == null) {
			return "Factura no disponible.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Factura encontrada con id: ").append(factura.get_idFactura()).append("\n");
		sb.append("Remitente: ").append(factura.get_idRemitente()).append("\n");
		sb.append("Precio Total: ").append(factura.get_precioTotal()).append("\n");
		
		return sb.toString();
	}

	private String formatearFacturaTOA(TFacturaTOA toa) {
		StringBuilder builder = new StringBuilder();

		TFactura factura = toa.get_tFactura();
		TRemitente remitente = toa.get_tRemitente();
		Set<TLineaFactura> lineasFactura = toa.get_tLineasFactura();

		String cabecera = rellenarCabecera(factura, remitente);
		String lineas = rellenarLineasFactura(lineasFactura);

		return cabecera + lineas;
	}

	private String rellenarCabecera(TFactura factura, TRemitente remitente) {
		StringBuilder builder = new StringBuilder();

		if (factura != null) {
			builder.append("Se ha encontrado factura con id: ").append(factura.get_idFactura()).append("\n");
		}

		if (remitente != null) {
			builder.append("Remitente: ").append(remitente.getId());
			builder.append(", Dirección: ").append(remitente.getDireccion() != null ? remitente.getDireccion() : "-");
			builder.append(", Nombre: ").append(remitente.getNombre() != null ? remitente.getNombre() : "-");
			builder.append(", Teléfono: ").append(remitente.getTelefono() != null ? remitente.getTelefono() : "-");
			
			if (remitente instanceof TEmpresa) {
				TEmpresa e = (TEmpresa) remitente;
				builder.append(", Tipo: Empresa");
				builder.append(", Número registro fiscal: ").append(e.getNumRegistroFiscal());
			}
			else if(remitente instanceof TParticular) {
				TParticular p = (TParticular) remitente;
				builder.append(", Tipo: Particular");
				builder.append(" Fecha de nacimiento: ").append(p.getFechaNacimiento());
			}
			
			
			builder.append(", Activo: ").append(remitente.getActivo() == 1 ? "Si" : "No").append("\n");
		}

		if (factura != null) {
			builder.append("Total factura: ").append(factura.get_precioTotal()).append(" EUR\n");
			builder.append("-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}

	private String rellenarLineasFactura(Set<TLineaFactura> lineasFactura) {
		StringBuilder builder = new StringBuilder();

		if (lineasFactura == null || lineasFactura.isEmpty()) {
			return builder.toString();
		}

		Iterator<TLineaFactura> itLineas = lineasFactura.iterator();

		while (itLineas.hasNext()) {
			TLineaFactura linea = itLineas.next();
			builder.append("Id_paquete: ").append(linea.get_idPaquete());
			builder.append(", Precio: ").append(linea.get_precioTotal()).append(" EUR");
			builder.append(", Devuelto: ").append(linea.get_devuelto() ==1 ? "Si":"no").append("\n");


			builder.append("-------------------------------------------------------------------------------------\n");
		}

		return builder.toString();
	}
}