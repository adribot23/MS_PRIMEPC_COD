/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VModificarEmpleado extends JFrame implements IGUI {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public VModificarEmpleado() {
		super("Modificar Empleado");
		initGUI();
	}
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void initGUI() {
		setLayout(new GridLayout(12, 1));
		//setBorder(BorderFactory.createTitledBorder("Modificar información"));

		JTextField modificarID = new JTextField();
		JTextField modificarNombre = new JTextField();
		JTextField modificarDNI = new JTextField();
		JTextField modificarTlf = new JTextField();
		JTextField modificarHoras = new JTextField();
		JLabel lblHoras = new JLabel("Horas Extra:");

		JRadioButton rdbCompleto = new JRadioButton("Completo");
		JRadioButton rdbParcial = new JRadioButton("Parcial");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbCompleto);
		grupoTipo.add(rdbParcial);
		rdbCompleto.setSelected(true);

		rdbCompleto.addActionListener(e -> lblHoras.setText("Horas Extra:"));
		rdbParcial.addActionListener(e -> lblHoras.setText("Horas Semanales:"));

		JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
		tipoPanel.add(rdbCompleto);
		tipoPanel.add(rdbParcial);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modificarID.getText());
				String nombre = modificarNombre.getText();
				String dni = modificarDNI.getText();
				String tlf = modificarTlf.getText();
				int horas = Integer.parseInt(modificarHoras.getText());

				TEmpleado empleado;
				if (rdbCompleto.isSelected()) {
					empleado = new TEmpleadoCompleto(id, nombre, dni, tlf, horas);
				} else {
					empleado = new TEmpleadoParcial(id, nombre, dni, tlf, horas);
				}

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_EMPLEADO, empleado));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID, teléfono y horas deben ser números.");
			}
		});

		add(new JLabel("ID empleado:"));
		add(modificarID);
		add(new JLabel("Nombre:"));
		add(modificarNombre);
		add(new JLabel("DNI:"));
		add(modificarDNI);
		add(new JLabel("Tlf:"));
		add(modificarTlf);
		add(tipoPanel);
		add(lblHoras);
		add(modificarHoras);
		add(btnModificar);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {

		case VMODIFICAR_EMPLEADO:
			this.setVisible(true);
			break;
		case RES_MODIFICAR_EMPLEADO_OK:
			JOptionPane.showMessageDialog(null, "Empleado modificado con ID: " + datos);
			break;
		case RES_MODIFICAR_EMPLEADO_KO:
			JOptionPane.showMessageDialog(null, "Error al modificar el empleado.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		
	}
	}
}