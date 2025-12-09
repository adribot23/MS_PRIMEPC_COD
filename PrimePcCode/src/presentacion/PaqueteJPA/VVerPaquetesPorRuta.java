package presentacion.PaqueteJPA;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import negocio.PaqueteJPA.TPaquete;
import negocio.PaqueteJPA.TPaqueteExpress;
import negocio.PaqueteJPA.TPaqueteNormal;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerPaquetesPorRuta extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JTextField txtIdRuta;
    private JButton btnMostrar, btnVolver;

    public VVerPaquetesPorRuta() {
        super("Ver Paquetes por Ruta");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Filtrar paquetes por Ruta"));

        JLabel lblIdRuta = new JLabel("ID Ruta:");
        txtIdRuta = new JTextField();

        btnMostrar = new JButton("Mostrar paquetes");
        btnMostrar.setBackground(new Color(200, 255, 200));
        btnMostrar.addActionListener(e -> {
            try {
                int idRuta = Integer.parseInt(txtIdRuta.getText().trim());
                Controlador.getInstancia().accion(new Context(Evento.VER_PAQUETES_POR_RUTA, idRuta));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ID Ruta inválido.");
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
            dispose();
        });

        panel.add(lblIdRuta);
        panel.add(txtIdRuta);
        panel.add(btnMostrar);
        panel.add(btnVolver);

        add(panel);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VMOSTRAR_PAQUETES_POR_RUTA:
                setVisible(true);
                break;
            case RES_VER_PAQUETES_POR_RUTA_OK:
                mostrarTabla((Set<TPaquete>) datos);
                break;
            case RES_VER_PAQUETES_POR_RUTA_KO:
                JOptionPane.showMessageDialog(this, "No se encontraron paquetes para esa ruta.");
                break;
            default:
                break;
        }
    }

    private void mostrarTabla(Set<TPaquete> paquetes) {
        String[] columnNames = { "ID", "Número de Serie", "Peso", "Precio", "ID Ruta", "ID Factura", "Tipo", "Extra", "Activo" };
        Object[][] tableData = new Object[paquetes.size()][columnNames.length];

        int i = 0;
        for (TPaquete p : paquetes) {
            tableData[i][0] = p.getId();
            tableData[i][1] = p.getNumSerie();
            tableData[i][2] = p.getPeso();
            tableData[i][3] = p.getPrecio();
            tableData[i][4] = p.getIdRuta();
            tableData[i][5] = p.getIdFactura();
            tableData[i][8] = p.getActivo();

            if (p instanceof TPaqueteNormal normal) {
                tableData[i][6] = "Normal";
                tableData[i][7] = normal.getDescuento();
            } else if (p instanceof TPaqueteExpress express) {
                tableData[i][6] = "Express";
                tableData[i][7] = express.getPrioridad();
            } else {
                tableData[i][6] = "Desconocido";
                tableData[i][7] = "";
            }
            i++;
        }

        JTable table = new JTable(tableData, columnNames);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Paquetes por Ruta", JOptionPane.PLAIN_MESSAGE);
    }
}

