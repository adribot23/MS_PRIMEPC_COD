package presentacion.vista.empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.vista.Evento;

public class VBuscarEmpleado extends JPanel {
	
	private static final long serialVersionUID = 1L;
    private Controlador ctrl;
    
    public VBuscarEmpleado() {
        this.ctrl = Controlador.obtenerInstancia();
        initGUI();
    }
    
    private void initGUI() {
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createTitledBorder("Buscar empleado"));

        JTextField txtBuscarID = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(200, 255, 200));
        add(new JLabel("ID empleado:"));
        add(txtBuscarID);
        add(btnBuscar);
        
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtBuscarID.getText());
                ctrl.accion(Evento.BUSCAR_EMPLEADO, id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID debe ser un numero.");
            }
        });
    }
}
