/**
 * 
 */
package presentacion.Almacen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VBajaAlmacen extends JPanel implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Controlador ctrl;

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void initGUI() {
		setBorder(BorderFactory.createTitledBorder("Baja Almacen"));
		setLayout(new GridLayout(3, 1, 5, 5));

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
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID invalido.");
			}
		});
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}