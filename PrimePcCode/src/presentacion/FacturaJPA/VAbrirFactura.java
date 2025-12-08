/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.Color;
import java.awt.GridLayout;

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

public class VAbrirFactura extends JFrame implements IGUI {
	
	public VAbrirFactura() {
		super("Abrir Factura");
		initGUI();
	}
	
	private void initGUI() {

		setLayout(new GridLayout(4, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Abrir Factura"));

		JLabel lblRemitente = new JLabel("Id Remitente");
		JTextField txtRemitente = new JTextField();

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
	
	public void actualizar(Context context) {
		switch (context.getEvento()) {

		case VABRIR_FACTURA:
			setVisible(true);
			break;

		case RES_ABRIR_FACTURA_OK:
			JOptionPane.showMessageDialog(null, "Factura abierta: ");
			break;

		case RES_ABRIR_FACTURA_KO:
			JOptionPane.showMessageDialog(null, "Error al abrir factura.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
		}
	}
}