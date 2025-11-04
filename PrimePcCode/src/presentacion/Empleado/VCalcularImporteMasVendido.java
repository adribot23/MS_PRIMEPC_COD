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
public class VCalcularImporteMasVendido extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public VCalcularImporteMasVendido() {
		super("Calcular Importe Total Del Empleado Que Mas Productos Ha Vendido");
		initGUI();
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	 private void initGUI() {
	       
	        setLayout(new GridLayout(2, 2, 10, 10));
	        getRootPane().setBorder(BorderFactory.createTitledBorder("Calcular Importe Más Vendido"));

	       
	        JLabel lblID = new JLabel("ID del Producto:");
	        JTextField txtID = new JTextField();

	        
	        JButton btnCalcular = new JButton("Calcular");
	        btnCalcular.setBackground(new Color(200, 255, 200));
	        btnCalcular.addActionListener(e -> {
	            try {
	                int id = Integer.parseInt(txtID.getText().trim());
	                if (id <= 0) {
	                    JOptionPane.showMessageDialog(this, "El ID debe ser un número positivo.");
	                    return;
	                }

	                Controlador.getInstancia().accion(new Context(Evento.CALCULAR_MAS_VENDIDO, id));
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
	       
	        add(btnCalcular);
	        add(btnVolver);

	       
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(350, 150);
	        setLocationRelativeTo(null);
	    }

	    @Override
	    public void actualizar(Context context) {
	        Evento evento = context.getEvento();
	        Object datos = context.getDatos();

	        switch (evento) {
	            case VCALCULAR_MAS_VENDIDO:
	                this.setVisible(true);
	                break;

	            case RES_CALCULAR_MAS_VENDIDO_OK:
	                JOptionPane.showMessageDialog(this, 
	                    "Empleado que más ha vendido: ID " + datos, 
	                    "Resultado", 
	                    JOptionPane.INFORMATION_MESSAGE);
	                break;

	            case RES_CALCULAR_MAS_VENDIDO_KO:
	                JOptionPane.showMessageDialog(this, 
	                    "Error al calcular el empleado que más ha vendido.", 
	                    "Error", 
	                    JOptionPane.ERROR_MESSAGE);
	                break;

	            default:
	                JOptionPane.showMessageDialog(this, 
	                    "Evento no reconocido: " + evento, 
	                    "Aviso", 
	                    JOptionPane.WARNING_MESSAGE);
	                break;
	        }
	    }
}