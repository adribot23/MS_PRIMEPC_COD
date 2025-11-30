package presentacion.RemitenteJPA;

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

import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarRemitente extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JButton btnMostrar, btnVolver;
    private JTable tabla;

    public VMostrarRemitente() {
        super("Mostrar Remitentes");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel superior con botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Remitentes"));

        btnMostrar = new JButton("Mostrar todos los Remitentes");
        btnMostrar.setBackground(new Color(200, 255, 200));
        btnMostrar.addActionListener(e -> Controlador.getInstancia()
                .accion(new Context(Evento.MOSTRAR_TODOS_REMITENTES, null)));

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 220, 220));
        btnVolver.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(Evento.REMITENTE, null));
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
            case VMOSTRAR_TODOS_REMITENTES:
                setVisible(true);
                break;

            case RES_MOSTRAR_TODOS_REMITENTES_OK:
                mostrarTabla((Set<TRemitente>) datos);
                break;

            case RES_MOSTRAR_TODOS_REMITENTES_KO:
                JOptionPane.showMessageDialog(this, "No se pudieron mostrar los Remitentes.");
                break;

            default:
                break;
        }
    }

    private void mostrarTabla(Set<TRemitente> Remitentes) {
        String[] columnas = { "ID", "Telefono", "Direccion", "Activo" };
        Object[][] datos = new Object[Remitentes.size()][columnas.length];

        int i = 0;
        for (TRemitente t : Remitentes) {
            datos[i][0] = t.getId();
            datos[i][2] = t.getTelefono();
            datos[i][3] = t.getDireccion();
            datos[i][4] = t.getActivo();
            i++;
        }

        tabla.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }
}
