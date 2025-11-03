
package integracion.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Venta.TLineaVenta;

public class DAOLineaVentaImp implements DAOLineaVenta {
	
	public int create(TLineaVenta lineaVenta) {
		
		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO LINEAVENTA (id_producto, id_venta, num_unidades, precio_unidades, activo) VALUES (?, ?, ?, ?, ?)");
			ps.setInt(1, lineaVenta.get_producto());
			ps.setInt(2, lineaVenta.get_venta());
			ps.setInt(3, lineaVenta.get_num_unidades());
			ps.setDouble(4, lineaVenta.get_precio_unidades());
			ps.setInt(5, lineaVenta.get_activo());
			
			int filas = ps.executeUpdate();
			ps.close();
			
			return filas;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	public int delete(TLineaVenta tlineaVenta) {
		
		try {
			
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("UPDATE LINEAVENTA SET activo = 0 WHERE id_producto = ? AND id_venta = ?");
			ps.setInt(1, tlineaVenta.get_producto());
			ps.setInt(2, tlineaVenta.get_venta());
			
			int filas = ps.executeUpdate();
			ps.close();
			
			return filas;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	public TLineaVenta read(TLineaVenta tlineaVenta) {
		
		try {
			
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("SELECT id_producto, id_venta, num_unidades, precio_unidades, activo FROM LINEAVENTA WHERE id_producto = ? AND id_venta = ? AND activo = 1");
			ps.setInt(1, tlineaVenta.get_producto());
			ps.setInt(2, tlineaVenta.get_venta());
			
			java.sql.ResultSet rs = ps.executeQuery();
			
			TLineaVenta tlineaventa = null;
			
			if (rs.next()) {
				
				tlineaventa = new TLineaVenta();
				tlineaventa.set_producto(rs.getInt("id_producto"));
				tlineaventa.set_venta(rs.getInt("id_venta"));
				tlineaventa.set_num_unidades(rs.getInt("num_unidades"));
				tlineaventa.set_precio_unidades(rs.getDouble("precio_unidades"));
				tlineaventa.set_activo(rs.getInt("activo"));
			}
			
			rs.close();
			ps.close();
			
			return tlineaventa;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Set<TLineaVenta> read_all(int id_venta) {
		
		Set<TLineaVenta> lineasVenta = new LinkedHashSet<TLineaVenta>();
		
		try {
			
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("SELECT id_producto, id_venta, num_unidades, precio_unidades, activo FROM LINEAVENTA WHERE id_venta = ? FOR UPDATE");
			ps.setInt(1, id_venta);
			
			java.sql.ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				TLineaVenta tlineaventa = new TLineaVenta();
				tlineaventa.set_producto(rs.getInt("id_producto"));
				tlineaventa.set_venta(rs.getInt("id_venta"));
				tlineaventa.set_num_unidades(rs.getInt("num_unidades"));
				tlineaventa.set_precio_unidades(rs.getDouble("precio_unidades"));
				tlineaventa.set_activo(rs.getInt("activo"));
				
				lineasVenta.add(tlineaventa);
			}
			
			rs.close();
			ps.close();
			
			return lineasVenta;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public int update(TLineaVenta tlineaVenta) {
		
		try {
			
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("UPDATE LINEAVENTA SET num_unidades = ?, precio_unidades = ?, activo = ? WHERE id_producto = ? AND id_venta = ?");
			ps.setInt(1, tlineaVenta.get_num_unidades());
			ps.setDouble(2, tlineaVenta.get_precio_unidades());
			ps.setInt(3, tlineaVenta.get_activo());
			ps.setInt(4, tlineaVenta.get_producto());
			ps.setInt(5, tlineaVenta.get_venta());
			
			int filas = ps.executeUpdate();
			ps.close();
			
			return filas;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
		}

	public TLineaVenta read_all_by_producto(int id_producto) {
		
		try {
			
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection con = (Connection)tr.getResource();
			
			PreparedStatement ps = con.prepareStatement("SELECT id_producto, id_venta, num_unidades, precio_unidades, activo FROM LINEAVENTA WHERE id_producto = ? FOR UPDATE");
			ps.setInt(1, id_producto);
			
			java.sql.ResultSet rs = ps.executeQuery();
			
			TLineaVenta tlineaventa = null;
			
			if (rs.next()) {
				
				tlineaventa = new TLineaVenta();
				tlineaventa.set_producto(rs.getInt("id_producto"));
				tlineaventa.set_venta(rs.getInt("id_venta"));
				tlineaventa.set_num_unidades(rs.getInt("num_unidades"));
				tlineaventa.set_precio_unidades(rs.getDouble("precio_unidades"));
				tlineaventa.set_activo(rs.getInt("activo"));
			}
			
			rs.close();
			ps.close();
			
			return tlineaventa;
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}