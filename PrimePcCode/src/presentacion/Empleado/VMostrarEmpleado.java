/**
 * 
 */
package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import negocio.Empleado.TEmpleado;
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
public class VMostrarEmpleado extends JFrame implements IGUI {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private JTextArea textArea;
	
	public VMostrarEmpleado() {
		super("Mostrar Empleado");
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
        
        setLayout(new GridLayout(2, 1, 10, 5));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Mostrar Empleados"));

      
       
        JButton btnMostrarTodos = new JButton("Mostrar todos los empleados");
        btnMostrarTodos.setBackground(new Color(200, 255, 200));
        btnMostrarTodos.addActionListener(e ->
            Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_EMPLEADOS, null))
        );

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
            this.dispose();
        });

      
      
        add(btnMostrarTodos);
        add(btnVolver);

      
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VMOSTRAR_TODOS_EMPLEADOS:
                this.setVisible(true);
                break;

            case RES_MOSTRAR_TODOS_EMPLEADOS_OK:
                if (datos instanceof List<?>) {
                    List<?> lista = (List<?>) datos;

                    if (lista.isEmpty()) {
                        textArea.setText("No hay empleados registrados.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (Object obj : lista) {
                            if (obj instanceof TEmpleado) {
                                TEmpleado emp = (TEmpleado) obj;
                                sb.append("ID: ").append(emp.getId())
                                  .append(" | Nombre: ").append(emp.getNombre())
                                  .append(" | DNI: ").append(emp.getDni())
                                  .append(" | Teléfono: ").append(emp.getTelefono())
                                  .append("\n");
                            }
                        }
                        textArea.setText(sb.toString());
                    }
                } else {
                    textArea.setText("Formato de datos incorrecto al mostrar empleados.");
                }
                break;

            case RES_MOSTRAR_TODOS_EMPLEADOS_KO:
                JOptionPane.showMessageDialog(this, "Error al mostrar los empleados.");
                break;

            default:
                JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
                break;
        }
    }
}