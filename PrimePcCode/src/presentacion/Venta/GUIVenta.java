package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class GUIVenta extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    public GUIVenta() {
        super("[VENTA]");
        initGUI();
    }

    private void initGUI() {
        // === PANEL PRINCIPAL ===
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);
        this.setContentPane(mainPanel);

        // === CABECERA ===
        JLabel titulo = new JLabel("GESTIÓN DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Cambria", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 100, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // === PANEL DE BOTONES ===
        JPanel botonesPanel = new JPanel(new GridLayout(5, 2, 20, 20));
        botonesPanel.setBackground(Color.WHITE);

        // Fila 1
        botonesPanel.add(crearBotonVerde("ABRIR VENTA", Evento.VABRIR_VENTA));
        botonesPanel.add(crearBotonVerde("CERRAR VENTA", Evento.VCERRAR_VENTA));

        // Fila 2
        botonesPanel.add(crearBotonVerde("AÑADIR PRODUCTO A VENTA", Evento.VINSERTAR_PRODUCTO_VENTA));
        botonesPanel.add(crearBotonVerde("ELIMINAR PRODUCTO DE VENTA", Evento.VQUITAR_PRODUCTO_VENTA));

        // Fila 3
        botonesPanel.add(crearBotonVerde("MODIFICAR VENTA", Evento.VMODIFICAR_VENTA));
        botonesPanel.add(crearBotonVerde("DEVOLVER VENTA", Evento.VDEVOLVER_VENTA));

        // Fila 4
        botonesPanel.add(crearBotonVerde("BUSCAR VENTA", Evento.VBUSCAR_VENTA));
        botonesPanel.add(crearBotonVerde("MOSTRAR TODAS LAS VENTAS", Evento.VMOSTRAR_TODAS_VENTAS));

        // Fila 5
        botonesPanel.add(crearBotonVerde("VENTAS POR EMPLEADO", Evento.VMOSTRAR_VENTAS_POR_EMPLEADO));
        botonesPanel.add(crearBotonVerde("VENTAS POR CLIENTE", Evento.VMOSTRAR_VENTAS_POR_CLIENTE));

        mainPanel.add(botonesPanel, BorderLayout.CENTER);

        // === PANEL INFERIOR (VOLVER) ===
        JButton volver = new JButton("VOLVER A VISTA PRINCIPAL");
        volver.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volver.setBackground(Color.WHITE);
        volver.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
        volver.setFocusPainted(false);
        volver.setPreferredSize(new Dimension(300, 40));

        volver.addActionListener(e -> {
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
