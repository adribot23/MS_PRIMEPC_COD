/**
 * 
 */
package presentacion.FacturaJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.FacturaJPA.TLineaFactura;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VDevolucion extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	public VDevolucion() {
		super("Devolucion");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Devolucion"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel textFieldPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		mainPanel.add(textFieldPanel, BorderLayout.PAGE_START);
		
		JLabel facturaLabel = new JLabel("ID Factura: ");
		facturaLabel.setPreferredSize(new Dimension(60, 20)); // Aumenta el ancho
		JTextField facturaTextField = new JTextField();
		facturaTextField.setVisible(true);
		facturaTextField.setPreferredSize(new Dimension(60, 40));
		textFieldPanel.add(facturaLabel);
		textFieldPanel.add(facturaTextField);
		
		JLabel paqueteLabel = new JLabel("ID Paquete: ");
		paqueteLabel.setPreferredSize(new Dimension(60, 20)); // Aumenta el ancho
		JTextField paqueteTextField = new JTextField();
		paqueteTextField.setVisible(true);
		paqueteTextField.setPreferredSize(new Dimension(60, 40));
		textFieldPanel.add(paqueteLabel);
		textFieldPanel.add(paqueteTextField);
		
		JButton aceptar = new JButton("Procesar Devolucion");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> {
			try {
				int id_factura = Integer.parseInt(facturaTextField.getText());

				if (id_factura <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id factura debe ser mayor que 0");
					return;
				}

				int id_paquete = Integer.parseInt(paqueteTextField.getText());

				if (id_paquete <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id remitente debe ser mayor que 0");
					return;
				}

				TLineaFactura tLineaF = new TLineaFactura();
				tLineaF.set_idFactura(id_factura);
				tLineaF.set_idPaquete(id_paquete);
				
				Controlador.getInstancia().accion(new Context(Evento.DEVOLUCION, tLineaF));
				
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

		JPanel botonesPanel = new JPanel(new GridLayout(1, 1, 10, 10));
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
		if (context.getEvento() == Evento.VDEVOLUCION)
			this.setVisible(true);
		else if (context.getEvento() == Evento.RES_DEVOLUCION_OK) {
			JOptionPane.showMessageDialog(null, "Exito al procesar la devolucion.");
			Controlador.getInstancia().accion(new Context(Evento.DEVOLUCION, null));
			this.dispose();
		}
		else if (context.getEvento() == Evento.RES_DEVOLUCION_KO) {
			JOptionPane.showMessageDialog(null, "Error al procesar la devolucion.");
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
			this.dispose();
		}
	}
}