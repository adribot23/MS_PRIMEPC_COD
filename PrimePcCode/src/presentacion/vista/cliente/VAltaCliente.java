package presentacion.vista.cliente;

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

import negocio.transfers.TCliente;
import negocio.transfers.TClienteNoSocio;
import negocio.transfers.TClienteSocio;
import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VAltaCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VAltaCliente() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(8, 1));
		setBorder(BorderFactory.createTitledBorder("Alta Cliente"));

		JTextField altaNombre = new JTextField();
		JTextField altaDNI = new JTextField();
		JTextField altaVisitas = new JTextField();
		JLabel lblVisitas = new JLabel("Numero de puntos:");

		JRadioButton rdbSocio = new JRadioButton("Socio");
		JRadioButton rdbNoSocio = new JRadioButton("No Socio");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbSocio);
		grupoTipo.add(rdbNoSocio);
		rdbSocio.setSelected(true);

		rdbSocio.addActionListener(e -> lblVisitas.setText("Numero de puntos:"));
		rdbNoSocio.addActionListener(e -> lblVisitas.setText("Numero de visitas:"));

		JPanel altaTipoPanel = new JPanel(new GridLayout(1, 2));
		altaTipoPanel.add(rdbSocio);
		altaTipoPanel.add(rdbNoSocio);

		JButton btnAlta = new JButton("Dar de alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {
				String nombre = altaNombre.getText();
				String dni = altaDNI.getText();
				int visitas = Integer.parseInt(altaVisitas.getText());

				TCliente cliente;
				if (rdbSocio.isSelected()) {
					cliente = new TClienteSocio(nombre, dni, visitas);
				} else {
					cliente = new TClienteNoSocio(nombre, dni, visitas);
				}

				ctrl.accion(Evento.ALTA_CLIENTE, cliente);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Visitas y numero de socio deben ser numeros validos.");
			}
		});

		add(new JLabel("Nombre:"));
		add(altaNombre);
		add(new JLabel("DNI:"));
		add(altaDNI);
		add(altaTipoPanel);
		add(lblVisitas);
		add(altaVisitas);
		add(btnAlta);
	}

}
