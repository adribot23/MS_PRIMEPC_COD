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

import negocio.transfers.TProducto;

public class DAOProductoImp implements DAOProducto {

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	}

	@Override
	public int crear(TProducto producto) {
		int id = -1;
		String insertSql = "INSERT INTO PRODUCTO (PRECIO, MODELO, NUM_UNIDADES, MARCA, ID_ALMACEN, ID_PROVEEDOR, ACTIVO) VALUES (?, ?, ?, ?, -1, -1, 1)";

		try (Connection conexion = conectar();
				PreparedStatement insertPs = conexion.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

			insertPs.setDouble(1, producto.getPrecio());
			insertPs.setString(2, producto.getModelo());
			insertPs.setInt(3, producto.getUnidades());
			insertPs.setString(4, producto.getMarca());

			insertPs.executeUpdate();

			try (ResultSet rs = insertPs.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public TProducto leer(int id) {
		TProducto producto = null;

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				producto = cargarProducto(rs);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return producto;
	}

	@Override
	public int actualizar(TProducto producto) {
		int filasAfectadas = 0;

		try (Connection conexion = conectar()) {
			String sql = "UPDATE PRODUCTO SET PRECIO = ?, MODELO = ?, NUM_UNIDADES = ?, MARCA = ?,ID_ALMACEN=?, ID_PROVEEDOR=?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setDouble(1, producto.getPrecio());
			ps.setString(2, producto.getModelo());
			ps.setInt(3, producto.getUnidades());
			ps.setString(4, producto.getMarca());
			ps.setInt(5, producto.getIdAlmacen());
			ps.setInt(6, producto.getIdProveedor());
			ps.setInt(7, producto.getId());

			filasAfectadas = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filasAfectadas;
	}

	@Override
	public int eliminar(int id) {
		int filasAfectadas = 0;

		try (Connection conexion = conectar()) {
			String sql = "UPDATE PRODUCTO SET ACTIVO = 0 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);

			filasAfectadas = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filasAfectadas;
	}

	@Override
	public int eliminarFisicamente(int id) { // solo para el test
		int filasAfectadas = 0;

		try (Connection conexion = conectar()) {
			String sql = "DELETE FROM PRODUCTO WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);

			filasAfectadas = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filasAfectadas;
	}

	@Override
	public Collection<TProducto> leerTodo() {
		List<TProducto> productos = new ArrayList<>();

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productos.add(cargarProducto(rs));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}

	@Override
	public TProducto leerPorModelo(String modelo) {
		TProducto producto = null;

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO WHERE MODELO = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, modelo);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				producto = cargarProducto(rs);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return producto;
	}

	@Override
	public TProducto leerPorMarcaYModelo(String modelo, String marca) {
		TProducto producto = null;

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO WHERE MODELO = ? AND MARCA = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, modelo);
			ps.setString(2, marca);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				producto = cargarProducto(rs);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return producto;
	}

	@Override
	public Collection<TProducto> leerPorAlmacen(int idAlmacen) {
		List<TProducto> productos = new ArrayList<>();

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO WHERE ID_ALMACEN = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idAlmacen);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productos.add(cargarProducto(rs));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}

	@Override
	public Collection<TProducto> leerPorProveedor(int idProveedor) {
		List<TProducto> productos = new ArrayList<>();

		try (Connection conexion = conectar()) {
			String sql = "SELECT * FROM PRODUCTO WHERE ID_PROVEEDOR = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idProveedor);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productos.add(cargarProducto(rs));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}

	private TProducto cargarProducto(ResultSet rs) throws SQLException {
		TProducto producto = new TProducto();
		producto.setId(rs.getInt("ID"));
		producto.setPrecio(rs.getDouble("PRECIO"));
		producto.setModelo(rs.getString("MODELO"));
		producto.setUnidades(rs.getInt("NUM_UNIDADES"));
		producto.setMarca(rs.getString("MARCA"));
		producto.setIdAlmacen(rs.getInt("ID_ALMACEN"));
		producto.setIdProveedor(rs.getInt("ID_PROVEEDOR"));
		producto.setActivo(rs.getInt("ACTIVO"));
		return producto;
	}
}
