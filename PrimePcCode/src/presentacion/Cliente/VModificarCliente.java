/**
 * 
 */
package presentacion.Cliente;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteSocio;
import negocio.Cliente.TClienteNoSocio;
import presentacion.Controller.Controlador;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import presentacion.GUI.IGUI;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VModificarCliente extends JFrame implements IGUI {
	
	public VModificarCliente() {
		super("Modificar Cliente");
		initGUI();
	}
	
	public void initGUI() {
		setLayout(new GridLayout(6, 1, 10, 10));
		getRootPane().setBorder(BorderFactory.createTitledBorder("Modificar Cliente"));

		JTextField modificarID = new JTextField();
		JTextField modificarNombre = new JTextField();
		JTextField modificarDNI = new JTextField();

		JLabel lblPuntos = new JLabel("Puntos:");
		JTextField modificarPuntos = new JTextField();
		
		JRadioButton rdbSocio = new JRadioButton("Socio");
        JRadioButton rdbNosocio = new JRadioButton("No socio");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbSocio);
        grupoTipo.add(rdbNosocio);
        rdbSocio.setSelected(true);

        JPanel tipoPanel = new JPanel(new GridLayout(1, 2));
        tipoPanel.add(rdbSocio);
        tipoPanel.add(rdbNosocio);
        
        rdbSocio.addActionListener(e -> lblPuntos.setText("Puntos:"));
        rdbNosocio.addActionListener(e -> lblPuntos.setText("Numero de Visitas:"));
        
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(200, 255, 200));
		btnModificar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(modificarID.getText().trim());
				String nombre = modificarNombre.getText().trim();
				String dni = modificarDNI.getText().trim();
				int puntosVisitas = Integer.parseInt(modificarPuntos.getText().trim());

				if (nombre.isEmpty() || dni.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenados.");
                    return;
				}
				
				TCliente cliente;
				if(rdbSocio.isSelected()) {
					cliente = new TClienteSocio(id, nombre, dni, puntosVisitas);
				} else {
					cliente = new TClienteNoSocio(id, nombre, dni, puntosVisitas);
				}
				

				Controlador.getInstancia().accion(new Context(Evento.MODIFICAR_CLIENTE, cliente));
				modificarID.setText("");
				modificarNombre.setText("");
				modificarDNI.setText("");
				modificarPuntos.setText("");
				lblPuntos.setText("Puntos:");
				rdbSocio.setSelected(true);
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID y Puntos/Visitas deben ser números.");
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 220, 220));
		btnVolver.addActionListener(e -> {
			Controlador.getInstancia().accion(new Context(Evento.CLIENTE, null));
			dispose();
		});

		add(new JLabel("ID cliente:"));
		add(modificarID);
		add(new JLabel("Nombre:"));
		add(modificarNombre);
		add(new JLabel("DNI:"));
		add(modificarDNI);
		add(new JLabel("Tipo de Cliente:")); add(tipoPanel);
		add(lblPuntos);
		add(modificarPuntos);
		add(btnModificar);
		add(btnVolver);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
	    setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();
		switch (evento) {
			case VMODIFICAR_CLIENTE:
				this.setVisible(true);
				break;
			case RES_MODIFICAR_CLIENTE_OK:
				JOptionPane.showMessageDialog(null, "Cliente modificado con ID: " + datos);
				break;
			case RES_MODIFICAR_CLIENTE_KO:
				JOptionPane.showMessageDialog(null, "Error al modificar el cliente.");
				break;
			default:
				JOptionPane.showMessageDialog(null, "Evento no reconocido: " + evento);
		}
	}
}
