/**
 * 
 */
package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class VVerProdPorAlmacen extends JPanel implements IGUI {
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
		setLayout(new GridLayout(1, 2));
		setBorder(BorderFactory.createTitledBorder("Productos por Almacén"));

		JTextField txtIdAlmacen = new JTextField();
		JButton btnPorAlmacen = new JButton("Mostrar por Almacén");
		btnPorAlmacen.setBackground(new Color(200, 255, 200));
		add(txtIdAlmacen);
		add(btnPorAlmacen);

		btnPorAlmacen.addActionListener(e -> {
			try {
				int idAlmacen = Integer.parseInt(txtIdAlmacen.getText());
				Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_PRODUCTOS_POR_ALMACEN, idAlmacen));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID erroneo.");
			}
		});
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}