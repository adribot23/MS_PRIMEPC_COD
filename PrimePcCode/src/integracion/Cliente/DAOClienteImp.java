/**
 * 
 */
package integracion.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class DAOClienteImp implements DAOCliente {
	
	private Connection conexion;

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/MSPrimePC.db", "root", "root"); //TODO Poner el nombre de la bd 
	}
	
	public Integer create(TCliente cliente) {
		 int id = cliente.getId();
		    Connection conexion = null;

		    try {
		        conexion = conectar();

		        String insertSql = "INSERT INTO CLIENTE (DNI, NOMBRE, ACTIVO) VALUES (?, ?, 1)";
		        PreparedStatement insertPs = conexion.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
		        insertPs.setString(1, cliente.getDni());
		        insertPs.setString(2, cliente.getNombre());
		        insertPs.executeUpdate();

		        ResultSet insertRs = insertPs.getGeneratedKeys();

		        if (insertRs.next()) {
		            id = insertRs.getInt(1);

		            if (cliente instanceof TClienteSocio) {
		                TClienteSocio socio = (TClienteSocio) cliente;
		                insertSql = "INSERT INTO SOCIO (ID, PUNTOS, NUM_SOCIO) VALUES (?, ?, ?)";
		                PreparedStatement psSocio = conexion.prepareStatement(insertSql);
		                if (id != -1)
		                    psSocio.setInt(1, id);
		                psSocio.setInt(2, socio.getPuntos());
		                psSocio.setInt(3, generarNumSocio(conexion)); 
		                psSocio.executeUpdate();
		                psSocio.close();
		            } else if (cliente instanceof TClienteNoSocio) {
		                TClienteNoSocio noSocio = (TClienteNoSocio) cliente;
		                insertSql = "INSERT INTO NOSOCIO (ID, NUM_VISITAS) VALUES (?, ?)";
		                PreparedStatement psNoSocio = conexion.prepareStatement(insertSql);
		                if (id != -1)
		                    psNoSocio.setInt(1, id);
		                psNoSocio.setInt(2, noSocio.getNumVisitas());
		                psNoSocio.executeUpdate();
		                psNoSocio.close();
		            }
		        } else {
		            throw new SQLException();
		        }

		        insertRs.close();
		        insertPs.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (conexion != null && !conexion.isClosed()) {
		                conexion.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return id;
	}

	public TCliente read(Integer id) {
		TCliente cliente = null;

		try {
			conexion = conectar();
			PreparedStatement psCliente = conexion
					.prepareStatement("SELECT DNI, NOMBRE, ACTIVO FROM CLIENTE WHERE ID = ?");
			psCliente.setInt(1, id);
			ResultSet rsCliente = psCliente.executeQuery();

			if (rsCliente.next()) {
				String dni = rsCliente.getString("DNI");
				String nombre = rsCliente.getString("NOMBRE");
				int activo = rsCliente.getInt("ACTIVO");

				PreparedStatement psSocio = conexion
						.prepareStatement("SELECT PUNTOS, NUM_SOCIO FROM SOCIO WHERE ID = ?");
				psSocio.setInt(1, id);
				ResultSet rsSocio = psSocio.executeQuery();

				if (rsSocio.next()) {
					int puntos = rsSocio.getInt("PUNTOS");
					int numSocio = rsSocio.getInt("NUM_SOCIO");

					TClienteSocio socio = new TClienteSocio(id, nombre,dni, activo, numSocio,puntos);
					cliente = socio;
				} else {
					PreparedStatement psNoSocio = conexion
							.prepareStatement("SELECT NUM_VISITAS FROM NOSOCIO WHERE ID = ?");
					psNoSocio.setInt(1, id);
					ResultSet rsNoSocio = psNoSocio.executeQuery();

					if (rsNoSocio.next()) {
						int numVisitas = rsNoSocio.getInt("NUM_VISITAS");

						TClienteNoSocio noSocio = new TClienteNoSocio(id, nombre,dni, activo, numVisitas);
						cliente = noSocio;
					}

					rsNoSocio.close();
					psNoSocio.close();
				}

				rsSocio.close();
				psSocio.close();
				
			}

			rsCliente.close();
			psCliente.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cliente;
	}

	public Integer update(TCliente cliente) {
		int filasAfectadas = 0;

		try {
			conexion = conectar();

			// Actualizar tabla CLIENTE
			String updateSql = "UPDATE CLIENTE SET DNI = ?, NOMBRE = ?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement updatePs = conexion.prepareStatement(updateSql);
			updatePs.setString(1, cliente.getDni());
			updatePs.setString(2, cliente.getNombre());
			updatePs.setInt(3, cliente.getId());
			filasAfectadas += updatePs.executeUpdate();
			updatePs.close();

			// Si es SOCIO, actualizamos tabla SOCIO
			if (cliente instanceof TClienteSocio) {
				TClienteSocio socio = (TClienteSocio) cliente;

				String updateSocioSql = "UPDATE SOCIO SET PUNTOS = ? WHERE ID = ?";
				PreparedStatement psSocio = conexion.prepareStatement(updateSocioSql);
				psSocio.setInt(1, socio.getPuntos());
				psSocio.setInt(2, socio.getId());
				filasAfectadas += psSocio.executeUpdate();
				psSocio.close();

			} else if (cliente instanceof TClienteNoSocio) {
				// Si es NO SOCIO, actualizamos tabla NOSOCIO
				TClienteNoSocio noSocio = (TClienteNoSocio) cliente;

				String updateNoSocioSql = "UPDATE NOSOCIO SET NUM_VISITAS = ? WHERE ID = ?";
				PreparedStatement psNoSocio = conexion.prepareStatement(updateNoSocioSql);
				psNoSocio.setInt(1, noSocio.getNumVisitas());
				psNoSocio.setInt(2, noSocio.getId());
				filasAfectadas += psNoSocio.executeUpdate();
				psNoSocio.close();

			}
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filasAfectadas;
	}

	public Integer delete(Integer id) {
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "UPDATE CLIENTE SET ACTIVO = 0 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}
	
/*
	@Override
	public int eliminarFisicamente(int id) {  //solo para el test
	    int filasAfectadas = 0;
	    try {
	        conexion = conectar();
	        String sql = "DELETE FROM CLIENTE WHERE ID = ?";
	        PreparedStatement ps = conexion.prepareStatement(sql);
	        ps.setInt(1, id);
	        filasAfectadas = ps.executeUpdate();
	        ps.close();
	        conexion.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return filasAfectadas;
	}

*/
	public TCliente read_by_DNI(String dni) {
	    if (dni == null || dni.isEmpty()) {
	        return null;
	    }

	    TCliente cliente = null;

	    try (Connection conexion = conectar();
	         PreparedStatement psCliente = conexion.prepareStatement("SELECT ID, NOMBRE, ACTIVO FROM CLIENTE WHERE DNI = ?")) {

	        psCliente.setString(1, dni);
	        try (ResultSet rsCliente = psCliente.executeQuery()) {
	            if (rsCliente.next()) {
	                int id = rsCliente.getInt("ID");
	                String nombre = rsCliente.getString("NOMBRE");
	                int activo = rsCliente.getInt("ACTIVO");

	                try (PreparedStatement psSocio = conexion.prepareStatement("SELECT PUNTOS, NUM_SOCIO FROM SOCIO WHERE ID = ?")) {
	                    psSocio.setInt(1, id);
	                    try (ResultSet rsSocio = psSocio.executeQuery()) {
	                        if (rsSocio.next()) {
	                            int puntos = rsSocio.getInt("PUNTOS");
	                            int numSocio = rsSocio.getInt("NUM_SOCIO");
	                            cliente = new TClienteSocio(id, nombre, dni, activo, numSocio, puntos);
	                        } else {
	                            try (PreparedStatement psNoSocio = conexion.prepareStatement("SELECT NUM_VISITAS FROM NOSOCIO WHERE ID = ?")) {
	                                psNoSocio.setInt(1, id);
	                                try (ResultSet rsNoSocio = psNoSocio.executeQuery()) {
	                                    if (rsNoSocio.next()) {
	                                        int numVisitas = rsNoSocio.getInt("NUM_VISITAS");
	                                        cliente = new TClienteNoSocio(id, nombre, dni, activo, numVisitas);
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cliente;
	}
	public Set<TCliente> read_all() {
	    Set<TCliente> clientes = new HashSet<>();
		String sql = "SELECT ID, DNI, NOMBRE, ACTIVO FROM CLIENTE";
	    try (Connection conexion = conectar();
	         PreparedStatement ps = conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("ID");
	            String dni = rs.getString("DNI");
	            String nombre = rs.getString("NOMBRE");
	            int activo = rs.getInt("ACTIVO");

	            Integer numSocio = rs.getObject("NUM_SOCIO", Integer.class);
	            Integer puntos = rs.getObject("PUNTOS", Integer.class);
	            Integer numVisitas = rs.getObject("NUM_VISITAS", Integer.class);

	            if (numSocio != null && puntos != null) {
	                clientes.add(new TClienteSocio(id, nombre, dni, activo, numSocio, puntos));
	            } else if (numVisitas != null) {
	                clientes.add(new TClienteNoSocio(id, nombre, dni, activo, numVisitas));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return clientes;
	}

	private int generarNumSocio(Connection conexion) throws SQLException {
	    String sql = "SELECT MAX(NUM_SOCIO) FROM SOCIO";
	    Statement s = conexion.createStatement();
	    ResultSet rs = s.executeQuery(sql);
	    int nuevoID = 1;
	    if (rs.next()) {
	        nuevoID = rs.getInt(1) + 1;
	    }
	    return nuevoID;
	}
}