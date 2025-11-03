/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Proveedor.TProveedor;
import presentacion.Controller.Controlador;
import presentacion.GUI.IGUI;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VAltaProveedor extends JFrame implements IGUI {

	private Controlador ctrl;

	public VAltaProveedor() {
		super("Alta de Proveedor");
		initGUI();
	}

	public void initGUI() {
		// Configuración de la ventana
		setLayout(new GridLayout(4, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Proveedor"));

		// Componentes
		JLabel lblNombre = new JLabel("Nombre:");
		JTextField altaNombre = new JTextField();
		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));

		// Acción del botón
		btnAlta.addActionListener(e -> {
			String nombre = altaNombre.getText().trim();
			if (!nombre.isEmpty()) {
				TProveedor p = new TProveedor(0, nombre);
				Controlador.getInstancia().accion(new Context(Evento.ALTA_PROVEEDOR, p));
				altaNombre.setText("");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});
		// Añadir componentes
		add(lblNombre);
		add(altaNombre);
		add(btnAlta);
		add(btnVolver);
		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {

		case VALTA_PROVEEDOR:
			this.setVisible(true);
			break;
		case RES_ALTA_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Proveedor dado de alta con ID: " + datos);
			break;
		case RES_ALTA_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el proveedor.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
			
		}

	}
}
