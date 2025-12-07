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
        String[] estados = { "No enviado", "Enviado", "Entregado" };
        JComboBox<String> cbEstado = new JComboBox<>(estados);

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
                String estado = (String) cbEstado.getSelectedItem();
                String pesoTxt = txtPeso.getText().trim();
                String precioTxt = txtPrecio.getText().trim();
                String idRutaTxt = txtIdRuta.getText().trim();
                String extraTxt = txtExtra.getText().trim();

                if (numSerie.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Número de serie' está vacío.");
                    return;
                }
                if (pesoTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Peso' está vacío.");
                    return;
                }
                if (precioTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Precio' está vacío.");
                    return;
                }
                if (idRutaTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'ID Ruta' está vacío.");
                    return;
                }
                if (extraTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Descuento/Prioridad' está vacío.");
                    return;
                }

                double peso;
                double precio;
                int idRuta;
                try {
                    peso = Double.parseDouble(pesoTxt);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Peso inválido. Debe ser un número.");
                    return;
                }
                try {
                    precio = Double.parseDouble(precioTxt);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Precio inválido. Debe ser un número.");
                    return;
                }
                try {
                    idRuta = Integer.parseInt(idRutaTxt);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID Ruta inválido. Debe ser un número entero.");
                    return;
                }

                TPaquete t;

                if (rdbNormal.isSelected()) {
                    double descuento;
                    try {
                        descuento = Double.parseDouble(extraTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Descuento inválido. Debe ser un número.");
                        return;
                    }
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
                    int prioridad;
                    try {
                        prioridad = Integer.parseInt(extraTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Prioridad inválida. Debe ser un número entero.");
                        return;
                    }
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

                // Limpiar campos
                txtNumSerie.setText("");
                cbEstado.setSelectedIndex(0);
                txtPeso.setText("");
                txtPrecio.setText("");
                txtIdRuta.setText("");
                txtExtra.setText("");

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
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
        add(cbEstado);

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
                JOptionPane.showMessageDialog(this, "Paquete dado de alta con ID: " + context.getDatos());
                break;
            case RES_ALTA_PAQUETE_KO:
                JOptionPane.showMessageDialog(this, "Error al dar de alta el Paquete.");
                break;
        }
    }
}



