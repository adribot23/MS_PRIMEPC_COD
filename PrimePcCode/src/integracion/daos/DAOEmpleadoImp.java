
package integracion.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import negocio.transfers.TEmpleado;
import negocio.transfers.TEmpleadoCompleto;
import negocio.transfers.TEmpleadoParcial;

public class DAOEmpleadoImp implements DAOEmpleado {

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	}

	@Override
	public int crear(TEmpleado empleado) {
		int id = empleado.getId();
		Connection conexion = null;

		try {
			conexion = conectar();

			String insertSql = "INSERT INTO EMPLEADO (DNI, NOMBRE, TELEFONO, ACTIVO) VALUES (?, ?, ?, 1)";
			PreparedStatement insertPs = conexion.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			insertPs.setString(1, empleado.getDni());
			insertPs.setString(2, empleado.getNombre());
			insertPs.setString(3, empleado.getTelefono());
			insertPs.executeUpdate();

			ResultSet insertRs = insertPs.getGeneratedKeys();

			if (insertRs.next()) {
				id = insertRs.getInt(1);
				if (empleado instanceof TEmpleadoCompleto) {
					TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
					insertSql = "INSERT INTO COMPLETO (ID, HORAS_EXTRA) VALUES (?, ?)";
					PreparedStatement psCompleto = conexion.prepareStatement(insertSql);
					if (id != -1)
						psCompleto.setInt(1, id);
					psCompleto.setInt(2, completo.getHorasExtra());

					psCompleto.executeUpdate();
					psCompleto.close();
				} else if (empleado instanceof TEmpleadoParcial) {
					TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
					insertSql = "INSERT INTO PARCIAL (ID, HORAS_SEMANALES) VALUES (?, ?)";
					PreparedStatement psParcial = conexion.prepareStatement(insertSql);
					if (id != -1)
						psParcial.setInt(1, id);
					psParcial.setInt(2, parcial.getHorasSemanales());
					psParcial.executeUpdate();
					psParcial.close();
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

	@Override
	public TEmpleado leer(int id) {
		TEmpleado empleado = null;
		Connection conexion = null;

		try {
			conexion = conectar();

			String sqlEmpleado = "SELECT DNI, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO WHERE ID = ?";
			PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado);
			psEmpleado.setInt(1, id);
			ResultSet rsEmpleado = psEmpleado.executeQuery();

			if (rsEmpleado.next()) {
				String dni = rsEmpleado.getString("DNI");
				String nombre = rsEmpleado.getString("NOMBRE");
				String telefono = rsEmpleado.getString("TELEFONO");
				int activo = rsEmpleado.getInt("ACTIVO");

				String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ?";
				PreparedStatement psCompleto = conexion.prepareStatement(sqlCompleto);
				psCompleto.setInt(1, id);
				ResultSet rsCompleto = psCompleto.executeQuery();

				if (rsCompleto.next()) {
					int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
					empleado = new TEmpleadoCompleto(id, nombre, dni, telefono, activo, horasExtra);
				} else {
					String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ?";
					PreparedStatement psParcial = conexion.prepareStatement(sqlParcial);
					psParcial.setInt(1, id);
					ResultSet rsParcial = psParcial.executeQuery();

					if (rsParcial.next()) {
						int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
						empleado = new TEmpleadoParcial(id, nombre, dni, telefono, activo, horasSemanales);
					}
					rsParcial.close();
					psParcial.close();
				}
				rsCompleto.close();
				psCompleto.close();
			}
			rsEmpleado.close();
			psEmpleado.close();
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

		return empleado;
	}

	@Override
	public int actualizar(TEmpleado empleado) {
		int filasAfectadas = 0;
		Connection conexion = null;

		try {
			conexion = conectar();

			String updateSql = "UPDATE EMPLEADO SET DNI = ?, NOMBRE = ?, TELEFONO = ?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement updatePs = conexion.prepareStatement(updateSql);
			updatePs.setString(1, empleado.getDni());
			updatePs.setString(2, empleado.getNombre());
			updatePs.setString(3, empleado.getTelefono());
			updatePs.setInt(4, empleado.getId());
			filasAfectadas += updatePs.executeUpdate();
			updatePs.close();

			if (empleado instanceof TEmpleadoCompleto) {
				TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
				String updateCompletoSql = "UPDATE COMPLETO SET HORAS_EXTRA = ? WHERE ID = ?";
				PreparedStatement psCompleto = conexion.prepareStatement(updateCompletoSql);
				psCompleto.setInt(1, completo.getHorasExtra());
				psCompleto.setInt(2, completo.getId());
				filasAfectadas += psCompleto.executeUpdate();
				psCompleto.close();
			} else if (empleado instanceof TEmpleadoParcial) {
				TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
				String updateParcialSql = "UPDATE PARCIAL SET HORAS_SEMANALES = ? WHERE ID = ?";
				PreparedStatement psParcial = conexion.prepareStatement(updateParcialSql);
				psParcial.setInt(1, parcial.getHorasSemanales());
				psParcial.setInt(2, parcial.getId());
				filasAfectadas += psParcial.executeUpdate();
				psParcial.close();
			}
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

		return filasAfectadas;
	}

	@Override
	public int eliminar(int id) {
		int filasAfectadas = 0;
		Connection conexion = null;

		try {
			conexion = conectar();

			String sql = "UPDATE EMPLEADO SET ACTIVO = 0 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
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

		return filasAfectadas;
	}

	@Override
	public int eliminarFisicamente(int id) { // solo para el test
		int filasAfectadas = 0;
		Connection conexion = null;

		try {
			conexion = conectar();

			String sql = "DELETE FROM EMPLEADO WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
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

		return filasAfectadas;
	}

	@Override
	public Collection<TEmpleado> leerTodo() {
		List<TEmpleado> empleados = new ArrayList<TEmpleado>();
		Connection conexion = null;

		try {
			conexion = conectar();

			String sqlEmpleado = "SELECT ID, DNI, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO";
			PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado);
			ResultSet rsEmpleado = psEmpleado.executeQuery();

			while (rsEmpleado.next()) {
				int id = rsEmpleado.getInt("ID");
				String dni = rsEmpleado.getString("DNI");
				String nombre = rsEmpleado.getString("NOMBRE");
				String telefono = rsEmpleado.getString("TELEFONO");
				int activo = rsEmpleado.getInt("ACTIVO");

				String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ?";
				PreparedStatement psCompleto = conexion.prepareStatement(sqlCompleto);
				psCompleto.setInt(1, id);
				ResultSet rsCompleto = psCompleto.executeQuery();

				if (rsCompleto.next()) {
					int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
					TEmpleadoCompleto completo = new TEmpleadoCompleto(id, nombre, dni, telefono, activo, horasExtra);
					empleados.add(completo);
				} else {

					String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ?";
					PreparedStatement psParcial = conexion.prepareStatement(sqlParcial);
					psParcial.setInt(1, id);
					ResultSet rsParcial = psParcial.executeQuery();

					if (rsParcial.next()) {
						int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
						TEmpleadoParcial parcial = new TEmpleadoParcial(id, nombre, dni, telefono, activo,
								horasSemanales);
						empleados.add(parcial);
					}
					rsParcial.close();
					psParcial.close();
				}
				rsCompleto.close();
				psCompleto.close();
			}
			rsEmpleado.close();
			psEmpleado.close();
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

		return empleados;
	}

	@Override
	public TEmpleado leerPorDNI(String dni) {
		TEmpleado empleado = null;
		Connection conexion = null;

		try {
			conexion = conectar();

			String sqlEmpleado = "SELECT ID, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO WHERE DNI = ?";
			PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado);
			psEmpleado.setString(1, dni);
			ResultSet rsEmpleado = psEmpleado.executeQuery();

			if (rsEmpleado.next()) {
				int id = rsEmpleado.getInt("ID");
				String nombre = rsEmpleado.getString("NOMBRE");
				String telefono = rsEmpleado.getString("TELEFONO");
				int activo = rsEmpleado.getInt("ACTIVO");

				String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ?";
				PreparedStatement psCompleto = conexion.prepareStatement(sqlCompleto);
				psCompleto.setInt(1, id);
				ResultSet rsCompleto = psCompleto.executeQuery();

				if (rsCompleto.next()) {
					int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
					empleado = new TEmpleadoCompleto(id, nombre, dni, telefono, activo, horasExtra);
				} else {
					String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ?";
					PreparedStatement psParcial = conexion.prepareStatement(sqlParcial);
					psParcial.setInt(1, id);
					ResultSet rsParcial = psParcial.executeQuery();

					if (rsParcial.next()) {
						int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
						empleado = new TEmpleadoParcial(id, nombre, dni, telefono, activo, horasSemanales);
					}

					rsParcial.close();
					psParcial.close();
				}

				rsCompleto.close();
				psCompleto.close();
			}

			rsEmpleado.close();
			psEmpleado.close();
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

		return empleado;
	}

}
