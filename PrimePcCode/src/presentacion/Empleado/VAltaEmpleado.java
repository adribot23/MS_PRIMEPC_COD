package presentacion.Empleado;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.IGUI;
import presentacion.GUI.Evento;

public class VAltaEmpleado extends JFrame implements IGUI {

    public VAltaEmpleado() {
        super("Alta de Empleado");
        initGUI();
    }

    public void initGUI() {
        // Configuración de la ventana
        setLayout(new GridLayout(6, 1, 5, 5));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Empleado"));

        // Campos de texto
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField altaNombre = new JTextField();

        JLabel lblDNI = new JLabel("DNI:");
        JTextField altaDNI = new JTextField();

        JLabel lblTlf = new JLabel("Teléfono:");
        JTextField altaTlf = new JTextField();

        // Radio buttons para tipo de empleado
        JRadioButton rdbCompleto = new JRadioButton("Completo");
        JRadioButton rdbParcial = new JRadioButton("Parcial");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbCompleto);
        grupoTipo.add(rdbParcial);
        rdbCompleto.setSelected(true);

        JLabel tipoLabel = new JLabel("Tipo de Empleado:");
        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
        tipoPanel.add(rdbCompleto);
        tipoPanel.add(rdbParcial);

        // Label y campo de horas
        JLabel lblHoras = new JLabel("Horas Extra:");
        JTextField altaHoras = new JTextField();

        // Cambiar texto del label según el tipo
        rdbCompleto.addActionListener(e -> lblHoras.setText("Horas Extra:"));
        rdbParcial.addActionListener(e -> lblHoras.setText("Horas Semanales:"));

        // Botón Dar de Alta
        JButton btnAlta = new JButton("Dar de Alta");
        btnAlta.setBackground(new Color(200, 255, 200));
        btnAlta.addActionListener(e -> {
            try {
                String nombre = altaNombre.getText().trim();
                String dni = altaDNI.getText().trim();
                String tlf = altaTlf.getText().trim();
                int horas = Integer.parseInt(altaHoras.getText().trim());

                if (!nombre.isEmpty() && !dni.isEmpty() && !tlf.isEmpty()) {
                    TEmpleado empleado;
                    if (rdbCompleto.isSelected()) {
                        empleado = new TEmpleadoCompleto(-1, nombre, dni, tlf, horas);
                    } else {
                        empleado = new TEmpleadoParcial(-1, nombre, dni, tlf, horas);
                    }

                    Controlador.getInstancia().accion(new Context(Evento.ALTA_EMPLEADO, empleado));

                    // Limpiar campos
                    altaNombre.setText("");
                    altaDNI.setText("");
                    altaTlf.setText("");
                    altaHoras.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenados.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Horas deben ser un número.");
            }
        });

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.EMPLEADO, null));
            this.dispose();
        });

        // Añadir componentes
        add(lblNombre);
        add(altaNombre);
        add(lblDNI);
        add(altaDNI);
        add(lblTlf);
        add(altaTlf);
        add(lblHoras);
        add(altaHoras);
        add(tipoLabel); 
        add(tipoPanel);
        add(btnAlta);
        add(btnVolver);

        // Configuración final de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();
        switch (evento) {
            case VALTA_EMPLEADO:
                this.setVisible(true);
                break;
            case RES_ALTA_EMPLEADO_OK:
                JOptionPane.showMessageDialog(this, "Empleado dado de alta con ID: " + datos);
                break;
            case RES_ALTA_EMPLEADO_KO:
                JOptionPane.showMessageDialog(this, "Error al dar de alta el empleado.");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);
        }
    }
}
