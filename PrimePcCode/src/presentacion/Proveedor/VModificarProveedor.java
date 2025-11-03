/**
 * 
 */
package presentacion.Proveedor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.Proveedor.TProveedor;
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
public class VModificarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField modId;
	private JTextField modNombre;
	private JButton btnModificar, btnVolver;

	public VModificarProveedor() {
		initGUI();
	}

	private void initGUI() {
		setTitle("Modificar Proveedor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);

		// Panel principal
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("Modificar Proveedor"));

		modId = new JTextField();
		modNombre = new JTextField();

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modId.getText());
				String nombre = modNombre.getText().trim();

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
					return;
				}

				TProveedor p = new TProveedor(id, nombre);
				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_PROVEEDOR, p));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Debe ser un número entero.");
			}
		});

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			dispose();
		});

		panel.add(new JLabel("ID:"));
		panel.add(modId);
		panel.add(new JLabel("Nuevo nombre:"));
		panel.add(modNombre);
		panel.add(btnModificar);
		panel.add(btnVolver);

		add(panel);

	
		setSize(350, 250);
		setVisible(true);    
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {
			case VMODIFICAR_PROVEEDOR:
				this.setVisible(true);
				break;

			case RES_MODIFICAR_PROVEEDOR_OK:
				JOptionPane.showMessageDialog(this, 
						"Proveedor modificado correctamente:\n" + context.toString());
				break;

			case RES_MODIFICAR_PROVEEDOR_KO:
				JOptionPane.showMessageDialog(this, "Error al modificar el proveedor.");
				break;

			default:
				break;
		}
	}
}

