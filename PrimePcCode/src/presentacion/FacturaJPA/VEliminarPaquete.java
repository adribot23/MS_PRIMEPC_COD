/**
 * 
 */
package presentacion.FacturaJPA;

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
import javax.swing.JTextField;

import negocio.FacturaJPA.TCarritoFactura;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VEliminarPaquete extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private final JTextField idPaqueteField = new JTextField();
	private TCarritoFactura carrito;

	public VEliminarPaquete() {
		super("Eliminar paquete de factura");
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(3, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Eliminar paquete de factura"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volver();
			}
		});

		add(new JLabel("Id paquete:"));
		add(idPaqueteField);

		JButton aceptar = new JButton("Eliminar paquete");
		aceptar.setBackground(new Color(200, 255, 200));
		aceptar.addActionListener(e -> onAceptar());
		add(aceptar);

		JButton volver = new JButton("Volver");
		volver.setBackground(new Color(255, 220, 220));
		volver.addActionListener(e -> volver());
		add(volver);

		setSize(400, 200);
		setLocationRelativeTo(null);
	}

	private void onAceptar() {
		try {
			int idPaquete = Integer.parseInt(idPaqueteField.getText().trim());


			if (idPaquete <= 0) {
				JOptionPane.showMessageDialog(this, "El Id paquete debe ser un numero positivo.",
						"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (carrito != null) {
				carrito.set_idPaquete(idPaquete);
				Controlador.getInstancia().accion(new Context(Evento.QUITAR_PAQUETE_FACTURA, carrito));
				limpiarCampos();
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El Id paquete debe ser un numero enteros positivo.",
					"Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		if (carrito != null) {
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_CERRAR, carrito));
		} else {
			Controlador.getInstancia().accion(new Context(Evento.FACTURA, null));
		}
		dispose();
	}

	private void limpiarCampos() {
		idPaqueteField.setText("");
	}

	private void cerrarTodasLasVentanasDeEsteipo() {
		for (Window window : Window.getWindows()) {
			if (window instanceof VEliminarPaquete && window.isVisible()) {
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
		case VQUITAR_PAQUETE_FACTURA:
			limpiarCampos();
			setVisible(true);
			break;
		case RES_PASAR_CARRITOFACTURA_A_ELIMINAR_OK:
			if (datos instanceof TCarritoFactura) {
				this.carrito = (TCarritoFactura) datos;
				setVisible(true);
			}
			break;
		case RES_PASAR_CARRITOFACTURA_A_ELIMINAR_KO:
			JOptionPane.showMessageDialog(this, "Error en el traspaso del carrito.");
			dispose();
			break;
		case RES_QUITAR_PAQUETE_FACTURA_OK:
			cerrarTodasLasVentanasDeEsteipo();
			if (datos instanceof TCarritoFactura) {
				Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_CERRAR, datos));
			} else {
				Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_CERRAR, datos));
			}
			break;
		case RES_QUITAR_PAQUETE_FACTURA_KO:
			cerrarTodasLasVentanasDeEsteipo();
			JOptionPane.showMessageDialog(null, "No se pudo eliminar el paquete del carrito. Compruebe los datos.");
			Controlador.getInstancia().accion(new Context(Evento.PASAR_CARRITOFACTURA_A_CERRAR, datos));
			break;
		default:
			break;
		}
	}
}