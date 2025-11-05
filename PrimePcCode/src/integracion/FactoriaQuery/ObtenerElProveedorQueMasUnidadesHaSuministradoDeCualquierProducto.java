/**
 * 
 */
package integracion.FactoriaQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class ObtenerElProveedorQueMasUnidadesHaSuministradoDeCualquierProducto implements Query {
	public Object execute(Object param) {
        int idProveedor = -1;

        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            // No se puede hacer FOR UPDATE con filas agregadas
            String query =
            		"SELECT pr.ID, pr.NOMBRE, SUM(p.NUM_UNIDADES) AS total_unidades " +
            		"FROM PROVEEDOR pr " +
            		"JOIN PRODUCTO_PROVEEDOR pp ON pr.ID = pp.ID_PROVEEDOR " +
            		"JOIN PRODUCTO p ON pp.ID_PRODUCTO = p.ID " +
            		"WHERE pr.ACTIVO = 1 " +
            		"GROUP BY pr.ID, pr.NOMBRE " +
            		"ORDER BY total_unidades DESC " +
            		"LIMIT 1;";


            PreparedStatement statement = c.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                idProveedor = rs.getInt("ID");
            }

            rs.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idProveedor;
	}
}