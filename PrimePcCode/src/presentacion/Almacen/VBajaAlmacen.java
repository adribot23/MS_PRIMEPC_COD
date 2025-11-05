/**
 * 
 */
package presentacion.Almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class VBajaAlmacen extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	
	public VBajaAlmacen() {
		super("Baja de Almacén");
		initGUI();
	}
	
	public void initGUI() {
		setLayout(new GridLayout(2, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Almacén"));

		JTextField idField = new JTextField();
		JButton bajaButton = new JButton("Dar de baja");
		bajaButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID almacen:"));
		add(idField);
		add(bajaButton);

		bajaButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BAJA_ALMACEN, id));
				idField.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		});
		add(btnVolver);
		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);
	
	}

	public void actualizar(Context context) {
		Evento evento = context.getEvento();

		switch (evento) {
		case VBAJA_ALMACEN:
			this.setVisible(true);
			break;
		case RES_BAJA_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, "Almacén dado de baja correctamente.");
			break;
		case RES_BAJA_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de baja el almacén.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}