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
public class VModificarProducto extends JPanel implements IGUI {
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
		setLayout(new GridLayout(11, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar Producto"));

		JTextField txtModId = new JTextField();
		JTextField txtModPrecio = new JTextField();
		JTextField txtModModelo = new JTextField();
		JTextField txtModUnidades = new JTextField();
		JTextField txtModMarca = new JTextField();

		add(new JLabel("ID:"));
		add(txtModId);
		add(new JLabel("Marca:"));
		add(txtModMarca);
		add(new JLabel("Modelo:"));
		add(txtModModelo);
		add(new JLabel("Precio:"));
		add(txtModPrecio);
		add(new JLabel("Unidades:"));
		add(txtModUnidades);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtModId.getText());
				double precio = Double.parseDouble(txtModPrecio.getText());
				String modelo = txtModModelo.getText();
				int unidades = Integer.parseInt(txtModUnidades.getText());
				String marca = txtModMarca.getText();

				TProducto modificado = new TProducto(id, precio, modelo, unidades, marca);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_PRODUCTO, modificado));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Datos erroneos.");
			}
		});
		add(btnModificar);
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}