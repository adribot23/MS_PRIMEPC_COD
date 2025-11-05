package integracion.FactoriaQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.Map;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

public class CalcularElImporteTotalDelEmpleadoQueMasHaVendidoUnProducto implements Query {

    @Override
    public Object execute(Object param) {
        Integer idProducto = (Integer) param;
        double total = -1;
        int idEmpleado = -1;

        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            // No se puede hacer FOR UPDATE con filas agregadas
            String query =
                    "SELECT v_in.ID_EMPLEADO, SUM(lv_in.NUM_UNIDADES * lv_in.PRECIO_UNIDADES) AS total_vendido " +
                    "FROM LINEAVENTA lv_in " +
                    "JOIN VENTA v_in ON lv_in.ID_VENTA = v_in.ID " +
                    "WHERE lv_in.ID_PRODUCTO = ? AND v_in.ACTIVO = 1 " +
                    "GROUP BY v_in.ID_EMPLEADO " +
                    "ORDER BY total_vendido DESC " +
                    "LIMIT 1";

            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, idProducto);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                idEmpleado = rs.getInt("ID_EMPLEADO");
                total = rs.getDouble("total_vendido");
                if (total == 0) total = -1;
            }

            rs.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Devuelvo un par (idEmpleado, total)
        return new AbstractMap.SimpleEntry<>(idEmpleado, total);
    }
}

