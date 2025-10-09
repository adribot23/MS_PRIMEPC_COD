package presentacion.vista.empleado;

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

import negocio.transfers.TEmpleado;
import negocio.transfers.TEmpleadoCompleto;
import negocio.transfers.TEmpleadoParcial;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VAltaEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VAltaEmpleado() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
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

				ctrl.accion(Evento.ALTA_EMPLEADO, empleado);
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

}
