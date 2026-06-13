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


        // -------- FILA EXTRA (DESCUENTO / PRIORIDAD) ------------
        JLabel lblExtra = new JLabel("Descuento:");

        JTextField txtDescuento = new JTextField(); // Solo para Normal

        JComboBox<Integer> cbPrioridad = new JComboBox<>();
        for (int i = 1; i <= 5; i++) cbPrioridad.addItem(i);
        cbPrioridad.setVisible(false); // Empieza oculto (tipo normal inicial)

        JPanel panelExtra = new JPanel(new GridLayout(1, 2));
        panelExtra.add(txtDescuento);
        panelExtra.add(cbPrioridad);

        // ---------------------------------------------------------


        // Tipo de paquete
        JRadioButton rdbNormal = new JRadioButton("Normal", true);
        JRadioButton rdbExpress = new JRadioButton("Express");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbNormal);
        grupoTipo.add(rdbExpress);

        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
        tipoPanel.add(rdbNormal);
        tipoPanel.add(rdbExpress);

        // Cambiar campo extra dinámicamente
        rdbNormal.addActionListener(e -> {
            lblExtra.setText("Descuento:");
            txtDescuento.setVisible(true);
            cbPrioridad.setVisible(false);
        });

        rdbExpress.addActionListener(e -> {
            lblExtra.setText("Prioridad:");
            txtDescuento.setVisible(false);
            cbPrioridad.setVisible(true);
        });


        // Botón alta
        JButton btnAlta = new JButton("Dar de Alta");
        btnAlta.setBackground(new Color(200, 255, 200));
        btnAlta.addActionListener(e -> {

            try {
                String numSerie = txtNumSerie.getText().trim();
                String estado = (String) cbEstado.getSelectedItem();
                double peso = Double.parseDouble(txtPeso.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int idRuta = Integer.parseInt(txtIdRuta.getText().trim());

                TPaquete t;

                if (rdbNormal.isSelected()) {
                    double descuento = Double.parseDouble(txtDescuento.getText().trim());

                    TPaqueteNormal tPn = new TPaqueteNormal();
                    tPn.setActivo(1);
                    tPn.setNumSerie(numSerie);
                    tPn.setEstado(estado);
                    tPn.setPeso(peso);
                    tPn.setPrecio(precio);
                    tPn.setIdRuta(idRuta);
                    tPn.setDescuento(descuento);
                    t = tPn;

                } else {
                    int prioridad = (Integer) cbPrioridad.getSelectedItem();

                    TPaqueteExpress tPe = new TPaqueteExpress();
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
                cbEstado.setSelectedIndex(0);
                txtPeso.setText("");
                txtPrecio.setText("");
                txtIdRuta.setText("");
                txtDescuento.setText("");

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


        // Añadir componentes
        add(lblNumSerie); add(txtNumSerie);
        add(lblEstado); add(cbEstado);
        add(lblPeso); add(txtPeso);
        add(lblPrecio); add(txtPrecio);
        add(lblIdRuta); add(txtIdRuta);
        add(new JLabel("Tipo de Paquete:")); add(tipoPanel);
        add(lblExtra); add(panelExtra);
        add(btnAlta); add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(460, 420);
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




