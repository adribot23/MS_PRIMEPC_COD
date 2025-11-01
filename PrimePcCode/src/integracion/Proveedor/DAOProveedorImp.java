/**
 * 
 */
package integracion.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Proveedor.TProveedor;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class DAOProveedorImp implements DAOProveedor {

	@Override
	public int create(TProveedor proveedor) {
		int id = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();

			String sql = "INSERT INTO PROVEEDOR (NOMBRE, ACTIVO) VALUES (?, 1)";
			PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, proveedor.getNombre());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public TProveedor read(Integer id) {
		TProveedor proveedor = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			String sql = "SELECT * FROM PROVEEDOR WHERE ID = ? FOR UPDATE";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				proveedor = new TProveedor();
				int idProveedor = rs.getInt("ID");
				proveedor.setId(idProveedor);
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));

			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedor;
	}

	@Override
	public int update(TProveedor proveedor) {
		int filasAfectadas = 0;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			String sql = "UPDATE PROVEEDOR SET NOMBRE = ?, ACTIVO = 1 WHERE ID = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, proveedor.getNombre());
			ps.setInt(2, proveedor.getId());

			filasAfectadas = ps.executeUpdate();

			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	@Override
	public int delete(Integer id) {
		int filasAfectadas = 0;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			String sql = "UPDATE PROVEEDOR SET ACTIVO = 0 WHERE ID = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);

			filasAfectadas = ps.executeUpdate();

			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	@Override
	public TProveedor read_by_name(String nombre) {
		TProveedor proveedor = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			String sql = "SELECT * FROM PROVEEDOR WHERE NOMBRE = ? FOR UPDATE";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				proveedor = new TProveedor();
				proveedor.setId(rs.getInt("ID"));
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedor;
	}

	@Override
	public Set<TProveedor> read_all() {
		Set<TProveedor> proveedores = new HashSet<>();

		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();

			String sql = "SELECT * FROM PROVEEDOR FOR UPDATE ";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TProveedor proveedor = new TProveedor();
				proveedor.setId(rs.getInt("ID"));
				proveedor.setNombre(rs.getString("NOMBRE"));
				proveedor.setActivo(rs.getInt("ACTIVO"));
				proveedores.add(proveedor);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedores;
	}
}