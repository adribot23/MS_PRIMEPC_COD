/**
 * 
 */
package presentacion.Producto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Producto.TProducto;
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
public class VAltaProducto extends JPanel implements IGUI {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private Controlador ctrl;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void initGUI() {
		setLayout(new GridLayout(9, 1));
		setBorder(BorderFactory.createTitledBorder("Alta Producto"));

		JTextField txtAltaPrecio = new JTextField();
		JTextField txtAltaModelo = new JTextField();
		JTextField txtAltaUnidades = new JTextField();
		JTextField txtAltaMarca = new JTextField();

		add(new JLabel("Marca:"));
		add(txtAltaMarca);
		add(new JLabel("Modelo:"));
		add(txtAltaModelo);
		add(new JLabel("Precio:"));
		add(txtAltaPrecio);
		add(new JLabel("Unidades:"));
		add(txtAltaUnidades);

		JButton btnAlta = new JButton("Dar de alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {
				double precio = Double.parseDouble(txtAltaPrecio.getText());
				String modelo = txtAltaModelo.getText();
				int unidades = Integer.parseInt(txtAltaUnidades.getText());
				String marca = txtAltaMarca.getText();

				TProducto nuevo = new TProducto(precio, modelo, unidades, marca);
				ctrl.accion(new Context(Evento.ALTA_PRODUCTO, nuevo));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Datos erroneos.");
			}
		});
		add(btnAlta);
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}