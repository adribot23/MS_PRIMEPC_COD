package integracion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TVinculacionProdProv;

public class DAOVinculacionProdProvImp implements DAOVinculacionProdProv{

	@Override
	public int create(TVinculacionProdProv producto) {
		int id = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO PRODUCTO_PROVEEDOR(ID_PRODUCTO,ID_PROVEEDOR) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, producto.getIdProducto());
			s.setInt(2, producto.getIdProveedor());
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

	@Override
	public TVinculacionProdProv read(int idProducto, int idProveedor) {
		TVinculacionProdProv vinculacion = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO = ? AND ID_PROVEEDOR = ? FOR UPDATE");
			s.setInt(1, idProducto);
			s.setInt(2, idProveedor);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				vinculacion = new TVinculacionProdProv();
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

	@Override
	public int delete(int idProducto, int idProveedor) {
		int exito = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"DELETE PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO = ? AND ID_PROVEEDOR = ?");
			s.setInt(1, idProducto);
			s.setInt(2, idProveedor);
			exito = s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return exito;
	}

	@Override
	public Set<TVinculacionProdProv> read_all() {
		Set<TVinculacionProdProv> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionProdProv vinculacion = new TVinculacionProdProv();
				int idProducto = r.getInt("ID_PRODUCTO");
				int idProveedor = r.getInt("ID_PROVEEDOR");;
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

	@Override
	public Set<TVinculacionProdProv> read_all_by_producto(int idProducto) {
		Set<TVinculacionProdProv> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO=? FOR UPDATE");
			s.setInt(1, idProducto);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionProdProv vinculacion = new TVinculacionProdProv();
				int idProveedor = r.getInt("ID_PROVEEDOR");
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

	@Override
	public Set<TVinculacionProdProv> read_all_by_proveedor(int idProveedor) {
		Set<TVinculacionProdProv> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PROVEEDOR=? FOR UPDATE");
			s.setInt(1, idProveedor);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionProdProv vinculacion = new TVinculacionProdProv();
				int idProducto = r.getInt("ID_PRODUCTO");
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

}
