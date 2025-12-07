package presentacion.TransporteJPA;

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

import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VVerTransportePorTrabajador extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JButton btnBuscar, btnVolver;

    public VVerTransportePorTrabajador() {
        super("Transportes por Trabajador");
        initGUI();
    }

    private void initGUI() {
       

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Transportes por Trabajador"));

        btnBuscar = new JButton("Buscar transporte del trabajador");
        btnBuscar.setBackground(new Color(200, 255, 200));
        btnBuscar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Introduce ID del trabajador:");
            if (input != null) {
                try {
                    int id = Integer.parseInt(input.trim());
                    Controlador.getInstancia().accion(new Context(Evento.VER_TRANSPORTE_POR_TRABAJADOR, id));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido.");
                }
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
            dispose();
        });

        panel.add(btnBuscar);
        panel.add(btnVolver);
        add(panel);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VVER_TRANSPORTE_POR_TRABAJADOR:
                setVisible(true);
                break;

            case RES_VER_TRANSPORTE_POR_TRABAJADOR_OK:
                mostrarTabla((Set<TTransporte>) datos);
                break;

            case RES_VER_TRANSPORTE_POR_TRABAJADOR_KO:
                JOptionPane.showMessageDialog(this, "Este trabajador no tiene transportes asignados.");
                break;

            default:
                JOptionPane.showMessageDialog(null, "Evento no reconocido: " + context.getEvento());
        }
    }

    private void mostrarTabla(Set<TTransporte> transportes) {
        String[] columnas = { "ID", "Nombre", "Capacidad", "Matrícula", "Activo" };
        Object[][] datos = new Object[transportes.size()][columnas.length];

        int i = 0;
        for (TTransporte t : transportes) {
            datos[i][0] = t.getId();
            datos[i][1] = t.getNombre();
            datos[i][2] = t.getCapacidad();
            datos[i][3] = t.getMatricula();
            datos[i][4] = t.getActivo();
            i++;
        }

        JTable table = new JTable(datos, columnas);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Transportes del Trabajador", JOptionPane.PLAIN_MESSAGE);
    }
}
