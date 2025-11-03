
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
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO ventas (metodoPago, precio, descuento, id_empleado, id_cliente, activo) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			
			ps.setString(1, venta.getMetodoPago());	
			ps.setDouble(2, venta.getPrecio());
			ps.setDouble(3, venta.getDescuento());
			ps.setInt(4, venta.getIdEmpleado());
			ps.setInt(5, venta.getIdCliente());
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
			
			PreparedStatement ps = con.prepareStatement("SELECT id_venta, metodoPago, precio, descuento, id_empleado, activo FROM VENTA WHERE id_cliente = ? FOR UPDATE");
			ps.setInt(1, idCliente);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {	
				TVenta venta = new TVenta();
				venta.setMetodoPago(rs.getString("metodoPago"));
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
			
			PreparedStatement ps = con.prepareStatement("SELECT id_venta, metodoPago, precio, descuento, id_empleado, activo FROM VENTA WHERE id_empleado = ? FOR UPDATE");
			ps.setInt(1, idEmpleado);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {	
				TVenta venta = new TVenta();
				venta.setMetodoPago(rs.getString("metodoPago"));
				venta.setPrecio(rs.getDouble("precio"));
				venta.setDescuento(rs.getDouble("descuento"));
				venta.setIdEmpleado(idEmpleado);
				venta.setIdCliente(rs.getInt("id_cliente"));
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
			
			PreparedStatement ps = con.prepareStatement("SELECT metodoPago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA WHERE id_venta = ? FOR UPDATE");
			ps.setInt(1, id_venta);
			
			ResultSet rs = ps.executeQuery();
			
			TVenta venta = null;
			
			if(rs.next()) {	
				venta = new TVenta();
				venta.setMetodoPago(rs.getString("metodoPago"));
				venta.setPrecio(rs.getDouble("precio"));
				venta.setDescuento(rs.getDouble("descuento"));
				venta.setIdEmpleado(rs.getInt("id_empleado"));
				venta.setIdCliente(rs.getInt("id_cliente"));
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
			
			PreparedStatement ps = con.prepareStatement("UPDATE VENTA SET metodoPago = ?, precio = ?, descuento = ?, id_empleado = ?, id_cliente = ?, activo = ? WHERE id_venta = ?");
			
			ps.setString(1, venta.getMetodoPago());	
			ps.setDouble(2, venta.getPrecio());
			ps.setDouble(3, venta.getDescuento());
			ps.setInt(4, venta.getIdEmpleado());
			ps.setInt(5, venta.getIdCliente());
			ps.setInt(6, venta.getActivo());
			
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
			
			PreparedStatement ps = con.prepareStatement("UPDATE FROM VENTA WHERE id_venta = ? SET activo = 0");
			ps.setInt(1, id_venta);
			ps.setInt(2, 0);
			
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
			
			PreparedStatement ps = con.prepareStatement("SELECT id_venta, metodoPago, precio, descuento, id_empleado, id_cliente, activo FROM VENTA FOR UPDATE");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {	
				TVenta venta = new TVenta();
				venta.setMetodoPago(rs.getString("metodoPago"));
				venta.setPrecio(rs.getDouble("precio"));
				venta.setDescuento(rs.getDouble("descuento"));
				venta.setIdEmpleado(rs.getInt("id_empleado"));
				venta.setIdCliente(rs.getInt("id_cliente"));
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
	
}