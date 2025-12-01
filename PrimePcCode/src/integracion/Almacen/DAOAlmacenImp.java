/**
 * 
 */
package integracion.Almacen;

import negocio.Almacen.TAlmacen;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DAOAlmacenImp implements DAOAlmacen {

	private Connection conexion;

	public Integer create(TAlmacen almacen) {
		int id = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();

			String sql = "INSERT INTO ALMACEN (CAPACIDAD_MAX, OCUPACION, NOMBRE, ACTIVO) VALUES (?, ?, ?, 1)";
			PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, almacen.getCapacidadMaxima());
			ps.setInt(2, almacen.getOcupacion());
			ps.setString(3, almacen.getNombre());

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public TAlmacen read(Integer id) {
		TAlmacen almacen = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();

			String sql = "SELECT * FROM almacen WHERE id = ? FOR UPDATE";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				almacen = new TAlmacen();
				almacen.setId(rs.getInt("ID"));
				almacen.setNombre(rs.getString("NOMBRE"));
				almacen.setCapacidadMaxima(rs.getInt("CAPACIDAD_MAX"));
				almacen.setOcupacion(rs.getInt("OCUPACION"));
				almacen.setActivo(rs.getInt("ACTIVO"));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacen;
	}

	public Integer update(TAlmacen almacen) {
		int filasAfectadas = 0;

		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();
			String sql = "UPDATE almacen SET CAPACIDAD_MAX = ?, NOMBRE = ?,OCUPACION = ?,ACTIVO=1 WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, almacen.getCapacidadMaxima());
			ps.setString(2, almacen.getNombre());
			ps.setInt(3, almacen.getOcupacion());
			ps.setInt(4, almacen.getId());

			filasAfectadas = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public Integer delete(Integer id) {
		int filasAfectadas = 0;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();
			String sql = "UPDATE ALMACEN SET ACTIVO = 0 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public TAlmacen read_by_name(String nombre) {
		TAlmacen almacen = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();
			String sql = "SELECT * FROM almacen WHERE nombre = ? FOR UPDATE";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				almacen = new TAlmacen();
				almacen.setId(rs.getInt("ID"));
				almacen.setNombre(rs.getString("NOMBRE"));
				almacen.setCapacidadMaxima(rs.getInt("CAPACIDAD_MAX"));
				almacen.setOcupacion(rs.getInt("OCUPACION"));
				almacen.setActivo(rs.getInt("ACTIVO"));
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacen;
	}

	public Set<TAlmacen> read_all() {
		Set<TAlmacen> almacenes = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			conexion = (Connection) t.getResource();
			String sql = "SELECT * FROM almacen FOR UPDATE";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				TAlmacen almacen = new TAlmacen();
				almacen.setId(rs.getInt("ID"));
				almacen.setNombre(rs.getString("NOMBRE"));
				almacen.setCapacidadMaxima(rs.getInt("CAPACIDAD_MAX"));
				almacen.setOcupacion(rs.getInt("OCUPACION"));
				almacen.setActivo(rs.getInt("ACTIVO"));
				almacenes.add(almacen);

			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacenes;
	}

	/*
	 * @Override public int eliminarFisicamente(int id) { // solo para el test int
	 * filasAfectadas = 0; try { conexion = conectar(); String sql =
	 * "DELETE FROM ALMACEN WHERE ID = ?"; PreparedStatement ps =
	 * conexion.prepareStatement(sql); ps.setInt(1, id); filasAfectadas =
	 * ps.executeUpdate(); ps.close(); conexion.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } return filasAfectadas; }
	 */

}