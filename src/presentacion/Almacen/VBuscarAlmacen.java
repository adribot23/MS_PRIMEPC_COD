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
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VBuscarAlmacen extends JFrame implements IGUI {
	public VBuscarAlmacen() {
		super("Buscar Almacén");
		initGUI();
	}

	public void initGUI() {
		// Configuración general
		setLayout(new GridLayout(2, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Almacén"));

		JTextField idField = new JTextField();
		JButton buscarButton = new JButton("Buscar");
		buscarButton.setBackground(new Color(200, 255, 200));
		add(new JLabel("ID almacen:"));
		add(idField);
		add(buscarButton);

		buscarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_ALMACEN, id));
				idField.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un numero.");
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
		setSize(350, 150);
		setLocationRelativeTo(null);

	}

	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_ALMACEN:
			this.setVisible(true);
			break;
		case RES_BUSCAR_ALMACEN_OK:
			JOptionPane.showMessageDialog(this, datos.toString());
			break;

		case RES_BUSCAR_ALMACEN_KO:
			JOptionPane.showMessageDialog(this, "Almacén no encontrado.");
			break;

		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}
}