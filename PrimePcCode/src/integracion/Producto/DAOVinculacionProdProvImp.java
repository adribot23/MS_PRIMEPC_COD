package integracion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import Negocio.Trabajador.TVinculacionTrabHab;
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
	public TVinculacionProdProv read(int id) {
		TVinculacionProdProv vinculacion = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM PRODUCTO_PROVEEDOR WHERE ID_PRODUCTO = ? AND ID_PROVEEDOR = ? FOR UPDATE");
			s.setInt(1, id_trabajador);
			s.setInt(2, id_habilidad);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				vinculacion = new TVinculacionTrabHab();
				vinculacion.set_id_trabajador(r.getInt("id_trabajador"));
				vinculacion.set_id_habilidad(r.getInt("id_habilidad"));
				vinculacion.set_activo(r.getInt("activo"));
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculacion;
	}

	@Override
	public int update(TVinculacionProdProv producto) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public Set<TVinculacionProdProv> read_all() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TVinculacionProdProv> read_all_by_almacen(int idAlmacen) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TVinculacionProdProv> read_all_by_proveedor(int idProveedor) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
