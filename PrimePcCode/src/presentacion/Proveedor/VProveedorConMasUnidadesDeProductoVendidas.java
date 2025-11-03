/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.GUI.IGUI;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VProveedorConMasUnidadesDeProductoVendidas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idProducto;

	public VProveedorConMasUnidadesDeProductoVendidas() {
		super("Proveedor con más unidades vendidas");
		initGUI();
	}

	private void initGUI() {
		// Configuración de la ventana
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Proveedor con más unidades vendidas de un producto"));

		// Componentes
		JLabel lblId = new JLabel("ID Producto:");
		idProducto = new JTextField();
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setBackground(new Color(200, 255, 200));

		// Acción del botón Calcular
		btnCalcular.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idProducto.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR_CON_MAS_UDS, id));
				idProducto.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Introduce un número entero.");
			}
		});

		// Botón Volver
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});

		// Añadir componentes
		add(lblId);
		add(idProducto);
		add(btnCalcular);
		add(btnVolver);

		// Configuración final de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);

	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VPROVEEDOR_CON_MAS_UDS:
			this.setVisible(true);
			break;

		case RES_PROVEEDOR_CON_MAS_UDS_OK:
			JOptionPane.showMessageDialog(null, "Proveedor con más unidades vendidas:\n" + datos.toString());
			break;

		case RES_PROVEEDOR_CON_MAS_UDS_KO:
			JOptionPane.showMessageDialog(null, "Error al calcular el proveedor con más unidades vendidas.");
			break;

		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}