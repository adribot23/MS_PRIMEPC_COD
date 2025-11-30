/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VBuscarCliente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VBuscarCliente() {
		super("Buscar Cliente");
		initGUI();
	}

	public void initGUI() {
		setLayout(new GridLayout(2, 2, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Buscar Cliente"));

		JTextField txtBuscarID = new JTextField();
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(200, 255, 200));

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			dispose();
		});

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtBuscarID.getText());
				Controlador.getInstancia().accion(new Context(Evento.BUSCAR_CLIENTE, id));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});

		add(new JLabel("ID del Cliente:"));
		add(txtBuscarID);
		add(btnBuscar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 150);
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		switch (evento) {
		case VBUSCAR_CLIENTE:
			this.setVisible(true);
			break;
		case RES_BUSCAR_CLIENTE_OK:
			JOptionPane.showMessageDialog(this, datos.toString());
			break;
		case RES_BUSCAR_CLIENTE_KO:
			JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
			break;
		default:
			JOptionPane.showMessageDialog(this, "Evento no reconocido: " + evento);

		}
	}
}