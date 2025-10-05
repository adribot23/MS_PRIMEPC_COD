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

import negocio.transfers.TProveedor;

public class DAOProveedorImp implements DAOProveedor {

	private Connection conexion;

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	}

	@Override
	public int crear(TProveedor proveedor) {
		int id = -1;
		try {
			conexion = conectar();
			String sql = "INSERT INTO PROVEEDOR (NOMBRE, ACTIVO) VALUES (?, 1)";
			PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, proveedor.getNombre());

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
	public TProveedor leer(int id) {
		TProveedor proveedor = null;
		try {
			conexion = conectar();
			String sql = "SELECT * FROM PROVEEDOR WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				proveedor = new TProveedor();
				int idProveedor = rs.getInt("ID");
				proveedor.setId(idProveedor);
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));

			}

			rs.close();
			ps.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedor;
	}

	@Override
	public int actualizar(TProveedor proveedor) {
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "UPDATE PROVEEDOR SET NOMBRE = ?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, proveedor.getNombre());
			ps.setInt(2, proveedor.getId());

			filasAfectadas = ps.executeUpdate();

			ps.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	@Override
	public int eliminar(int id) {
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "UPDATE PROVEEDOR SET ACTIVO = 0 WHERE ID = ?";
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
		Connection conexion = null;

		try {
			conexion = conectar();
			String sql = "DELETE FROM PROVEEDOR WHERE ID = ?";
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
	public TProveedor leerPorNombre(String nombre) {
		TProveedor proveedor = null;
		try {
			conexion = conectar();
			String sql = "SELECT * FROM PROVEEDOR WHERE NOMBRE = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				proveedor = new TProveedor();
				proveedor.setId(rs.getInt("ID"));
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));
			}

			rs.close();
			ps.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedor;
	}

	@Override
	public Collection<TProveedor> leerTodo() {
		List<TProveedor> proveedores = new ArrayList<>();

		try {
			conexion = conectar();

			String sql = "SELECT * FROM PROVEEDOR";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TProveedor proveedor = new TProveedor();
				proveedor.setId(rs.getInt("ID"));
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));
				proveedores.add(proveedor);
			}

			rs.close();
			ps.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedores;
	}

}
