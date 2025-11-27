package presentacion.TransporteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVincularTransporteTrabajador extends JFrame implements IGUI {

    public VVincularTransporteTrabajador() {
        super("Vincular Transporte a Trabajador");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(3,2,10,10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Vincular Transporte"));

        JLabel lblIdT = new JLabel("ID Transporte:");
        JTextField txtIdT = new JTextField();

        JLabel lblIdTrab = new JLabel("ID Trabajador:");
        JTextField txtIdTrab = new JTextField();

        JButton btnVincular = new JButton("Vincular");
        btnVincular.setBackground(new Color(200,255,200));

        btnVincular.addActionListener(e -> {
            try {
                int[] datos = {
                    Integer.parseInt(txtIdT.getText().trim()),
                    Integer.parseInt(txtIdTrab.getText().trim())
                };

                Controlador.getInstancia().accion(new Context(Evento.VINCULAR_TRANSPORTE_TRABAJADOR, datos));

            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos inválidos.");
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255,220,220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
            this.dispose();
        });

        add(lblIdT); add(txtIdT);
        add(lblIdTrab); add(txtIdTrab);
        add(btnVincular); add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350,200);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {

        switch(context.getEvento()) {

        case VVINCULAR_TRANSPORTE_TRABAJADOR:
            setVisible(true);
            break;

        case RES_VINCULAR_TRANSPORTE_TRABAJADOR_OK:
            JOptionPane.showMessageDialog(null, "Vinculación realizada correctamente.");
            break;

        case RES_VINCULAR_TRANSPORTE_TRABAJADOR_KO:
            JOptionPane.showMessageDialog(null, "Error al vincular transporte.");
            break;
        }
    }
}
