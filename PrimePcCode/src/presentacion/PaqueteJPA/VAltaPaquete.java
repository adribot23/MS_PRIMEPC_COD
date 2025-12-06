package presentacion.PaqueteJPA;

import javax.swing.*;
import java.awt.*;

import negocio.PaqueteJPA.TPaquete;
import negocio.PaqueteJPA.TPaqueteExpress;
import negocio.PaqueteJPA.TPaqueteNormal;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VAltaPaquete extends JFrame implements IGUI {

    public VAltaPaquete() {
        super("Alta de Paquete");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(8, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Alta Paquete"));

        JLabel lblNumSerie = new JLabel("Número de serie:");
        JTextField txtNumSerie = new JTextField();

        JLabel lblEstado = new JLabel("Estado:");
        JTextField txtEstado = new JTextField();

        JLabel lblPeso = new JLabel("Peso:");
        JTextField txtPeso = new JTextField();

        JLabel lblPrecio = new JLabel("Precio:");
        JTextField txtPrecio = new JTextField();

        JLabel lblIdRuta = new JLabel("ID Ruta:");
        JTextField txtIdRuta = new JTextField();

        JLabel lblExtra = new JLabel("Descuento:");
        JTextField txtExtra = new JTextField();

        // Tipo de paquete
        JRadioButton rdbNormal = new JRadioButton("Normal");
        JRadioButton rdbExpress = new JRadioButton("Express");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbNormal);
        grupoTipo.add(rdbExpress);
        rdbNormal.setSelected(true);

        rdbNormal.addActionListener(e -> lblExtra.setText("Descuento:"));
        rdbExpress.addActionListener(e -> lblExtra.setText("Prioridad:"));

        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
        tipoPanel.add(rdbNormal);
        tipoPanel.add(rdbExpress);

        // Botón Alta
        JButton btnAlta = new JButton("Dar de Alta");
        btnAlta.setBackground(new Color(200, 255, 200));
        btnAlta.addActionListener(e -> {
            try {

                String numSerie = txtNumSerie.getText().trim();
                String estado = txtEstado.getText().trim();
                String pesoTxt = txtPeso.getText().trim();
                String precioTxt = txtPrecio.getText().trim();
                String idRutaTxt = txtIdRuta.getText().trim();
                String extraTxt = txtExtra.getText().trim();

                if (numSerie.isEmpty() || estado.isEmpty() || pesoTxt.isEmpty()
                        || precioTxt.isEmpty() || idRutaTxt.isEmpty() || extraTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
                    return;
                }

                double peso = Double.parseDouble(pesoTxt);
                double precio = Double.parseDouble(precioTxt);
                int idRuta = Integer.parseInt(idRutaTxt);

                TPaquete t;

                if (rdbNormal.isSelected()) {
                    double descuento = Double.parseDouble(extraTxt);
                    TPaqueteNormal tPn = new TPaqueteNormal();
                    tPn.setId(-1);
                    tPn.setActivo(1);
                    tPn.setNumSerie(numSerie);
                    tPn.setEstado(estado);
                    tPn.setPeso(peso);
                    tPn.setPrecio(precio);
                    tPn.setIdRuta(idRuta);
                    tPn.setDescuento(descuento);
                    t = tPn;

                } else {
                    int prioridad = Integer.parseInt(extraTxt);
                    TPaqueteExpress tPe = new TPaqueteExpress();
                    tPe.setId(-1);
                    tPe.setActivo(1);
                    tPe.setNumSerie(numSerie);
                    tPe.setEstado(estado);
                    tPe.setPeso(peso);
                    tPe.setPrecio(precio);
                    tPe.setIdRuta(idRuta);
                    tPe.setPrioridad(prioridad);
                    t = tPe;
                }

                Controlador.getInstancia().accion(new Context(Evento.ALTA_PAQUETE, t));

                txtNumSerie.setText("");
                txtEstado.setText("");
                txtPeso.setText("");
                txtPrecio.setText("");
                txtIdRuta.setText("");
                txtExtra.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos inválidos.");
            }
        });

        // Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
            this.dispose();
        });

        add(lblNumSerie);
        add(txtNumSerie);

        add(lblEstado);
        add(txtEstado);

        add(lblPeso);
        add(txtPeso);

        add(lblPrecio);
        add(txtPrecio);

        add(lblIdRuta);
        add(txtIdRuta);

        add(new JLabel("Tipo de Paquete:"));
        add(tipoPanel);

        add(lblExtra);
        add(txtExtra);

        add(btnAlta);
        add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {

        switch (context.getEvento()) {

        case VALTA_PAQUETE:
            setVisible(true);
            break;

        case RES_ALTA_PAQUETE_OK:
            JOptionPane.showMessageDialog(null, "Paquete dado de alta con ID: " + context.getDatos());
            break;

        case RES_ALTA_PAQUETE_KO:
            JOptionPane.showMessageDialog(null, "Error al dar de alta el Paquete.");
            break;
        }
    }
}


