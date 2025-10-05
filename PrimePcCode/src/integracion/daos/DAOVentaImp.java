package integracion.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negocio.transfers.TVenta;

public class DAOVentaImp implements DAOVenta {

	private Connection conexion;

	private Connection conectar() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	}

	@Override
	public int crear(TVenta venta) {
		int id = -1;
		try {
			conexion = conectar();
			String sql = "INSERT INTO VENTA (METODO_PAGO, PRECIO, DESCUENTO, ID_EMPLEADO, ID_CLIENTE, ACTIVO) VALUES (?, ?, ?, ?, ?, 1)";
			PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, venta.getMetodoPago());
			ps.setDouble(2, venta.getPrecio());
			ps.setDouble(3, venta.getDescuento());
			ps.setInt(4, venta.getIdEmpleado());
			ps.setInt(5, venta.getIdCliente());

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
	public TVenta leer(int id) {
		TVenta venta = null;
		try {
			conexion = conectar();
			String sql = "SELECT * FROM VENTA WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				venta.setIdCliente(rs.getInt("ID_CLIENTE"));
				venta.setActivo(rs.getInt("ACTIVO"));

				Map<Integer, Integer> productos = new HashMap<>();
				String sqlProductos = "SELECT PRODUCTO_ID, CANTIDAD FROM producto_venta WHERE VENTA_ID = ?";
				PreparedStatement psProductos = conexion.prepareStatement(sqlProductos);
				psProductos.setInt(1, venta.getId());
				ResultSet rsProductos = psProductos.executeQuery();
				while (rsProductos.next()) {
					productos.put(rsProductos.getInt("PRODUCTO_ID"), rsProductos.getInt("CANTIDAD"));
				}
				venta.setProductos(productos);
				rsProductos.close();
				psProductos.close();
			}

			rs.close();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return venta;
	}

	@Override
	public int actualizar(TVenta venta) {
		int filasAfectadas = 0;
		try {
			conexion = conectar();
			String sql = "UPDATE VENTA SET METODO_PAGO = ?, PRECIO = ?, DESCUENTO = ?, ID_EMPLEADO = ?, ID_CLIENTE = ?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, venta.getMetodoPago());
			ps.setDouble(2, venta.getPrecio());
			ps.setDouble(3, venta.getDescuento());
			ps.setInt(4, venta.getIdEmpleado());
			ps.setInt(5, venta.getIdCliente());
			ps.setInt(6, venta.getId());

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
			String sql = "UPDATE VENTA SET ACTIVO = 0 WHERE ID = ?";
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
			String sql = "DELETE FROM VENTA WHERE ID = ?";
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
	public Collection<TVenta> leerTodo() {
		List<TVenta> ventas = new ArrayList<>();
		try {
			conexion = conectar();
			String sql = "SELECT * FROM VENTA";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				venta.setIdCliente(rs.getInt("ID_CLIENTE"));
				venta.setActivo(rs.getInt("ACTIVO"));

				// Leer los productos asociados a la venta
				Map<Integer, Integer> productos = new HashMap<>();
				String sqlProductos = "SELECT PRODUCTO_ID, CANTIDAD FROM producto_venta WHERE VENTA_ID = ?";
				PreparedStatement psProductos = conexion.prepareStatement(sqlProductos);
				psProductos.setInt(1, venta.getId());
				ResultSet rsProductos = psProductos.executeQuery();
				while (rsProductos.next()) {
					productos.put(rsProductos.getInt("PRODUCTO_ID"), rsProductos.getInt("CANTIDAD"));
				}
				venta.setProductos(productos); // Asumiendo que TVenta tiene un
												// setter para los productos

				ventas.add(venta);

				rsProductos.close();
				psProductos.close();
			}

			rs.close();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ventas;
	}

	@Override
	public int vincularProducto(int idVenta, int idProducto, int cantidad, double importe) {
		int filasAfectadas = 0;
		String sql = "INSERT INTO producto_venta (PRODUCTO_ID, VENTA_ID, CANTIDAD, IMPORTE) VALUES (?, ?, ?, ?)";

		try {
			conexion = conectar();

			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idProducto);
			pstmt.setInt(2, idVenta);
			pstmt.setInt(3, cantidad);
			pstmt.setDouble(4, importe);

			filasAfectadas = pstmt.executeUpdate();

			pstmt.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filasAfectadas;
	}

	@Override
	public Collection<TVenta> leerPorEmpleado(int idEmpleado) {
		List<TVenta> ventas = new ArrayList<>();
		try {
			conexion = conectar();
			String sql = "SELECT * FROM VENTA WHERE ID_EMPLEADO = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idEmpleado);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				venta.setIdCliente(rs.getInt("ID_CLIENTE"));
				venta.setActivo(rs.getInt("ACTIVO"));

				Map<Integer, Integer> productos = new HashMap<>();
				String sqlProductos = "SELECT PRODUCTO_ID, CANTIDAD FROM producto_venta WHERE VENTA_ID = ?";
				PreparedStatement psProductos = conexion.prepareStatement(sqlProductos);
				psProductos.setInt(1, venta.getId());
				ResultSet rsProductos = psProductos.executeQuery();
				while (rsProductos.next()) {
					productos.put(rsProductos.getInt("PRODUCTO_ID"), rsProductos.getInt("CANTIDAD"));
				}
				venta.setProductos(productos);

				ventas.add(venta);

				rsProductos.close();
				psProductos.close();
			}

			rs.close();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ventas;
	}

	@Override
	public Collection<TVenta> leerPorCliente(int idCliente) {
		List<TVenta> ventas = new ArrayList<>();
		try {
			conexion = conectar();
			String sql = "SELECT * FROM VENTA WHERE ID_CLIENTE = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idCliente);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				venta.setIdCliente(rs.getInt("ID_CLIENTE"));
				venta.setActivo(rs.getInt("ACTIVO"));

				Map<Integer, Integer> productos = new HashMap<>();
				String sqlProductos = "SELECT PRODUCTO_ID, CANTIDAD FROM producto_venta WHERE VENTA_ID = ?";
				PreparedStatement psProductos = conexion.prepareStatement(sqlProductos);
				psProductos.setInt(1, venta.getId());
				ResultSet rsProductos = psProductos.executeQuery();
				while (rsProductos.next()) {
					productos.put(rsProductos.getInt("PRODUCTO_ID"), rsProductos.getInt("CANTIDAD"));
				}
				venta.setProductos(productos);

				ventas.add(venta);

				rsProductos.close();
				psProductos.close();
			}

			rs.close();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ventas;

	}

}
