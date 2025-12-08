/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VModificarFactura extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	public VModificarFactura() {
		super("Modificar Factura");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar factura"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel textFieldPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		mainPanel.add(textFieldPanel, BorderLayout.PAGE_START);
		
		JLabel facturaLabel = new JLabel("ID Factura: ");
		facturaLabel.setPreferredSize(new Dimension(60, 30)); // Aumenta el ancho
		JTextField facturaTextField = new JTextField();
		facturaTextField.setVisible(true);
		facturaTextField.setPreferredSize(new Dimension(60, 30));
		textFieldPanel.add(facturaLabel);
		textFieldPanel.add(facturaTextField);
		
		JLabel remitenteLabel = new JLabel("ID Remitente: ");
		remitenteLabel.setPreferredSize(new Dimension(60, 30)); // Aumenta el ancho
		JTextField remitenteTextField = new JTextField();
		remitenteTextField.setVisible(true);
		remitenteTextField.setPreferredSize(new Dimension(60, 30));
		textFieldPanel.add(remitenteLabel);
		textFieldPanel.add(remitenteTextField);
		
		JButton aceptar = new JButton("Guardar cambios");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> {
			try {
				int id_factura = Integer.parseInt(facturaTextField.getText());

				if (id_factura <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id alquiler debe ser mayor que 0");
					return;
				}

				int id_remitente = Integer.parseInt(remitenteTextField.getText());

				if (id_remitente <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id cliente debe ser mayor que 0");
					return;
				}

				TFactura tFactura = new TFactura();
				tFactura.set_idFactura(id_factura);
				tFactura.set_idRemitente(id_remitente);
				
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_FACTURA, tFactura));
				
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios y el id debe ser numerico");
			}});;

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
		this.dispose();
		});

		JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		botonesPanel.add(aceptar);
		botonesPanel.add(volver);
		botonesPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
		botonesPanel.setOpaque(false);
		mainPanel.add(botonesPanel, BorderLayout.PAGE_END);
		

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.VMODIFICAR_FACTURA)
			this.setVisible(true);
		else if (context.getEvento() == Evento.RES_MODIFICAR_FACTURA_OK) {
			JOptionPane.showMessageDialog(null, "Exito al modificar la factura. Id: " + (int) context.getDatos());
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		}
		else if (context.getEvento() == Evento.RES_MODIFICAR_FACTURA_KO) {
			JOptionPane.showMessageDialog(null, "Error al modificar la factura.");
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		}
	}
}