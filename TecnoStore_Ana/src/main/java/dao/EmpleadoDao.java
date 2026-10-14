
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Empleado;
import patron.ConexionDB;

public class EmpleadoDao {
    public void insertar(Empleado e) {
        String sqlPersona = "INSERT INTO persona (nombre, identificacion, correo, telefono) VALUES (?, ?, ?, ?)";
        String sqlEmpleado = "INSERT INTO empleado (id, cargo, fecha_ingreso, salario) VALUES (?, ?, ?, ?)";
        Connection con = ConexionDB.getInstancia().getConexion();

        try {
            con.setAutoCommit(false);

            int idGenerado;
            try (PreparedStatement ps = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, e.getNombre());
                ps.setString(2, e.getIdentificacion());
                ps.setString(3, e.getCorreo());
                ps.setString(4, e.getTelefono());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    idGenerado = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                ps.setInt(1, idGenerado);
                ps.setString(2, e.getCargo());

                if (e.getFechaIngreso() != null) {
                    ps.setDate(3, new java.sql.Date(e.getFechaIngreso().getTime()));
                } else {
                    ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                }

                ps.setDouble(4, e.getSalario());
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Empleado insertado con id " + idGenerado);

        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                System.err.println("Error en rollback: " + e2.getMessage());
            }
            System.err.println("Error al insertar empleado: " + ex.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error al restaurar autocommit: " + ex.getMessage());
            }
        }
    }

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.cargo, e.fecha_ingreso, e.salario " +
                     "FROM persona p " +
                     "JOIN empleado e ON p.id = e.id " +
                     "ORDER BY p.id";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearEmpleadoDesdeRS(rs));
            }

        } catch (SQLException ex) {
            System.err.println("Error al listar empleados: " + ex.getMessage());
        }
        return lista;
    }

    public Empleado buscarPorId(int id) {
        Empleado emp = null;
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.cargo, e.fecha_ingreso, e.salario " +
                     "FROM persona p " +
                     "JOIN empleado e ON p.id = e.id " +
                     "WHERE p.id = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = crearEmpleadoDesdeRS(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar empleado: " + ex.getMessage());
        }
        return emp;
    }

    public Empleado buscarPorIdentificacion(String identificacion) {
        Empleado emp = null;
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.cargo, e.fecha_ingreso, e.salario " +
                     "FROM persona p " +
                     "JOIN empleado e ON p.id = e.id " +
                     "WHERE p.identificacion = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, identificacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = crearEmpleadoDesdeRS(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar empleado: " + ex.getMessage());
        }
        return emp;
    }

    public void actualizar(Empleado e) {
        String sqlPersona = "UPDATE persona SET nombre=?, identificacion=?, correo=?, telefono=? WHERE id=?";
        String sqlEmpleado = "UPDATE empleado SET cargo=?, fecha_ingreso=?, salario=? WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, e.getNombre());
                ps.setString(2, e.getIdentificacion());
                ps.setString(3, e.getCorreo());
                ps.setString(4, e.getTelefono());
                ps.setInt(5, e.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                ps.setString(1, e.getCargo());

                if (e.getFechaIngreso() != null) {
                    ps.setDate(2, new java.sql.Date(e.getFechaIngreso().getTime()));
                } else {
                    ps.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                }

                ps.setDouble(3, e.getSalario());
                ps.setInt(4, e.getId());
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Empleado actualizado");

        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                System.err.println("Error en rollback: " + e2.getMessage());
            }
            System.err.println("Error al actualizar empleado: " + ex.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error al restaurar autocommit: " + ex.getMessage());
            }
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM persona WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Empleado eliminado");
            } else {
                System.out.println("No se encontro el empleado con ese id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al eliminar empleado: " + ex.getMessage());
        }
    }

    private Empleado crearEmpleadoDesdeRS(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setId(rs.getInt("id"));
        e.setNombre(rs.getString("nombre"));
        e.setIdentificacion(rs.getString("identificacion"));
        e.setCorreo(rs.getString("correo"));
        e.setTelefono(rs.getString("telefono"));
        e.setCargo(rs.getString("cargo"));
        e.setFechaIngreso(rs.getDate("fecha_ingreso"));
        e.setSalario(rs.getDouble("salario"));
        return e;
    }
}
