/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
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
public class VAltaCliente extends JPanel implements IGUI {
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
		setLayout(new GridLayout(10, 1));
		setBorder(BorderFactory.createTitledBorder("Alta Cliente"));

		JTextField altaNombre = new JTextField();
		JTextField altaDNI = new JTextField();
		JTextField altaNumSocio = new JTextField();
		JTextField altaPuntos = new JTextField();
		JTextField altaVisitas = new JTextField();

		JLabel lblNumSocio = new JLabel("Número de Socio:");
		JLabel lblPuntos = new JLabel("Puntos:");
		JLabel lblVisitas = new JLabel("Número de Visitas:");

		JRadioButton rdbSocio = new JRadioButton("Socio");
		JRadioButton rdbNoSocio = new JRadioButton("No Socio");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbSocio);
		grupoTipo.add(rdbNoSocio);
		rdbSocio.setSelected(true);

		JPanel panelCamposTipo = new JPanel(new GridLayout(3, 2));
		panelCamposTipo.add(lblNumSocio);
		panelCamposTipo.add(altaNumSocio);
		panelCamposTipo.add(lblPuntos);
		panelCamposTipo.add(altaPuntos);
		panelCamposTipo.add(lblVisitas);
		panelCamposTipo.add(altaVisitas);

		lblVisitas.setVisible(false);
		altaVisitas.setVisible(false);

		rdbSocio.addActionListener(e -> {
			lblNumSocio.setVisible(true);
			altaNumSocio.setVisible(true);
			lblPuntos.setVisible(true);
			altaPuntos.setVisible(true);
			lblVisitas.setVisible(false);
			altaVisitas.setVisible(false);
		});

		rdbNoSocio.addActionListener(e -> {
			lblNumSocio.setVisible(false);
			altaNumSocio.setVisible(false);
			lblPuntos.setVisible(false);
			altaPuntos.setVisible(false);
			lblVisitas.setVisible(true);
			altaVisitas.setVisible(true);
		});

		JPanel altaTipoPanel = new JPanel(new GridLayout(1, 2));
		altaTipoPanel.add(rdbSocio);
		altaTipoPanel.add(rdbNoSocio);

		JButton btnAlta = new JButton("Dar de alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {
				String nombre = altaNombre.getText();
				String dni = altaDNI.getText();
				TCliente cliente;

				if (rdbSocio.isSelected()) {
					int puntos = Integer.parseInt(altaPuntos.getText());
					cliente = new TClienteSocio(-1, nombre, dni, puntos);
				} else {
					int numVisitas = Integer.parseInt(altaVisitas.getText());
					cliente = new TClienteNoSocio(nombre, dni, numVisitas);
				}

				Controlador.getInstancia().accion(new Context(Evento.ALTA_CLIENTE, cliente));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los campos numéricos deben ser válidos.");
			}
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(new JLabel("DNI:"));
		add(altaDNI);
		add(altaTipoPanel);
		add(panelCamposTipo);
		add(btnAlta);
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}