package integracion.FactoriaQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

public class CalcularElImporteTotalDelEmpleadoQueMasHaVendidoUnProducto implements Query {

    @Override
    public Object execute(Object param) {
        Integer idProducto = (Integer) param;
        double total = -1;

        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            String query =
                    "SELECT SUM(lv.NUM_UNIDADES * lv.PRECIO_UNIDADES) AS total_vendido " +
                    "FROM LINEAVENTA lv " +
                    "JOIN VENTA v ON lv.ID_VENTA = v.ID " +
                    "WHERE v.ID_EMPLEADO = (" +
                    "    SELECT v_in.ID_EMPLEADO " +
                    "    FROM LINEAVENTA lv_in " +
                    "    JOIN VENTA v_in ON lv_in.ID_VENTA = v_in.ID " +
                    "    WHERE lv_in.ID_PRODUCTO = ? AND v_in.ACTIVO = 1 " +
                    "    GROUP BY v_in.ID_EMPLEADO " +
                    "    ORDER BY SUM(lv_in.NUM_UNIDADES * lv_in.PRECIO_UNIDADES) DESC " +
                    "    LIMIT 1" +
                    ") AND lv.ID_PRODUCTO = ? AND v.ACTIVO = 1 FOR UPDATE";

            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, idProducto);
            statement.setInt(2, idProducto);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
                if (total == 0) total = -1;
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}
