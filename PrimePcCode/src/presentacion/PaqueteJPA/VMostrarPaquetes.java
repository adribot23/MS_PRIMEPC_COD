package presentacion.PaqueteJPA;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.PaqueteJPA.TPaquete;
import negocio.PaqueteJPA.TPaqueteExpress;
import negocio.PaqueteJPA.TPaqueteNormal;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarPaquetes extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JButton btnMostrar, btnVolver;

    public VMostrarPaquetes() {
        super("Mostrar Paquetes");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Mostrar Paquetes"));

        btnMostrar = new JButton("Mostrar todos los paquetes");
        btnMostrar.setBackground(new Color(200, 255, 200));
        btnMostrar.addActionListener(
                e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_PAQUETES, null)));

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.PAQUETE, null));
            dispose();
        });

        panel.add(btnMostrar);
        panel.add(btnVolver);
        add(panel);

        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VMOSTRAR_TODOS_PAQUETES:
                setVisible(true);
                break;
            case RES_MOSTRAR_TODOS_PAQUETES_OK:
                mostrarTabla((Set<TPaquete>) datos);
                break;
            case RES_MOSTRAR_TODOS_PAQUETES_KO:
                JOptionPane.showMessageDialog(this, "No se pudieron mostrar los paquetes.");
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
        JOptionPane.showMessageDialog(null, scrollPane, "Paquetes", JOptionPane.PLAIN_MESSAGE);
    }
}

