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

public class VModificarPaquete extends JFrame implements IGUI {

    public VModificarPaquete() {
        super("Modificar Paquete");
        initGUI();
    }

    private void initGUI() {

        setLayout(new GridLayout(9, 2, 10, 10));
        getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Paquete"));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();

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

        // Botón Modificar
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(200, 255, 200));
        btnModificar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String numSerie = txtNumSerie.getText().trim();
                String estado = (String) cbEstado.getSelectedItem();
                double peso = Double.parseDouble(txtPeso.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int idRuta = Integer.parseInt(txtIdRuta.getText().trim());
                String extraTxt = txtExtra.getText().trim();

                if (numSerie.isEmpty() || extraTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
                    return;
                }

                TPaquete paquete;
                if (rdbNormal.isSelected()) {
                    double descuento = Double.parseDouble(extraTxt);
                    TPaqueteNormal tPn = new TPaqueteNormal();
                    tPn.setId(id);
                    tPn.setNumSerie(numSerie);
                    tPn.setEstado(estado);
                    tPn.setPeso(peso);
                    tPn.setPrecio(precio);
                    tPn.setIdRuta(idRuta);
                    tPn.setDescuento(descuento);
                    paquete = tPn;
                } else {
                    int prioridad = Integer.parseInt(extraTxt);
                    TPaqueteExpress tPe = new TPaqueteExpress();
                    tPe.setId(id);
                    tPe.setNumSerie(numSerie);
                    tPe.setEstado(estado);
                    tPe.setPeso(peso);
                    tPe.setPrecio(precio);
                    tPe.setIdRuta(idRuta);
                    tPe.setPrioridad(prioridad);
                    paquete = tPe;
                }

                Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_PAQUETE, paquete));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos inválidos: " + ex.getMessage());
            }
        });

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
            this.dispose();
        });

        // Agregar componentes en orden
        add(lblId); add(txtId);
        add(lblNumSerie); add(txtNumSerie);
        add(lblEstado); add(cbEstado);
        add(lblPeso); add(txtPeso);
        add(lblPrecio); add(txtPrecio);
        add(lblIdRuta); add(txtIdRuta);
        add(new JLabel("Tipo de Paquete:")); add(tipoPanel);
        add(lblExtra); add(txtExtra);
        add(btnModificar); add(btnVolver);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        switch (context.getEvento()) {
            case VMODIFICAR_PAQUETE:
                setVisible(true);
                break;
            case RES_MODIFICAR_PAQUETE_OK:
                JOptionPane.showMessageDialog(null, "Paquete modificado correctamente.");
                break;
            case RES_MODIFICAR_PAQUETE_KO:
                JOptionPane.showMessageDialog(null, "Error al modificar paquete.");
                break;
        }
    }
}

