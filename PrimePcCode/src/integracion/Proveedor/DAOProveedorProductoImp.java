package integracion.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Proveedor.TProveedorProducto;

public class DAOProveedorProductoImp implements DAOProveedorProducto {
	public int create(TProveedorProducto tProveedorProducto) {
		int id = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO PRODUCTO_PROVEEDOR(ID_PRODUCTO,ID_PROVEEDOR) VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, tProveedorProducto.getIdProducto());
			s.setInt(2, tProveedorProducto.getIdProveedor());
			s.executeUpdate();
			ResultSet r = s.getGeneratedKeys();
			if (r.next()) {
				id = r.getInt(1);
			}
			s.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public TProveedorProducto read(int id_proveedor, int id_producto) {
		TProveedorProducto vinculacion = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO = ? AND ID_PROVEEDOR = ? FOR UPDATE");
			s.setInt(1, id_producto);
			s.setInt(2, id_proveedor);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				vinculacion = new TProveedorProducto();
				vinculacion.setIdProducto(r.getInt("ID_PRODUCTO"));
				vinculacion.setIdProveedor(r.getInt("ID_PROVEEDOR"));
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculacion;
	}

	public int update(TProveedorProducto tProveedorProducto) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return 0;
		// end-user-code
	}

	public int delete(int id_proveedor, int id_producto) {
		int exito = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("DELETE PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO = ? AND ID_PROVEEDOR = ?");
			s.setInt(1, id_producto);
			s.setInt(2, id_proveedor);
			exito = s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return exito;
	}

	public Set<TProveedorProducto> read_all() {
		Set<TProveedorProducto> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProveedorProducto vinculacion = new TProveedorProducto();
				int idProducto = r.getInt("ID_PRODUCTO");
				int idProveedor = r.getInt("ID_PROVEEDOR");
				;
				vinculacion.setIdProducto(idProducto);
				vinculacion.setIdProveedor(idProveedor);
				vinculaciones.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculaciones;
	}

	public Set<TProveedorProducto> read_all_by_producto(int id_producto) {
		Set<TProveedorProducto> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO=? FOR UPDATE");
			s.setInt(1, id_producto);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProveedorProducto vinculacion = new TProveedorProducto();
				int id_proveedor = r.getInt("ID_PROVEEDOR");
				vinculacion.setIdProducto(id_producto);
				vinculacion.setIdProveedor(id_proveedor);
				vinculaciones.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculaciones;
	}

	public Set<TProveedorProducto> read_all_by_proveedor(int id_proveedor) {
		Set<TProveedorProducto> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PROVEEDOR=? FOR UPDATE");
			s.setInt(1, id_proveedor);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProveedorProducto vinculacion = new TProveedorProducto();
				int idProducto = r.getInt("ID_PRODUCTO");
				vinculacion.setIdProducto(idProducto);
				vinculacion.setIdProveedor(id_proveedor);
				vinculaciones.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculaciones;
	}
}