package presentacion.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentacion.Controller.Command.Context;
import presentacion.Controller.Controlador;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class GUIVenta extends JPanel implements IGUI {

	private static final long serialVersionUID = 1L;

	private final JLabel statusLabel;

	public GUIVenta() {
		setLayout(new BorderLayout(20, 20));
		setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

		JLabel header = new JLabel("Gestión de ventas", SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(Font.BOLD, 28f));
		add(header, BorderLayout.PAGE_START);

		JPanel actionsPanel = new JPanel(new GridLayout(5, 2, 16, 16));
		actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		actionsPanel.add(createActionButton("Abrir venta", Evento.ABRIR_VENTA));
		actionsPanel.add(createActionButton("Cerrar venta", Evento.CERRAR_VENTA));
		actionsPanel.add(createActionButton("Añadir producto", Evento.INSERTAR_PRODUCTO_VENTA));
		actionsPanel.add(createActionButton("Eliminar producto", Evento.QUITAR_PRODUCTO_VENTA));
		actionsPanel.add(createActionButton("Devolver venta", Evento.DEVOLVER_VENTA));
		actionsPanel.add(createActionButton("Modificar venta", Evento.MODIFICAR_VENTA));
		actionsPanel.add(createActionButton("Buscar venta", Evento.BUSCAR_VENTA));
		actionsPanel.add(createActionButton("Listar todas las ventas", Evento.MOSTRAR_TODAS_VENTAS));
		actionsPanel.add(createActionButton("Ventas por empleado", Evento.MOSTRAR_VENTAS_POR_EMPLEADO));
		actionsPanel.add(createActionButton("Ventas por cliente", Evento.MOSTRAR_VENTAS_POR_CLIENTE));

		add(actionsPanel, BorderLayout.CENTER);

		statusLabel = new JLabel("Selecciona una acción para comenzar.", SwingConstants.CENTER);
		statusLabel.setForeground(new Color(70, 70, 70));
		statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		add(statusLabel, BorderLayout.PAGE_END);
	}

	private JButton createActionButton(String text, Evento evento) {
		JButton button = new JButton(text);
		button.addActionListener(e -> Controlador.getInstancia().accion(new Context(evento, null)));
		return button;
	}

	@Override
	public void actualizar(Context context) {
		if (context == null || context.getEvento() == null) {
			return;
		}

		statusLabel.setText("Acción enviada: " + context.getEvento().name());
	}
}
