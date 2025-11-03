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

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VBajaProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField bajaId;

	public VBajaProveedor() {
		super("Baja de Proveedor");
		initGUI();
	}

	private void initGUI() {
		// Configuración de la ventana
		setLayout(new GridLayout(4, 1, 100, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Proveedor"));

		// Componentes
		JLabel lblId = new JLabel("ID Proveedor:");
		bajaId = new JTextField();
		JButton btnBaja = new JButton("Dar de Baja");
		btnBaja.setBackground(new Color(200, 255, 200));

		// Acción del botón
		btnBaja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(bajaId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_PROVEEDOR, id));
				bajaId.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Introduce un número entero.");
			}
		});

		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});
		
		// Añadir componentes
		add(lblId);
		add(bajaId);
		add(btnBaja);
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
		case VBAJA_PROVEEDOR:
			this.setVisible(true);
			break;
		case RES_BAJA_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(null, "Proveedor dado de baja correctamente.");
			break;
		case RES_BAJA_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el proveedor.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}

}