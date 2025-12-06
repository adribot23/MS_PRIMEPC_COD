package presentacion.PaqueteJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class GUIPaquete extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    public GUIPaquete() {
        super("[PAQUETE]");
        initGUI();
    }

    private void initGUI() {
        // === PANEL PRINCIPAL ===
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);
        this.setContentPane(mainPanel);

        // === CABECERA ===
        JLabel titulo = new JLabel("GESTIÓN DE PAQUETES", SwingConstants.CENTER);
        titulo.setFont(new Font("Cambria", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 100, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // === PANEL DE BOTONES ===
        JPanel botonesPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        botonesPanel.setBackground(Color.WHITE);

        // Fila 1
        botonesPanel.add(crearBotonVerde("ALTA PAQUETE", Evento.VALTA_PAQUETE));
        botonesPanel.add(crearBotonVerde("BAJA PAQUETE", Evento.VBAJA_PAQUETE));
        botonesPanel.add(crearBotonVerde("MODIFICAR PAQUETE", Evento.VMODIFICAR_PAQUETE));

        // Fila 2
        botonesPanel.add(crearBotonVerde("MOSTRAR TODOS LOS PAQUETES", Evento.VMOSTRAR_TODOS_PAQUETES));
        botonesPanel.add(crearBotonVerde("VER PAQUETES POR FACTURA", Evento.VMOSTRAR_PAQUETES_POR_FACTURA));
        botonesPanel.add(crearBotonVerde("VER PAQUETES POR RUTA", Evento.VMOSTRAR_PAQUETES_POR_RUTA));

        mainPanel.add(botonesPanel, BorderLayout.CENTER);

        // === PANEL INFERIOR (VOLVER) ===
        JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
        volver.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volver.setBackground(Color.WHITE);
        volver.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
        volver.setFocusPainted(false);
        volver.setPreferredSize(new Dimension(300, 40));

        volver.addActionListener((e) -> {
            Controlador.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
            this.dispose();
        });

        JPanel volverPanel = new JPanel();
        volverPanel.setBackground(Color.WHITE);
        volverPanel.add(volver);
        mainPanel.add(volverPanel, BorderLayout.SOUTH);

        // === CONFIGURACIÓN FRAME ===
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton crearBotonVerde(String texto, Evento evento) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));
        boton.setOpaque(true);
        boton.setPreferredSize(new Dimension(250, 100));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(220, 255, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });

        // Acción del botón
        boton.addActionListener(e -> {
            Controlador.getInstancia().accion(new Context(evento, null));
            this.dispose();
        });

        return boton;
    }

    @Override
    public void actualizar(Context context) {
        setVisible(true);
    }
}
