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
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Almacen.TAlmacen;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VModificarAlmacen extends JFrame implements IGUI {
	
	public VModificarAlmacen() {
		super("Modificar Almacén");
		initGUI();
	}
	
	public void initGUI() {
		setLayout(new GridLayout(4, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Almacen"));

		JTextField idField = new JTextField();
		JTextField nombreField = new JTextField();
		JTextField capacidadField = new JTextField();
		JButton modificarButton = new JButton("Modificar");

		modificarButton.setBackground(new Color(200, 255, 200));

		add(new JLabel("ID:"));
		add(idField);
		add(new JLabel("Nombre:"));
		add(nombreField);
		add(new JLabel("Capacidad máxima:"));
		add(capacidadField);
		add(modificarButton);

		modificarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText());
				String nombre = nombreField.getText();
				int capacidad = Integer.parseInt(capacidadField.getText());
				TAlmacen almacen = new TAlmacen(id, nombre, capacidad, -1);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_ALMACEN, almacen));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos inválidos.");
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.ALMACEN, null));
			this.dispose();
		});
		add(btnVolver);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actualizar(Context context) {
		switch (context.getEvento()) {
		case VMODIFICAR_ALMACEN:
			this.setVisible(true);
			break;

		case RES_MODIFICAR_ALMACEN_OK:
			JOptionPane.showMessageDialog(this, 
					"Proveedor modificado correctamente:\n" + context.toString());
			break;

		case RES_MODIFICAR_ALMACEN_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar el proveedor.");
			break;

		default:
			break;
		}
	}
}