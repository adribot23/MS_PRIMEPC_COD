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

import negocio.Almacen.TAlmacen;
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
public class VAltaAlmacen extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VAltaAlmacen() {
		super("Alta de Almacen");
		initGUI();
	}
	
	public void initGUI() {
		
		setLayout(new GridLayout(3, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Almacen"));

		JTextField nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton altaButton = new JButton("Dar de alta");
		altaButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(altaButton);
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		});
		add(btnVolver);

		altaButton.addActionListener(e -> {
			try {
				String nombre = nombreField.getText().trim();
				int capacidad = Integer.parseInt(capacidadField.getText().trim());
				TAlmacen almacen = new TAlmacen(nombre, capacidad, 0);
				Controlador.getInstancia().accion(new Context(Evento.ALTA_ALMACEN, almacen));
				nombreField.setText("");
				capacidadField.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Capacidad y ocupacion deben ser numeros.");
			}
		});
		
		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
		
	}

	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {

		case VALTA_ALMACEN:
			this.setVisible(true);
			break;
		case RES_ALTA_ALMACEN_OK:
			JOptionPane.showMessageDialog(null, "Almacén dado de alta con ID: " + datos);
			break;
		case RES_ALTA_ALMACEN_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el almacén.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
			
		}
	}
}