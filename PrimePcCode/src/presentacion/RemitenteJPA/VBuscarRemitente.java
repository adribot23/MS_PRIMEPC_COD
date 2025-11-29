package presentacion.RemitenteJPA;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarRemitente extends JFrame implements IGUI{

    public VBuscarRemitente() {
        super("Buscar Remitente");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(2, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Remitente"));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(200, 255, 200));

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Controlador.getInstancia().accion(new Context(Evento.BUSCAR_REMITENTE, id));
                txtId.setText("");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ID inválido.");
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.REMITENTE, null));
            this.dispose();
        });

        add(lblId); add(txtId);
        add(btnBuscar); add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {

        switch (context.getEvento()) {

        case VBUSCAR_REMITENTE:
            setVisible(true);
            break;

        case RES_BUSCAR_REMITENTE_OK:
            TRemitente t = (TRemitente) context.getDatos();
            JOptionPane.showMessageDialog(null,
                    "ID: " + t.getId() +
                    "\nNombre: " + t.getDireccion() +
                    "\nMatrícula: " + t.getTelefono() +
                    "\nActivo: " + t.getActivo());
            break;

        case RES_BUSCAR_REMITENTE_KO:
            JOptionPane.showMessageDialog(null, "No existe el Remitente solicitado.");
            break;
        }
    }
}
