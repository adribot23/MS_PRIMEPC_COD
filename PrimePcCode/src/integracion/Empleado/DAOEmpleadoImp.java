package integracion.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
import negocio.Producto.TProducto;

public class DAOEmpleadoImp implements DAOEmpleado {

    @Override
    public int create(TEmpleado empleado) {
        int id = empleado.getId();

        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
            String insertSql = "INSERT INTO EMPLEADO (DNI, NOMBRE, TELEFONO, ACTIVO) VALUES (?, ?, ?, 1)";
            PreparedStatement insertPs = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertPs.setString(1, empleado.getDni());
            insertPs.setString(2, empleado.getNombre());
            insertPs.setString(3, empleado.getTelefono());
            insertPs.executeUpdate();

            ResultSet insertRs = insertPs.getGeneratedKeys();
            if (insertRs.next()) {
                id = insertRs.getInt(1);
                if (empleado instanceof TEmpleadoCompleto) {
                    TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
                    insertSql = "INSERT INTO COMPLETO (ID, HORAS_EXTRA) VALUES (?, ?)";
                    PreparedStatement psCompleto = c.prepareStatement(insertSql);
                    psCompleto.setInt(1, id);
                    psCompleto.setInt(2, completo.getHorasExtra());
                    psCompleto.executeUpdate();
                    psCompleto.close();
                } else if (empleado instanceof TEmpleadoParcial) {
                    TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
                    insertSql = "INSERT INTO PARCIAL (ID, HORAS_SEMANALES) VALUES (?, ?)";
                    PreparedStatement psParcial = c.prepareStatement(insertSql);
                    psParcial.setInt(1, id);
                    psParcial.setInt(2, parcial.getHorasSemanales());
                    psParcial.executeUpdate();
                    psParcial.close();
                }
            } else {
                throw new SQLException();
            }

            insertRs.close();
            insertPs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public void calculateMostSold(TProducto tProducto) {
        // TODO
    }

    @Override
    public TEmpleado read(Integer id) {
        TEmpleado empleado = null;
        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
            String sqlEmpleado = "SELECT ID, DNI, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO WHERE ID = ? FOR UPDATE";
            PreparedStatement psEmpleado = c.prepareStatement(sqlEmpleado);
            psEmpleado.setInt(1, id);
            ResultSet rsEmpleado = psEmpleado.executeQuery();

            if (rsEmpleado.next()) {
                String dni = rsEmpleado.getString("DNI");
                String nombre = rsEmpleado.getString("NOMBRE");
                String telefono = rsEmpleado.getString("TELEFONO");
                int activo = rsEmpleado.getInt("ACTIVO");

                String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ? FOR UPDATE";
                PreparedStatement psCompleto = c.prepareStatement(sqlCompleto);
                psCompleto.setInt(1, id);
                ResultSet rsCompleto = psCompleto.executeQuery();

                if (rsCompleto.next()) {
                    int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
                    empleado = new TEmpleadoCompleto(id, nombre, dni, telefono, activo, horasExtra);
                } else {
                    String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ? FOR UPDATE";
                    PreparedStatement psParcial = c.prepareStatement(sqlParcial);
                    psParcial.setInt(1, id);
                    ResultSet rsParcial = psParcial.executeQuery();

                    if (rsParcial.next()) {
                        int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
                        empleado = new TEmpleadoParcial(id, nombre, dni, telefono, activo, horasSemanales);
                    }
                    rsParcial.close();
                    psParcial.close();
                }
                rsCompleto.close();
                psCompleto.close();
            }
            rsEmpleado.close();
            psEmpleado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }

    @Override
    public int update(TEmpleado empleado) {
        int exito = -1;
        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
        	TEmpleado empleadoActual = read(empleado.getId());
            if (empleadoActual == null) {
                return -1;
            }

            boolean tipoDiferente =
                    (empleadoActual instanceof TEmpleadoCompleto && empleado instanceof TEmpleadoParcial)
                            || (empleadoActual instanceof TEmpleadoParcial && empleado instanceof TEmpleadoCompleto);

            if (tipoDiferente) {
                System.err.println("No se puede cambiar el tipo de empleado.");
                return -1;
            }
        	
            String updateSql = "UPDATE EMPLEADO SET DNI = ?, NOMBRE = ?, TELEFONO = ?, ACTIVO = 1 WHERE ID = ?";
            PreparedStatement updatePs = c.prepareStatement(updateSql);
            updatePs.setString(1, empleado.getDni());
            updatePs.setString(2, empleado.getNombre());
            updatePs.setString(3, empleado.getTelefono());
            updatePs.setInt(4, empleado.getId());
            exito = updatePs.executeUpdate();
            updatePs.close();

            if (empleado instanceof TEmpleadoCompleto) {
                TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
                String updateCompletoSql = "UPDATE COMPLETO SET HORAS_EXTRA = ? WHERE ID = ?";
                PreparedStatement psCompleto = c.prepareStatement(updateCompletoSql);
                psCompleto.setInt(1, completo.getHorasExtra());
                psCompleto.setInt(2, completo.getId());
                psCompleto.close();
                exito = completo.getId();
            } else if (empleado instanceof TEmpleadoParcial) {
                TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
                String updateParcialSql = "UPDATE PARCIAL SET HORAS_SEMANALES = ? WHERE ID = ?";
                PreparedStatement psParcial = c.prepareStatement(updateParcialSql);
                psParcial.setInt(1, parcial.getHorasSemanales());
                psParcial.setInt(2, parcial.getId());
                psParcial.close();
                exito = parcial.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    @Override
    public int delete(Integer id) {
        int filasAfectadas = 0;
        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
            String sql = "UPDATE EMPLEADO SET ACTIVO = 0 WHERE ID = ?";
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
    public TEmpleado read_by_DNI(String DNI) {
        TEmpleado empleado = null;
        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
            String sqlEmpleado = "SELECT ID, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO WHERE DNI = ? FOR UPDATE";
            PreparedStatement psEmpleado = c.prepareStatement(sqlEmpleado);
            psEmpleado.setString(1, DNI);
            ResultSet rsEmpleado = psEmpleado.executeQuery();

            if (rsEmpleado.next()) {
                int id = rsEmpleado.getInt("ID");
                String nombre = rsEmpleado.getString("NOMBRE");
                String telefono = rsEmpleado.getString("TELEFONO");
                int activo = rsEmpleado.getInt("ACTIVO");

                String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ?";
                PreparedStatement psCompleto = c.prepareStatement(sqlCompleto);
                psCompleto.setInt(1, id);
                ResultSet rsCompleto = psCompleto.executeQuery();

                if (rsCompleto.next()) {
                    int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
                    empleado = new TEmpleadoCompleto(id, nombre, DNI, telefono, activo, horasExtra);
                } else {
                    String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ?";
                    PreparedStatement psParcial = c.prepareStatement(sqlParcial);
                    psParcial.setInt(1, id);
                    ResultSet rsParcial = psParcial.executeQuery();

                    if (rsParcial.next()) {
                        int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
                        empleado = new TEmpleadoParcial(id, nombre, DNI, telefono, activo, horasSemanales);
                    }

                    rsParcial.close();
                    psParcial.close();
                }

                rsCompleto.close();
                psCompleto.close();
            }

            rsEmpleado.close();
            psEmpleado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }

    @Override
    public Set<TEmpleado> read_all() {
        Set<TEmpleado> empleados = new LinkedHashSet<>();
        TManager tManager = TManager.getInstance();
        Transaction t = tManager.getTransaction();
        Connection c = (Connection) t.getResource();

        try {
            String sqlEmpleado = "SELECT ID, DNI, NOMBRE, TELEFONO, ACTIVO FROM EMPLEADO FOR UPDATE";
            PreparedStatement psEmpleado = c.prepareStatement(sqlEmpleado);
            ResultSet rsEmpleado = psEmpleado.executeQuery();

            while (rsEmpleado.next()) {
                int id = rsEmpleado.getInt("ID");
                String dni = rsEmpleado.getString("DNI");
                String nombre = rsEmpleado.getString("NOMBRE");
                String telefono = rsEmpleado.getString("TELEFONO");
                int activo = rsEmpleado.getInt("ACTIVO");

                String sqlCompleto = "SELECT HORAS_EXTRA FROM COMPLETO WHERE ID = ? FOR UPDATE";
                PreparedStatement psCompleto = c.prepareStatement(sqlCompleto);
                psCompleto.setInt(1, id);
                ResultSet rsCompleto = psCompleto.executeQuery();

                if (rsCompleto.next()) {
                    int horasExtra = rsCompleto.getInt("HORAS_EXTRA");
                    empleados.add(new TEmpleadoCompleto(id, nombre, dni, telefono, activo, horasExtra));
                } else {
                    String sqlParcial = "SELECT HORAS_SEMANALES FROM PARCIAL WHERE ID = ? FOR UPDATE";
                    PreparedStatement psParcial = c.prepareStatement(sqlParcial);
                    psParcial.setInt(1, id);
                    ResultSet rsParcial = psParcial.executeQuery();

                    if (rsParcial.next()) {
                        int horasSemanales = rsParcial.getInt("HORAS_SEMANALES");
                        empleados.add(new TEmpleadoParcial(id, nombre, dni, telefono, activo, horasSemanales));
                    }

                    rsParcial.close();
                    psParcial.close();
                }

                rsCompleto.close();
                psCompleto.close();
            }

            rsEmpleado.close();
            psEmpleado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }
}
