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
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class VModificarEmpleado extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public VModificarEmpleado() {
		super("Modificar Empleado");
		initGUI();
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	   private void initGUI() {
	      
	        setLayout(new GridLayout(8, 2, 5, 5));
	        getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Empleado"));

	      
	        JLabel lblID = new JLabel("ID Empleado:");
	        JTextField txtID = new JTextField();

	        JLabel lblNombre = new JLabel("Nombre:");
	        JTextField txtNombre = new JTextField();

	        JLabel lblDNI = new JLabel("DNI:");
	        JTextField txtDNI = new JTextField();

	        JLabel lblTlf = new JLabel("Teléfono:");
	        JTextField txtTlf = new JTextField();

	        JLabel lblHoras = new JLabel("Horas Extra:");
	        JTextField txtHoras = new JTextField();

	     
	        JRadioButton rdbCompleto = new JRadioButton("Completo");
	        JRadioButton rdbParcial = new JRadioButton("Parcial");
	        ButtonGroup grupoTipo = new ButtonGroup();
	        grupoTipo.add(rdbCompleto);
	        grupoTipo.add(rdbParcial);
	        rdbCompleto.setSelected(true);

	        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
	        tipoPanel.add(rdbCompleto);
	        tipoPanel.add(rdbParcial);

	        
	        rdbCompleto.addActionListener(e -> lblHoras.setText("Horas Extra:"));
	        rdbParcial.addActionListener(e -> lblHoras.setText("Horas Semanales:"));

	       
	        JButton btnModificar = new JButton("Modificar");
	        btnModificar.setBackground(new Color(200, 255, 200));
	        btnModificar.addActionListener(e -> {
	            try {
	                int id = Integer.parseInt(txtID.getText().trim());
	                String nombre = txtNombre.getText().trim();
	                String dni = txtDNI.getText().trim();
	                String tlf = txtTlf.getText().trim();
	                int horas = Integer.parseInt(txtHoras.getText().trim());

	                if (nombre.isEmpty() || dni.isEmpty() || tlf.isEmpty()) {
	                    JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenados.");
	                    return;
	                }

	                TEmpleado empleado;
	                if (rdbCompleto.isSelected()) {
	                    empleado = new TEmpleadoCompleto(id, nombre, dni, tlf, horas);
	                } else {
	                    empleado = new TEmpleadoParcial(id, nombre, dni, tlf, horas);
	                }

	                Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_EMPLEADO, empleado));

	                // Limpiar campos tras modificar
	                txtID.setText("");
	                txtNombre.setText("");
	                txtDNI.setText("");
	                txtTlf.setText("");
	                txtHoras.setText("");
	                rdbCompleto.setSelected(true);
	                lblHoras.setText("Horas Extra:");
	            } 
	            catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "ID y Horas deben ser números válidos.");
	            }
	        });

	     
	        JButton btnVolver = new JButton("Volver");
	        btnVolver.setBackground(new Color(255, 220, 220));
	        btnVolver.addActionListener(e -> {
	            Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
	            this.dispose();
	        });

	        
	        add(lblID); add(txtID);
	        add(lblNombre); add(txtNombre);
	        add(lblDNI); add(txtDNI);
	        add(lblTlf); add(txtTlf);
	        add(new JLabel("Tipo de Empleado:")); add(tipoPanel);
	        add(lblHoras); add(txtHoras);
	        add(btnModificar); add(btnVolver);

	        
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(500, 300);
	        setLocationRelativeTo(null);
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
	                JOptionPane.showMessageDialog(this, "Empleado modificado con ID: " + datos);
	                break;

	            case RES_MODIFICAR_EMPLEADO_KO:
	                JOptionPane.showMessageDialog(this, "Error al modificar el empleado.");
	                break;

	            default:
	                JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
	                break;
	        }
	    }
}