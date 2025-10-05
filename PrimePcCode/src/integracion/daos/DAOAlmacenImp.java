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

import negocio.transfers.TAlmacen;

public class DAOAlmacenImp implements DAOAlmacen {

	private Connection conexion;

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	}

	@Override
	public int crear(TAlmacen almacen) {
		int id = -1;
		try {
			conexion = conectar();
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
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public TAlmacen leer(int id) {
		TAlmacen almacen = null;
		try {
			conexion = conectar();
			String sql = "SELECT * FROM almacen WHERE id = ?";
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
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacen;
	}

	@Override
	public int actualizar(TAlmacen almacen) {
		int filasAfectadas = 0;

		try {
			conexion = conectar();
			String sql = "UPDATE almacen SET CAPACIDAD_MAX = ?, NOMBRE = ?,OCUPACION = ?,ACTIVO=1 WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, almacen.getCapacidadMaxima());
			ps.setString(2, almacen.getNombre());
			ps.setInt(3, almacen.getOcupacion());
			ps.setInt(4, almacen.getId());

			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	@Override
	public int eliminar(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "UPDATE ALMACEN SET ACTIVO = 0 WHERE ID = ?";
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

	@Override
	public int eliminarFisicamente(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "DELETE FROM ALMACEN WHERE ID = ?";
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

	@Override
	public TAlmacen leerPorNombre(String nombre) {
		TAlmacen almacen = null;
		try {
			conexion = conectar();
			String sql = "SELECT * FROM almacen WHERE nombre = ?";
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
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacen;
	}

	@Override
	public Collection<TAlmacen> leerTodo() {
		List<TAlmacen> almacenes = new ArrayList<>();
		try {
			conexion = conectar();
			String sql = "SELECT * FROM almacen";
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
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return almacenes;
	}

}