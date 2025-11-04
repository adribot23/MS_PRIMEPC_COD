/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class VBajaEmpleado extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public VBajaEmpleado() {
		super("Baja Empleado");
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
       
        setLayout(new GridLayout(3, 2, 5, 5));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Empleado"));

        
        JLabel lblID = new JLabel("ID del Empleado:");
        JTextField txtID = new JTextField();

      
        JButton btnBaja = new JButton("Dar de Baja");
        btnBaja.setBackground(new Color(200, 255, 200));
        btnBaja.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número positivo.");
                    return;
                }

                Controlador.getInstancia().accion(new Context(Evento.BAJA_EMPLEADO, id));
                txtID.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
            }
        });

        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
            this.dispose();
        });

     
        add(lblID);
        add(txtID);
        add(btnBaja);
        add(btnVolver);

       
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 180);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VBAJA_EMPLEADO:
                this.setVisible(true);
                break;

            case RES_BAJA_EMPLEADO_OK:
                JOptionPane.showMessageDialog(this, "Empleado dado de baja con ID: " + datos);
                break;

            case RES_BAJA_EMPLEADO_KO:
                JOptionPane.showMessageDialog(this, "Error al dar de baja el empleado.");
                break;

            default:
                JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
                break;
        }
    }
}