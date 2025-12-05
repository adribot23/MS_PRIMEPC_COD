package presentacion.RemitenteJPA;

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

import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.RemitenteJPA.TEmpresa;
import negocio.RemitenteJPA.TParticular;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaRemitente extends JFrame implements IGUI {

	public VAltaRemitente() {
		super("Alta de Remitente");
		initGUI();
	}

	private void initGUI() {

		setLayout(new GridLayout(6, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Remitente "));

		JLabel lblNombre = new JLabel("Nombre:");
		JTextField txtNombre = new JTextField();

		JLabel lblDireccion = new JLabel("Dirección:");
		JTextField txtDireccion = new JTextField();

		JLabel lblTelefono = new JLabel("Teléfono:");
		JTextField txtTelefono = new JTextField();

		JLabel lblVisitas = new JLabel("Número de Registro Fiscal:");
		JTextField altaNum = new JTextField();

		JRadioButton rdbEmpresa = new JRadioButton("Empresa");
		JRadioButton rdbParticular = new JRadioButton("Particular");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(rdbEmpresa);
		grupoTipo.add(rdbParticular);
		rdbEmpresa.setSelected(true);

		rdbEmpresa.addActionListener(e -> lblVisitas.setText("Número de Registro Fiscal:"));
		rdbParticular.addActionListener(e -> lblVisitas.setText("Fecha de nacimiento:"));

		JPanel altaTipoPanel = new JPanel(new GridLayout(1, 2));
		altaTipoPanel.add(rdbEmpresa);
		altaTipoPanel.add(rdbParticular);

		JButton btnAlta = new JButton("Dar de Alta");
		btnAlta.setBackground(new Color(200, 255, 200));
		btnAlta.addActionListener(e -> {
			try {

				String nombre = txtNombre.getText().trim();
				String direccion = txtDireccion.getText().trim();
				String telefono = txtTelefono.getText().trim();
				String extra = altaNum.getText().trim();

				if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || extra.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
					return;
				}

				TRemitente t;

				if (rdbEmpresa.isSelected()) {
					int registro = Integer.parseInt(extra);
					t = new TEmpresa(-1, 0, nombre, direccion, telefono, registro);
				} else {
					t = new TParticular(-1, 0, nombre, direccion, telefono, extra);
				}

				Controlador.getInstancia().accion(new Context(Evento.ALTA_REMITENTE, t));

				txtNombre.setText("");
				txtDireccion.setText("");
				txtTelefono.setText("");
				altaNum.setText("");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Datos inválidos.");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.REMITENTE, null));
			this.dispose();
		});

		add(lblNombre);
		add(txtNombre);
		add(lblDireccion);
		add(txtDireccion);
		add(lblTelefono);
		add(txtTelefono);

		add(new JLabel("Tipo de Remitente:"));
		add(altaTipoPanel);

		add(lblVisitas);
		add(altaNum);

		add(btnAlta);
		add(btnVolver);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvento()) {

		case VALTA_REMITENTE:
			setVisible(true);
			break;

		case RES_ALTA_REMITENTE_OK:
			JOptionPane.showMessageDialog(null, "Remitente dado de alta con ID: " + context.getDatos());
			break;

		case RES_ALTA_REMITENTE_KO:
			JOptionPane.showMessageDialog(null, "Error al dar de alta el Remitente.");
			break;
		}
	}

}
