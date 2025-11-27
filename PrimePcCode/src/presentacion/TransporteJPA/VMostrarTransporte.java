package presentacion.TransporteJPA;

import java.awt.BorderLayout;
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

public class VMostrarTransporte extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JButton btnMostrar, btnVolver;
    private JTable tabla;

    public VMostrarTransporte() {
        super("Mostrar Transportes");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel superior con botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Transportes"));

        btnMostrar = new JButton("Mostrar todos los transportes");
        btnMostrar.setBackground(new Color(200, 255, 200));
        btnMostrar.addActionListener(e -> Controlador.getInstancia()
                .accion(new Context(Evento.MOSTRAR_TODOS_TRANSPORTES, null)));

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.TRANSPORTE, null));
            dispose();
        });

        panelBotones.add(btnMostrar);
        panelBotones.add(btnVolver);

        // Tabla con scroll
        tabla = new JTable();
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setEnabled(false);
        JScrollPane scroll = new JScrollPane(tabla);

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        Evento evento = context.getEvento();
        Object datos = context.getDatos();

        switch (evento) {
            case VMOSTRAR_TODOS_TRANSPORTES:
                setVisible(true);
                break;

            case RES_MOSTRAR_TODOS_TRANSPORTES_OK:
                mostrarTabla((Set<TTransporte>) datos);
                break;

            case RES_MOSTRAR_TODOS_TRANSPORTES_KO:
                JOptionPane.showMessageDialog(this, "No se pudieron mostrar los transportes.");
                break;

            default:
                break;
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

        tabla.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }
}
