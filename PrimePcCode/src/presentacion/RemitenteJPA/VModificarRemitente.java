package presentacion.RemitenteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

	        setLayout(new GridLayout(5, 2, 10, 10));
	        getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Remitente"));

	        JLabel lblId = new JLabel("ID:");
	        JTextField txtId = new JTextField();

	        JLabel lblDireccion = new JLabel("Direccion:");
	        JTextField txtdireccion = new JTextField();

	        JLabel lblTelefono = new JLabel("Telefono:");
	        JTextField txttelefonos = new JTextField();

	        JButton btnModificar = new JButton("Modificar");
	        btnModificar.setBackground(new Color(200, 255, 200));

	        btnModificar.addActionListener(e -> {

	            try {
	                int id = Integer.parseInt(txtId.getText().trim());
	                String direccion = txtdireccion.getText().trim();
	                String telefonos = txttelefonos.getText().trim();

	                TRemitente t = new TRemitente();
	                t.setId(id);
	                t.setDireccion(direccion);
	                t.setTelefono(telefonos);

	                Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_REMITENTE, t));

	            }
	            catch (Exception ex) {
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
	        add(lblDireccion); add(txtdireccion);
	        add(lblTelefono); add(txttelefonos);
	        add(btnModificar); add(btnVolver);

	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setSize(400, 260);
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
