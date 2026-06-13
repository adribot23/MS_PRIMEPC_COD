
package integracion.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Venta.TVenta;

public class DAOVentaImp implements DAOVenta {

	public int create(TVenta venta) {

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO VENTA (METODO_PAGO, PRECIO, ID_EMPLEADO, ID_CLIENTE, ACTIVO, DESCUENTO) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, venta.getMetodoPago() != null ? venta.getMetodoPago() : "Efectivo");
			ps.setDouble(2, venta.getPrecio() != null ? venta.getPrecio() : 0.0);
			ps.setInt(3, venta.getIdEmpleado());
			if (venta.getIdCliente() != null && venta.getIdCliente() > 0) {
				ps.setInt(4, venta.getIdCliente());
			} else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			ps.setInt(5, 1);
			ps.setDouble(6, venta.getDescuento() != null ? venta.getDescuento() : 0.0);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			int idVenta = rs.getInt(1);

			rs.close();
			ps.close();

			return idVenta;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Set<TVenta> read_by_cliente(int idCliente) {

		Set<TVenta> ventas = new LinkedHashSet<>();

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"SELECT ID, METODO_PAGO, PRECIO, ID_EMPLEADO, ID_CLIENTE, ACTIVO, DESCUENTO FROM VENTA WHERE ID_CLIENTE = ? FOR UPDATE");
			ps.setInt(1, idCliente);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				venta.setIdCliente(idCliente);
				venta.setActivo(rs.getInt("ACTIVO"));

				ventas.add(venta);
			}

			rs.close();
			ps.close();

			return ventas;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Set<TVenta> read_by_empleado(int idEmpleado) {
		Set<TVenta> ventas = new LinkedHashSet<>();

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"SELECT ID, METODO_PAGO, PRECIO, ID_EMPLEADO, ID_CLIENTE, ACTIVO, DESCUENTO FROM VENTA WHERE ID_EMPLEADO = ? FOR UPDATE");
			ps.setInt(1, idEmpleado);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(idEmpleado);
				Integer idCliente = (Integer) rs.getObject("ID_CLIENTE");
				venta.setIdCliente(idCliente);
				venta.setActivo(rs.getInt("ACTIVO"));

				ventas.add(venta);
			}

			rs.close();
			ps.close();

			return ventas;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public TVenta read(int id_venta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"SELECT ID, METODO_PAGO, PRECIO, ID_EMPLEADO, ID_CLIENTE, ACTIVO, DESCUENTO FROM VENTA WHERE ID = ? FOR UPDATE");
			ps.setInt(1, id_venta);

			ResultSet rs = ps.executeQuery();

			TVenta venta = null;

			if (rs.next()) {
				venta = new TVenta();
				venta.setId(id_venta);
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				Integer idCliente = (Integer) rs.getObject("ID_CLIENTE");
				venta.setIdCliente(idCliente);
				venta.setActivo(rs.getInt("ACTIVO"));

				rs.close();
				ps.close();

			}

			rs.close();
			ps.close();

			return venta;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int update(TVenta venta) {

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"UPDATE VENTA SET METODO_PAGO = ?, PRECIO = ?, ID_EMPLEADO = ?, ID_CLIENTE = ?, ACTIVO = ?, DESCUENTO = ? WHERE ID = ?");

			ps.setString(1, venta.getMetodoPago());
			ps.setDouble(2, venta.getPrecio());

			ps.setInt(3, venta.getIdEmpleado());
			if (venta.getIdCliente() != null && venta.getIdCliente() > 0) {
				ps.setInt(4, venta.getIdCliente());
			} else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			ps.setInt(5, venta.getActivo());
			ps.setDouble(6, venta.getDescuento());
			ps.setInt(7, venta.getId());

			int filas = ps.executeUpdate();

			ps.close();

			return filas;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int delete(int id_venta) {

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement("UPDATE VENTA SET ACTIVO = 0 WHERE ID = ?");
			ps.setInt(1, id_venta);

			int filas = ps.executeUpdate();

			ps.close();

			return filas;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public Set<TVenta> read_all() {

		Set<TVenta> ventas = new LinkedHashSet<>();

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection) tr.getResource();

			PreparedStatement ps = con.prepareStatement(
					"SELECT ID, METODO_PAGO, PRECIO, ID_EMPLEADO, ID_CLIENTE, ACTIVO, DESCUENTO FROM VENTA FOR UPDATE");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TVenta venta = new TVenta();
				venta.setId(rs.getInt("ID"));
				venta.setMetodoPago(rs.getString("METODO_PAGO"));
				venta.setPrecio(rs.getDouble("PRECIO"));
				venta.setDescuento(rs.getDouble("DESCUENTO"));
				venta.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
				Integer idCliente = (Integer) rs.getObject("ID_CLIENTE");
				venta.setIdCliente(idCliente);
				venta.setActivo(rs.getInt("ACTIVO"));

				ventas.add(venta);
			}

			rs.close();
			ps.close();

			return ventas;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ventas;
	}

}