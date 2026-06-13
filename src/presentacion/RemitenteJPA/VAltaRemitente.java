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


import negocio.RemitenteJPA.TEmpresa;
import negocio.RemitenteJPA.TParticular;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaRemitente extends JFrame implements IGUI {

	private static final long serialVersionUID = -1846207169757416219L;

	public VAltaRemitente() {
		super("Alta de Remitente");
		initGUI();
	}

	private void initGUI() {

	    setLayout(new GridLayout(6, 2, 10, 10));
	    getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Remitente"));

	    JLabel lblNombre = new JLabel("Nombre:");
	    JTextField txtNombre = new JTextField();

	    JLabel lblDireccion = new JLabel("Dirección:");
	    JTextField txtDireccion = new JTextField();

	    JLabel lblTelefono = new JLabel("Teléfono:");
	    JTextField txtTelefono = new JTextField();

	    JLabel lblExtra = new JLabel("Número de Registro Fiscal:");
	    JTextField txtExtra = new JTextField();


	    JRadioButton rdbEmpresa = new JRadioButton("Empresa");
	    JRadioButton rdbParticular = new JRadioButton("Particular");
	    ButtonGroup grupoTipo = new ButtonGroup();
	    grupoTipo.add(rdbEmpresa);
	    grupoTipo.add(rdbParticular);
	    rdbEmpresa.setSelected(true);

	    rdbEmpresa.addActionListener(e -> lblExtra.setText("Número de Registro Fiscal:"));
	    rdbParticular.addActionListener(e -> lblExtra.setText("Fecha de Nacimiento (YYYY-MM-DD):"));

	    JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
	    tipoPanel.add(rdbEmpresa);
	    tipoPanel.add(rdbParticular);
	   
	    JButton btnAlta = new JButton("Dar de Alta");
	    btnAlta.setBackground(new Color(200, 255, 200));
	    btnAlta.addActionListener(e -> {
	        try {
	            String nombre = txtNombre.getText().trim();
	            String direccion = txtDireccion.getText().trim();
	            String telefono = txtTelefono.getText().trim();
	            String extra = txtExtra.getText().trim();

	            if (nombre.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "El campo 'Nombre' está vacío.");
	                return;
	            }
	            if (direccion.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "El campo 'Dirección' está vacío.");
	                return;
	            }
	            if (telefono.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "El campo 'Teléfono' está vacío.");
	                return;
	            }
	            if (extra.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "El campo extra está vacío.");
	                return;
	            }

	            TRemitente t;

	            if (rdbEmpresa.isSelected()) {

	                int registro;
	                try {
	                    registro = Integer.parseInt(extra);
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(this, "Número de Registro Fiscal inválido. Debe ser un número entero.");
	                    return;
	                }

	                TEmpresa te = new TEmpresa();
	                te.setId(-1);
	                te.setActivo(1);
	                te.setNombre(nombre);
	                te.setDireccion(direccion);
	                te.setTelefono(telefono);
	                te.setNumRegistroFiscal(registro);
	                t = te;

	            } else {

	                String fecha;
	                try {
	                    fecha = extra;
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(this, "Fecha inválida");
	                    return;
	                }

	                TParticular tp = new TParticular();
	                tp.setId(-1);
	                tp.setActivo(1);
	                tp.setNombre(nombre);
	                tp.setDireccion(direccion);
	                tp.setTelefono(telefono);
	                tp.setFechaNacimiento(fecha);
	                t = tp;
	            }

	            Controlador.getInstancia().accion(new Context(Evento.ALTA_REMITENTE, t));

	            txtNombre.setText("");
	            txtDireccion.setText("");
	            txtTelefono.setText("");
	            txtExtra.setText("");

	        } catch (Exception ex) {
	            ex.printStackTrace();
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
	    add(tipoPanel);

	    add(lblExtra);
	    add(txtExtra);

	    add(btnAlta);
	    add(btnVolver);

	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize(500, 350);
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
		default:
			break;
		}
	}

}
