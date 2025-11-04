package integracion.Cliente;

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

public class DAOClienteImp implements DAOCliente {

    @Override
    public int create(TCliente cliente) {
        int id = -1;
        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();
            
            PreparedStatement check = c.prepareStatement("SELECT ID FROM CLIENTE WHERE DNI = ? FOR UPDATE");
            check.setString(1, cliente.getDni());
            ResultSet rsCheck = check.executeQuery();
            if (rsCheck.next()) {
                System.err.println("Cliente con DNI " + cliente.getDni() + " ya existe.");
                rsCheck.close();
                check.close();
                return -1;
            }
            rsCheck.close();
            check.close();

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO CLIENTE (DNI, NOMBRE, ACTIVO) VALUES (?, ?, 1)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);

                if (cliente instanceof TClienteSocio) {
                    TClienteSocio socio = (TClienteSocio) cliente;
                    PreparedStatement psSocio = c.prepareStatement(
                            "INSERT INTO SOCIO (ID, PUNTOS, NUM_SOCIO) VALUES (?, ?, ?)");
                    psSocio.setInt(1, id);
                    psSocio.setInt(2, socio.getPuntos());
                    psSocio.setInt(3, generarNumSocio(c));
                    psSocio.executeUpdate();
                    psSocio.close();

                } else if (cliente instanceof TClienteNoSocio) {
                    TClienteNoSocio noSocio = (TClienteNoSocio) cliente;
                    PreparedStatement psNoSocio = c.prepareStatement(
                            "INSERT INTO NOSOCIO (ID, NUM_VISITAS) VALUES (?, ?)");
                    psNoSocio.setInt(1, id);
                    psNoSocio.setInt(2, noSocio.getNumVisitas());
                    psNoSocio.executeUpdate();
                    psNoSocio.close();
                }
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public TCliente read(int id) {
        TCliente cliente = null;
        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            PreparedStatement ps = c.prepareStatement("SELECT DNI, NOMBRE, ACTIVO FROM CLIENTE WHERE ID = ? FOR UPDATE");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dni = rs.getString("DNI");
                String nombre = rs.getString("NOMBRE");
                int activo = rs.getInt("ACTIVO");

                PreparedStatement psSocio = c.prepareStatement("SELECT PUNTOS, NUM_SOCIO FROM SOCIO WHERE ID = ? FOR UPDATE");
                psSocio.setInt(1, id);
                ResultSet rsSocio = psSocio.executeQuery();

                if (rsSocio.next()) {
                    int puntos = rsSocio.getInt("PUNTOS");
                    int numSocio = rsSocio.getInt("NUM_SOCIO");
                    cliente = new TClienteSocio(id, nombre, dni, activo, numSocio, puntos);
                } else {
                    PreparedStatement psNoSocio = c.prepareStatement("SELECT NUM_VISITAS FROM NOSOCIO WHERE ID = ? FOR UPDATE");
                    psNoSocio.setInt(1, id);
                    ResultSet rsNoSocio = psNoSocio.executeQuery();

                    if (rsNoSocio.next()) {
                        int numVisitas = rsNoSocio.getInt("NUM_VISITAS");
                        cliente = new TClienteNoSocio(id, nombre, dni, activo, numVisitas);
                    }

                    rsNoSocio.close();
                    psNoSocio.close();
                }

                rsSocio.close();
                psSocio.close();
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public int update(TCliente cliente) {
        int exito = -1;

        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            TCliente clienteActual = read(cliente.getId());
            if (clienteActual == null) {
                return -1;
            }

            boolean tipoDiferente =
                    (clienteActual instanceof TClienteSocio && cliente instanceof TClienteNoSocio)
                            || (clienteActual instanceof TClienteNoSocio && cliente instanceof TClienteSocio);

            if (tipoDiferente) {
                System.err.println("No se puede cambiar el tipo de cliente.");
                return -1;
            }

            PreparedStatement s = c.prepareStatement(
                    "UPDATE CLIENTE SET DNI = ?, NOMBRE = ?, ACTIVO = 1 WHERE ID = ? ");
            s.setString(1, cliente.getDni());
            s.setString(2, cliente.getNombre());
            s.setInt(3, cliente.getId());
            exito = s.executeUpdate();

            if (cliente instanceof TClienteSocio) {
                TClienteSocio socio = (TClienteSocio) cliente;
                PreparedStatement psSocio = c.prepareStatement(
                        "UPDATE SOCIO SET PUNTOS = ? WHERE ID = ?");
                psSocio.setInt(1, socio.getPuntos());
                psSocio.setInt(2, socio.getId());
                psSocio.executeUpdate();
                psSocio.close();

            } else if (cliente instanceof TClienteNoSocio) {
                TClienteNoSocio noSocio = (TClienteNoSocio) cliente;
                PreparedStatement psNoSocio = c.prepareStatement(
                        "UPDATE NOSOCIO SET NUM_VISITAS = ? WHERE ID = ?");
                psNoSocio.setInt(1, noSocio.getNumVisitas());
                psNoSocio.setInt(2, noSocio.getId());
                psNoSocio.executeUpdate();
                psNoSocio.close();
            }

            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return (exito > 0) ? exito : -1;
    }

    @Override
    public int delete(int id) {
        int exito = -1;
        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();
            PreparedStatement s = c.prepareStatement("UPDATE CLIENTE SET ACTIVO = 0 WHERE ID = ?");
            s.setInt(1, id);
            exito = s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return (exito > 0) ? exito : -1;
    }

    @Override
    public TCliente read_by_DNI(String dni) {
        if (dni == null || dni.isEmpty()) return null;

        TCliente cliente = null;
        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            PreparedStatement psCliente = c.prepareStatement(
                    "SELECT ID, NOMBRE, ACTIVO FROM CLIENTE WHERE DNI = ? FOR UPDATE");
            psCliente.setString(1, dni);
            ResultSet rsCliente = psCliente.executeQuery();

            if (rsCliente.next()) {
                int id = rsCliente.getInt("ID");
                String nombre = rsCliente.getString("NOMBRE");
                int activo = rsCliente.getInt("ACTIVO");

                PreparedStatement psSocio = c.prepareStatement("SELECT PUNTOS, NUM_SOCIO FROM SOCIO WHERE ID = ? FOR UPDATE");
                psSocio.setInt(1, id);
                ResultSet rsSocio = psSocio.executeQuery();

                if (rsSocio.next()) {
                    int puntos = rsSocio.getInt("PUNTOS");
                    int numSocio = rsSocio.getInt("NUM_SOCIO");
                    cliente = new TClienteSocio(id, nombre, dni, activo, numSocio, puntos);
                } else {
                    PreparedStatement psNoSocio = c.prepareStatement("SELECT NUM_VISITAS FROM NOSOCIO WHERE ID = ? FOR UPDATE");
                    psNoSocio.setInt(1, id);
                    ResultSet rsNoSocio = psNoSocio.executeQuery();
                    if (rsNoSocio.next()) {
                        int numVisitas = rsNoSocio.getInt("NUM_VISITAS");
                        cliente = new TClienteNoSocio(id, nombre, dni, activo, numVisitas);
                    }
                    rsNoSocio.close();
                    psNoSocio.close();
                }

                rsSocio.close();
                psSocio.close();
            }

            rsCliente.close();
            psCliente.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public Set<TCliente> read_all() {
        Set<TCliente> clientes = new LinkedHashSet<>();

        try {
            TManager tManager = TManager.getInstance();
            Transaction t = tManager.getTransaction();
            Connection c = (Connection) t.getResource();

            PreparedStatement s = c.prepareStatement("SELECT ID, DNI, NOMBRE, ACTIVO FROM CLIENTE FOR UPDATE");
            ResultSet r = s.executeQuery();

            while (r.next()) {
                int id = r.getInt("ID");
                String dni = r.getString("DNI");
                String nombre = r.getString("NOMBRE");
                int activo = r.getInt("ACTIVO");

                PreparedStatement psSocio = c.prepareStatement("SELECT PUNTOS, NUM_SOCIO FROM SOCIO WHERE ID = ? FOR UPDATE");
                psSocio.setInt(1, id);
                ResultSet rsSocio = psSocio.executeQuery();

                if (rsSocio.next()) {
                    int puntos = rsSocio.getInt("PUNTOS");
                    int numSocio = rsSocio.getInt("NUM_SOCIO");
                    clientes.add(new TClienteSocio(id, nombre, dni, activo, numSocio, puntos));
                } else {
                    PreparedStatement psNoSocio = c.prepareStatement("SELECT NUM_VISITAS FROM NOSOCIO WHERE ID = ? FOR UPDATE");
                    psNoSocio.setInt(1, id);
                    ResultSet rsNoSocio = psNoSocio.executeQuery();
                    if (rsNoSocio.next()) {
                        int numVisitas = rsNoSocio.getInt("NUM_VISITAS");
                        clientes.add(new TClienteNoSocio(id, nombre, dni, activo, numVisitas));
                    }
                    rsNoSocio.close();
                    psNoSocio.close();
                }

                rsSocio.close();
                psSocio.close();
            }

            r.close();
            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return clientes;
    }

    private int generarNumSocio(Connection c) throws SQLException {
        int nuevoID = 1;
        try (Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT MAX(NUM_SOCIO) FROM SOCIO")) {
            if (rs.next()) {
                nuevoID = rs.getInt(1) + 1;
            }
        }
        return nuevoID;
    }
}
