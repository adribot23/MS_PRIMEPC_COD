package integracion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TProducto;

public class DAOProductoImp implements DAOProducto {

	@Override
	public int create(TProducto producto) {
		int id = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO PRODUCTO (PRECIO, MODELO, NUM_UNIDADES, MARCA, ID_ALMACEN, ACTIVO) VALUES (?, ?, ?, ?, ?, 1)",
					Statement.RETURN_GENERATED_KEYS);
			s.setDouble(1, producto.getPrecio());
			s.setString(2, producto.getModelo());
			s.setInt(3, producto.getUnidades());
			s.setString(4, producto.getMarca());
			s.setInt(5,producto.getIdAlmacen());
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();

			if (r.next()) {
				id = r.getInt(1);
			}
			s.close();
			r.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public TProducto read(int id) {
		TProducto tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO WHERE ID = ? FOR UPDATE");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				tp = cargarProducto(r);
			}
			s.close();
			r.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tp;
	}

	@Override
	public int update(TProducto producto) {
		int exito = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement(
					"UPDATE PRODUCTO SET PRECIO = ?, MODELO = ?, NUM_UNIDADES = ?, MARCA = ?,ID_ALMACEN=NULL, ACTIVO = 1 WHERE ID = ?");
			s.setDouble(1, producto.getPrecio());
			s.setString(2, producto.getModelo());
			s.setInt(3, producto.getUnidades());
			s.setString(4, producto.getMarca());
			s.setInt(5, producto.getId());
			exito = s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		if (exito == 0)
			return -1;
		else
			return exito;
	}

	@Override
	public int delete(int id) {
	    int exito = -1;
	    try {
	        TManager tManager = TManager.getInstance();
	        Transaction t = tManager.getTransaction();
	        Connection c = (Connection) t.getResource();

	        // Desvincular el producto del almacén y marcarlo como inactivo
	        PreparedStatement s = c.prepareStatement(
	            "UPDATE PRODUCTO SET ACTIVO = 0, ID_ALMACEN = NULL WHERE ID = ?"
	        );
	        s.setInt(1, id);

	        exito = s.executeUpdate();
	        s.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	    }
		if (exito == 0)
			return -1;
		else
			return exito;
	}

	@Override
	public TProducto read_by_modelo(String modelo) {
		TProducto tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO WHERE MODELO = ? FOR UPDATE");
			s.setString(1, modelo);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				tp = cargarProducto(r);
			}
			s.close();
			r.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tp;
	}

	@Override
	public Set<TProducto> read_by_marca_and_modelo(String modelo, String marca) {
		Set<TProducto> tpr = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM PRODUCTO WHERE MODELO = ? AND MARCA = ? FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProducto tp = null;
				tp = cargarProducto(r);
				if (tp != null)
					tpr.add(tp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tpr;
	}

	@Override
	public Set<TProducto> read_all() {
		Set<TProducto> tpr = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProducto tp = null;
				tp = cargarProducto(r);
				if (tp != null)
					tpr.add(tp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tpr;
	}

	@Override
	public Set<TProducto> read_by_almacen(int idAlmacen) {
		Set<TProducto> tpr = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO WHERE ID_ALMACEN = ? FOR UPDATE");
			s.setInt(1, idAlmacen);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProducto tp = null;
				tp = cargarProducto(r);
				if (tp != null)
					tpr.add(tp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tpr;
	}

	private TProducto cargarProducto(ResultSet rs) throws SQLException {
		TProducto producto = new TProducto();
		producto.setId(rs.getInt("ID"));
		producto.setPrecio(rs.getDouble("PRECIO"));
		producto.setModelo(rs.getString("MODELO"));
		producto.setUnidades(rs.getInt("NUM_UNIDADES"));
		producto.setMarca(rs.getString("MARCA"));
		producto.setIdAlmacen(rs.getInt("ID_ALMACEN"));
		producto.setActivo(rs.getInt("ACTIVO"));
		return producto;
	}

}