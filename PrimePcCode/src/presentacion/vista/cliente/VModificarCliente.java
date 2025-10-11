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
import presentacion.controlador.Context;
import presentacion.controlador.Controlador;
import presentacion.factoria.Evento;

public class VModificarCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controlador ctrl;

	public VModificarCliente() {
		ctrl = Controlador.obtenerInstancia();
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(10, 1));
		setBorder(BorderFactory.createTitledBorder("Modificar cliente"));

		JTextField modificarID = new JTextField();
		JTextField modificarNombre = new JTextField();
		JTextField modificarDNI = new JTextField();
		JTextField modificarVisitas = new JTextField();
		JLabel lblVisitas = new JLabel("Numero de puntos:");

		JRadioButton rdbSocio = new JRadioButton("Socio");
		JRadioButton rdbNoSocio = new JRadioButton("No Socio");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbSocio);
		grupoTipo.add(rdbNoSocio);
		rdbSocio.setSelected(true);

		rdbSocio.addActionListener(e -> lblVisitas.setText("Numero de puntos:"));
		rdbNoSocio.addActionListener(e -> lblVisitas.setText("Numero de visitas:"));

		JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
		tipoPanel.add(rdbSocio);
		tipoPanel.add(rdbNoSocio);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modificarID.getText());
				String nombre = modificarNombre.getText();
				String dni = modificarDNI.getText();
				int visitas = Integer.parseInt(modificarVisitas.getText());

				TCliente cliente;
				if (rdbSocio.isSelected()) {
					cliente = new TClienteSocio(id, nombre, dni, visitas);
				} else {
					cliente = new TClienteNoSocio(id, nombre, dni, visitas);
				}

				ctrl.accion(new Context(Evento.MODIFICAR_CLIENTE, cliente));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y visitas/puntos deben ser numeros válidos.");
			}

		});

		add(new JLabel("ID cliente:"));
		add(modificarID);
		add(new JLabel("Nombre:"));
		add(modificarNombre);
		add(new JLabel("DNI:"));
		add(modificarDNI);
		add(tipoPanel);
		add(lblVisitas);
		add(modificarVisitas);
		add(btnModificar);
	}

}
