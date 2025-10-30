/**
 * 
 */
package presentacion.Empleado;

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

import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
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
public class VAltaEmpleado extends JPanel implements IGUI {
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
		setLayout(new GridLayout(10, 1));
		setBorder(BorderFactory.createTitledBorder("Alta Empleado"));

		JTextField altaNombre = new JTextField();
		JTextField altaDNI = new JTextField();
		JTextField altaTlf = new JTextField();
		JTextField altaHoras = new JTextField();
		JLabel lblHoras = new JLabel("Horas Extra:");

		JRadioButton rdbCompleto = new JRadioButton("Completo");
		JRadioButton rdbParcial = new JRadioButton("Parcial");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbCompleto);
		grupoTipo.add(rdbParcial);
		rdbCompleto.setSelected(true);

		rdbCompleto.addActionListener(e -> lblHoras.setText("Horas Extra:"));
		rdbParcial.addActionListener(e -> lblHoras.setText("Horas Semanales:"));

		JPanel altaTipoPanel = new JPanel(new GridLayout(1, 2));
		altaTipoPanel.add(rdbCompleto);
		altaTipoPanel.add(rdbParcial);

		JButton btnAlta = new JButton("Dar de alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {
				String nombre = altaNombre.getText();
				String dni = altaDNI.getText();
				String tlf = altaTlf.getText();
				int horas = Integer.parseInt(altaHoras.getText());

				TEmpleado empleado;
				if (rdbCompleto.isSelected()) {
					empleado = new TEmpleadoCompleto(-1, nombre, dni, tlf, horas);
				} else {
					empleado = new TEmpleadoParcial(-1, nombre, dni, tlf, horas);
				}

				ctrl.accion(new Context(Evento.ALTA_EMPLEADO, empleado));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Telefono y horas deben ser numeros.");
			}
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(new JLabel("DNI:"));
		add(altaDNI);
		add(new JLabel("Tlf:"));
		add(altaTlf);
		add(altaTipoPanel);
		add(lblHoras);
		add(altaHoras);
		add(btnAlta);
	}

	public void actualizar(Context context) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}