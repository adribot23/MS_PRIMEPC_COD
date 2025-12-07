package presentacion.PaqueteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.PaqueteJPA.TPaquete;
import negocio.PaqueteJPA.TPaqueteExpress;
import negocio.PaqueteJPA.TPaqueteNormal;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarPaquete extends JFrame implements IGUI {

    public VBuscarPaquete() {
        super("Buscar Paquete");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(2, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Paquete"));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(200, 255, 200));

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Controlador.getInstancia().accion(new Context(Evento.BUSCAR_PAQUETE, id));
                txtId.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ID inválido.");
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
        add(btnBuscar);
        add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {

        switch (context.getEvento()) {

        case VBUSCAR_PAQUETE:
            setVisible(true);
            break;

        case RES_BUSCAR_PAQUETE_OK: {
            TPaquete t = (TPaquete) context.getDatos();

            StringBuilder msg = new StringBuilder();

            msg.append("ID: ").append(t.getId()).append("\n");
            msg.append("Número de serie: ").append(t.getNumSerie()).append("\n");
            msg.append("Estado: ").append(t.getEstado()).append("\n");
            msg.append("Peso: ").append(t.getPeso()).append("\n");
            msg.append("Precio: ").append(t.getPrecio()).append("\n");
            msg.append("Activo: ").append(t.getActivo()).append("\n");

            // Tipo específico
            if (t instanceof TPaqueteExpress) {
                msg.append("Tipo: EXPRESS\n");
                msg.append("Prioridad: ").append(((TPaqueteExpress) t).getPrioridad()).append("\n");
            } else if (t instanceof TPaqueteNormal) {
                msg.append("Tipo: NORMAL\n");
                msg.append("Descuento: ").append(((TPaqueteNormal) t).getDescuento()).append("\n");
            }

            JOptionPane.showMessageDialog(null, msg.toString());
            break;
        }

        case RES_BUSCAR_PAQUETE_KO:
            JOptionPane.showMessageDialog(null, "No existe el Paquete solicitado.");
            break;
        }
    }
}
