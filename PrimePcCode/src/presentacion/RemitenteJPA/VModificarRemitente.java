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

public class VModificarRemitente extends JFrame implements IGUI{

	 public VModificarRemitente() {
	        super("Modificar Remitente");
	        initGUI();
	    }

	 private void initGUI() {

	        setLayout(new GridLayout(7, 2, 10, 10));
	        getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Remitente"));

	        JLabel lblId = new JLabel("ID:");
	        JTextField txtId = new JTextField();

	        JLabel lblNombre = new JLabel("Nombre:");
	        JTextField txtNombre = new JTextField();

	        JLabel lblDireccion = new JLabel("Direccion:");
	        JTextField txtDireccion = new JTextField();

	        JLabel lblTelefono = new JLabel("Telefono:");
	        JTextField txtTelefono = new JTextField();

	        JRadioButton rdbEmpresa = new JRadioButton("Empresa");
	        JRadioButton rdbParticular = new JRadioButton("Particular");
	        ButtonGroup grupo = new ButtonGroup();
	        grupo.add(rdbEmpresa);
	        grupo.add(rdbParticular);
	        rdbEmpresa.setSelected(true);

	        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
	        tipoPanel.add(rdbEmpresa);
	        tipoPanel.add(rdbParticular);

	        JLabel lblExtra = new JLabel("Número Registro Fiscal:");
	        JTextField txtExtra = new JTextField();

	        rdbEmpresa.addActionListener(e -> lblExtra.setText("Número Registro Fiscal:"));
	        rdbParticular.addActionListener(e -> lblExtra.setText("Fecha nacimiento:"));

	        JButton btnModificar = new JButton("Modificar");
	        btnModificar.setBackground(new Color(200, 255, 200));

	        btnModificar.addActionListener(e -> {
	            try {
	                int id = Integer.parseInt(txtId.getText().trim());
	                String nombre = txtNombre.getText().trim();
	                String direccion = txtDireccion.getText().trim();
	                String telefono = txtTelefono.getText().trim();
	                String extra = txtExtra.getText().trim();

	                if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || extra.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
	                    return;
	                }

	                TRemitente remitente;

	                if (rdbEmpresa.isSelected()) {
	                    int numFiscal = Integer.parseInt(extra);
	                    remitente = new TEmpresa(id, 1, nombre, direccion, telefono, numFiscal);
	                }
	                else {
	                    remitente = new TParticular(id, 1, nombre, direccion, telefono, extra);
	                }

	                Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_REMITENTE, remitente));

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

	        add(lblId); add(txtId);
	        add(lblNombre); add(txtNombre);
	        add(lblDireccion); add(txtDireccion);
	        add(lblTelefono); add(txtTelefono);
	        add(new JLabel("Tipo:")); add(tipoPanel);
	        add(lblExtra); add(txtExtra);
	        add(btnModificar); add(btnVolver);

	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setSize(420, 330);
	        setLocationRelativeTo(null);
	    }

	    @Override
	    public void actualizar(Context context) {

	        switch (context.getEvento()) {

	        case VMODIFICAR_REMITENTE:
	            setVisible(true);
	            break;

	        case RES_MODIFICAR_REMITENTE_OK:
	            JOptionPane.showMessageDialog(null, "Remitente modificado correctamente.");
	            break;

	        case RES_MODIFICAR_REMITENTE_KO:
	            JOptionPane.showMessageDialog(null, "Error al modificar remitente.");
	            break;
	        }
	    }
}
