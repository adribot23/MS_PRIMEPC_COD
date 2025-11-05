
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
			"INSERT INTO VENTA (metodo_Pago, precio, descuento, id_empleado, id_cliente, activo) VALUES (?, ?, ?, ?, ?, ?)",
			Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, venta.getMetodoPago() != null ? venta.getMetodoPago() : "Efectivo");
		ps.setDouble(2, venta.getPrecio() != null ? venta.getPrecio() : 0.0);
		ps.setDouble(3, venta.getDescuento() != null ? venta.getDescuento() : 0.0);
		ps.setInt(4, venta.getIdEmpleado());
		if (venta.getIdCliente() != null && venta.getIdCliente() > 0) {
			ps.setInt(5, venta.getIdCliente());
		} else {
			ps.setNull(5, java.sql.Types.INTEGER);
		}
		ps.setInt(6, 1);

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
					"SELECT id, metodo_Pago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA WHERE id_cliente = ? FOR UPDATE");
			ps.setInt(1, idCliente);

			ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TVenta venta = new TVenta();
			venta.setId(rs.getInt("id"));
			venta.setMetodoPago(rs.getString("metodo_Pago"));
			venta.setPrecio(rs.getDouble("precio"));
			venta.setDescuento(rs.getDouble("descuento"));
			venta.setIdEmpleado(rs.getInt("id_empleado"));
			venta.setIdCliente(idCliente);
			venta.setActivo(rs.getInt("activo"));

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
					"SELECT id, metodo_Pago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA WHERE id_empleado = ? FOR UPDATE");
			ps.setInt(1, idEmpleado);

			ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TVenta venta = new TVenta();
			venta.setId(rs.getInt("id"));
			venta.setMetodoPago(rs.getString("metodo_Pago"));
			venta.setPrecio(rs.getDouble("precio"));
			venta.setDescuento(rs.getDouble("descuento"));
			venta.setIdEmpleado(idEmpleado);
			Integer idCliente = (Integer) rs.getObject("id_cliente");
			venta.setIdCliente(idCliente);
			venta.setActivo(rs.getInt("activo"));

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
					"SELECT metodo_Pago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA WHERE id = ? FOR UPDATE");
			ps.setInt(1, id_venta);

			ResultSet rs = ps.executeQuery();

			TVenta venta = null;

		if (rs.next()) {
			venta = new TVenta();
			venta.setId(id_venta);
			venta.setMetodoPago(rs.getString("metodo_Pago"));
			venta.setPrecio(rs.getDouble("precio"));
			venta.setDescuento(rs.getDouble("descuento"));
			venta.setIdEmpleado(rs.getInt("id_empleado"));
			Integer idCliente = (Integer) rs.getObject("id_cliente");
			venta.setIdCliente(idCliente);
			venta.setActivo(rs.getInt("activo"));

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
				"UPDATE VENTA SET metodo_Pago = ?, precio = ?, descuento = ?, id_empleado = ?, id_cliente = ?, activo = ? WHERE id = ?");

		ps.setString(1, venta.getMetodoPago());
		ps.setDouble(2, venta.getPrecio());
		ps.setDouble(3, venta.getDescuento());
		ps.setInt(4, venta.getIdEmpleado());
		if (venta.getIdCliente() != null && venta.getIdCliente() > 0) {
			ps.setInt(5, venta.getIdCliente());
		} else {
			ps.setNull(5, java.sql.Types.INTEGER);
		}
		ps.setInt(6, venta.getActivo());
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

		PreparedStatement ps = con.prepareStatement("UPDATE VENTA SET activo = 0 WHERE id = ?");
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
					"SELECT id, metodo_Pago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA FOR UPDATE");

			ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TVenta venta = new TVenta();
			venta.setId(rs.getInt("id"));
			venta.setMetodoPago(rs.getString("metodo_Pago"));
			venta.setPrecio(rs.getDouble("precio"));
			venta.setDescuento(rs.getDouble("descuento"));
			venta.setIdEmpleado(rs.getInt("id_empleado"));
			Integer idCliente = (Integer) rs.getObject("id_cliente");
			venta.setIdCliente(idCliente);
			venta.setActivo(rs.getInt("activo"));

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