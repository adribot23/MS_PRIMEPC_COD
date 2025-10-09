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

public class VModificarEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarEmpleado() {
		this.ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(12, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar información"));

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

				ctrl.accion(Evento.MODIFICAR_EMPLEADO, empleado);
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
}
