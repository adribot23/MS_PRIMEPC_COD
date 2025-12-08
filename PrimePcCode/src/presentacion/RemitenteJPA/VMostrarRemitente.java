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

import negocio.RemitenteJPA.TEmpresa;
import negocio.RemitenteJPA.TParticular;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

public class VMostrarRemitente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton btnMostrar, btnVolver;

	public VMostrarRemitente() {
		super("Mostrar Remitentes");
		initGUI();
	}

	private void initGUI() {		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mostrar Transportes"));

		btnMostrar = new JButton("Mostrar todos los remitentes");
		btnMostrar.setBackground(new Color(200, 255, 200));
		btnMostrar.addActionListener(
				e -> Controlador.getInstancia().accion(new Context(Evento.MOSTRAR_TODOS_REMITENTES, null)));

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.REMITENTE, null));
			dispose();
		});

		panel.add(btnMostrar);
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
		String[] columnas = { "ID", "Nombre", "Telefono", "Direccion", "Activo", "Tipo", "Num. Registro Fiscal", "Fecha Nacimiento" };
		Object[][] datos = new Object[Remitentes.size()][columnas.length];

		int i = 0;
	    for (TRemitente t : Remitentes) {

	        datos[i][0] = t.getId();
	        datos[i][1] = t.getNombre();
	        datos[i][2] = t.getTelefono();
	        datos[i][3] = t.getDireccion();
	        datos[i][4] = t.getActivo();

	        if (t instanceof TEmpresa) {
	            TEmpresa e = (TEmpresa) t;

	            datos[i][5] = "Empresa";
	            datos[i][6] = e.getNumRegistroFiscal();
	            datos[i][7] = "---";               
	        } 
	        else if (t instanceof TParticular) {
	            TParticular p = (TParticular) t;

	            datos[i][5] = "Particular";
	            datos[i][6] = "---";               
	            datos[i][7] = p.getFechaNacimiento(); 
	        } 
	        else {
	            datos[i][4] = "Desconocido";
	            datos[i][5] = "---";
	            datos[i][6] = "---";
	        }

	        i++;
	    }

	    JTable table = new JTable(datos, columnas);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new java.awt.Dimension(900, 400));
		JOptionPane.showMessageDialog(null, scrollPane, "Remitentes", JOptionPane.PLAIN_MESSAGE);
	}
}
