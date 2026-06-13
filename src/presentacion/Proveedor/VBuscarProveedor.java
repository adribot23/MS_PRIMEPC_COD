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
public class VBuscarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField buscarId;

	public VBuscarProveedor() {
		super("Buscar Proveedor");
		initGUI();
	}

	private void initGUI() {
		// Configuración general
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Proveedor"));

		JLabel lblId = new JLabel("ID del proveedor:");
		buscarId = new JTextField();

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));
		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(buscarId.getText().trim());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_PROVEEDOR, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});

		add(lblId);
		add(buscarId);
		add(btnBuscar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);

	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_PROVEEDOR:
			this.setVisible(true);
			break;
		case RES_BUSCAR_PROVEEDOR_OK:
			JOptionPane.showMessageDialog(this, datos.toString());
			break;

		case RES_BUSCAR_PROVEEDOR_KO:
			JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
			break;

		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
			break;
		}
	}
}
