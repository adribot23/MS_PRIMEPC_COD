package presentacion.PaqueteJPA;

import javax.swing.JFrame;

import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;

public class VBajaPaquete extends JFrame implements IGUI {

    public VBajaPaquete() {
        super("Baja de Paquete");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(2, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Baja Paquete"));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();

        JButton btnBaja = new JButton("Dar de Baja");
        btnBaja.setBackground(new Color(200, 255, 200));
        btnBaja.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Controlador.getInstancia().accion(new Context(Evento.BAJA_PAQUETE, id));
                txtId.setText("");

            } catch (Exception ex) {
            	JOptionPane.showMessageDialog(this,
                        "Ha ocurrido un error inesperado: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
            this.dispose();
        });

        add(lblId);
        add(txtId);
        add(btnBaja);
        add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {

        switch (context.getEvento()) {

        case VBAJA_PAQUETE:
            setVisible(true);
            break;

        case RES_BAJA_PAQUETE_OK:
            JOptionPane.showMessageDialog(null, "Paquete dado de baja correctamente.");
            break;

        case RES_BAJA_PAQUETE_KO:
            JOptionPane.showMessageDialog(null, "Error al dar de baja el Paquete.");
            break;
        }
    }
}

