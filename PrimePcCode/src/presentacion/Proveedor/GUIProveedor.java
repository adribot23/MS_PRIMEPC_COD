/**
 * 
 */
package presentacion.Proveedor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.Proveedor.VAltaProveedor;
import presentacion.Proveedor.VBajaProveedor;
import presentacion.Proveedor.VBuscarProveedor;
import presentacion.Proveedor.VModificarProveedor;
import presentacion.Proveedor.VMostrarProveedor;
import presentacion.Proveedor.VVerPorProducto;
import presentacion.Proveedor.VVincularProveedor;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class GUIProveedor extends JPanel implements IGUI {
	public void actualizar(Context context) {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("MODULO PROVEEDOR", SwingConstants.CENTER);
		titulo.setFont(new Font("Cambria", Font.BOLD, 24));
		add(titulo, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel_Izquierda
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VBuscarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VVerPorProducto());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VMostrarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));
		panelIzquierda.add(new VModificarProveedor());
		panelIzquierda.add(Box.createVerticalStrut(10));

		// Panel_Derecha
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VBajaProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VVincularProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));
		panelDerecha.add(new VAltaProveedor());
		panelDerecha.add(Box.createVerticalStrut(10));

		panelCentral.add(panelIzquierda);
		panelCentral.add(panelDerecha);
		add(panelCentral, BorderLayout.CENTER);

		// Boton_Volver

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(30, 200, 100));
		btnVolver.addActionListener(e -> {
			CardLayout cl = (CardLayout) this.getParent().getLayout();
			cl.show(this.getParent(), "Menu");
		});

		add(btnVolver, BorderLayout.SOUTH);
	}
}