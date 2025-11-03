/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteSocio;
import negocio.Cliente.TClienteNoSocio;
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
public class VModificarCliente extends JPanel implements IGUI {
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
		setLayout(new GridLayout(12, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar información"));

		JTextField modificarID = new JTextField();
		JTextField modificarNombre = new JTextField();
		JTextField modificarDNI = new JTextField();
		JTextField modificarNumSocio = new JTextField();
		JTextField modificarPuntos = new JTextField();
		JTextField modificarVisitas = new JTextField();

		JLabel lblNumSocio = new JLabel("Número de Socio:");
		JLabel lblPuntos = new JLabel("Puntos:");
		JLabel lblVisitas = new JLabel("Número de Visitas:");

		JRadioButton rdbSocio = new JRadioButton("Socio");
		JRadioButton rdbNoSocio = new JRadioButton("No Socio");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbSocio);
		grupoTipo.add(rdbNoSocio);
		rdbSocio.setSelected(true);

		JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
		tipoPanel.add(rdbSocio);
		tipoPanel.add(rdbNoSocio);

		lblVisitas.setVisible(false);
		modificarVisitas.setVisible(false);

		rdbSocio.addActionListener(e -> {
			lblNumSocio.setVisible(true);
			modificarNumSocio.setVisible(true);
			lblPuntos.setVisible(true);
			modificarPuntos.setVisible(true);
			lblVisitas.setVisible(false);
			modificarVisitas.setVisible(false);
		});

		rdbNoSocio.addActionListener(e -> {
			lblNumSocio.setVisible(false);
			modificarNumSocio.setVisible(false);
			lblPuntos.setVisible(false);
			modificarPuntos.setVisible(false);
			lblVisitas.setVisible(true);
			modificarVisitas.setVisible(true);
		});

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modificarID.getText());
				String nombre = modificarNombre.getText();
				String dni = modificarDNI.getText();

				TCliente cliente;
				if (rdbSocio.isSelected()) {
					int puntos = Integer.parseInt(modificarPuntos.getText());
					cliente = new TClienteSocio(id, nombre, dni, puntos);
				} else {
					int visitas = Integer.parseInt(modificarVisitas.getText());
					cliente = new TClienteNoSocio(nombre, dni, visitas);
				}

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_CLIENTE, cliente));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y los campos numéricos deben ser números.");
			}
		});

		add(new JLabel("ID cliente:"));
		add(modificarID);
		add(new JLabel("Nombre:"));
		add(modificarNombre);
		add(new JLabel("DNI:"));
		add(modificarDNI);
		add(tipoPanel);
		add(lblNumSocio);
		add(modificarNumSocio);
		add(lblPuntos);
		add(modificarPuntos);
		add(lblVisitas);
		add(modificarVisitas);
		add(btnModificar);
	}

	@Override
	public void actualizar(Context context) {
		// TODO Auto-generated method stub
		
	}
}