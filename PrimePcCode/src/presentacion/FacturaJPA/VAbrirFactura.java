/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.FacturaJPA.TFactura;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;
import presentacion.Venta.VAbrirVenta;

public class VAbrirFactura extends JFrame implements IGUI {
	
	JTextField txtRemitente = new JTextField();
	
	public VAbrirFactura() {
		super("Abrir Factura");
		initGUI();
	}
	
	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Abrir Factura"));

		JLabel lblRemitente = new JLabel("Id Remitente");
		

		JButton btnAbrir = new JButton("Abrir Factura");
		btnAbrir.setBackground(new Color(200, 255, 200));

		btnAbrir.addActionListener(e -> {

			try {
				
				if (txtRemitente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena el id del remitente.");
					return;
				}
				
				Integer rem = Integer.parseInt(txtRemitente.getText().trim());

				TFactura f = new TFactura();
				f.set_idRemitente(rem);
				Controlador.getInstancia().accion(new Context(Evento.ABRIR_FACTURA, f));

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
	
	private void cerrarTodasLasVentanasDeEsteipo() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VAbrirFactura && window.isVisible()) {
				window.dispose();
			}
		}
	}

	
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}
		
		Object datos=context.getDatos();
		
		switch (context.getEvento()) {

		case VABRIR_FACTURA:
			txtRemitente.setText("");
			setVisible(true);
			break;

		case RES_ABRIR_FACTURA_OK:
			cerrarTodasLasVentanasDeEsteipo();
			JOptionPane.showMessageDialog(null, "Factura abierta");
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_CERRAR, datos));
			break;

		case RES_ABRIR_FACTURA_KO:
			cerrarTodasLasVentanasDeEsteipo();
			JOptionPane.showMessageDialog(null, "Error al abrir factura.");
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}