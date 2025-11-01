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

import negocio.Almacen.TAlmacen;
import negocio.Producto.TProducto;
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
public class VVincularAlmacen extends JPanel implements IGUI{
	//TODO: TERMINAR ESTA CLASE
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
		setBorder(BorderFactory.createTitledBorder("Vincular Almacen"));
		setLayout(new GridLayout(10, 1, 5, 5));

		JTextField idField = new JTextField();
		JTextField idProdField = new JTextField();
		//JTextField capacidadField = new JTextField();
		JButton modificarButton = new JButton("Vincular");

		modificarButton.setBackground(new Color(200, 255, 200));

		add(new JLabel("ID:"));
		add(idField);
		add(new JLabel("ID Producto:"));
		add(idProdField);

		add(modificarButton);

		modificarButton.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idField.getText());
				int idProd = Integer.parseInt(idProdField.getText());
				TProducto tProducto = new TProducto(idProd,id);
				Controlador.getInstancia().accion(new Context(Evento.VINCULAR_ALMACEN, tProducto));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Campos numericos inválidos.");
			}
		});
	}

	@Override
	public void actualizar(Context context) {
		// TODO Apéndice de método generado automáticamente
		
	}
}