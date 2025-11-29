package presentacion.TransporteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaTransporte extends JFrame implements IGUI {

    public VAltaTransporte() {
        super("Alta de Transporte");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(4, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Transporte"));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblCapacidad = new JLabel("Capacidad:");
        JTextField txtCapacidad = new JTextField();

        JLabel lblMatricula = new JLabel("Matrícula:");
        JTextField txtMatricula = new JTextField();

        JButton btnAlta = new JButton("Dar de Alta");
        btnAlta.setBackground(new Color(200, 255, 200));

        btnAlta.addActionListener(e -> {

            try {
                String nombre = txtNombre.getText().trim();
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                String matricula = txtMatricula.getText().trim();

                if (nombre.isEmpty() || matricula.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
                    return;
                }

                TTransporte t = new TTransporte(nombre, capacidad, matricula);
                Controlador.getInstancia().accion(new Context(Evento.ALTA_TRANSPORTE, t));

                txtNombre.setText("");
                txtCapacidad.setText("");
                txtMatricula.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos inválidos.");
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
            this.dispose();
        });

        add(lblNombre); add(txtNombre);
        add(lblCapacidad); add(txtCapacidad);
        add(lblMatricula); add(txtMatricula);
        add(btnAlta); add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        switch (context.getEvento()) {

        case VALTA_TRANSPORTE:
            setVisible(true);
            break;

        case RES_ALTA_TRANSPORTE_OK:
            JOptionPane.showMessageDialog(null, "Transporte dado de alta con ID: " + context.getDatos());
            break;

        case RES_ALTA_TRANSPORTE_KO:
            JOptionPane.showMessageDialog(null, "Error al dar de alta el transporte.");
            break;
        }
    }
}
