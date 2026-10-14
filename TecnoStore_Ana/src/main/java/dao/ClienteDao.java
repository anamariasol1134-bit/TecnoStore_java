package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Cliente;
import patron.ConexionDB;

public class ClienteDao {
    public void insertar(Cliente c) {
        String sqlPersona = "INSERT INTO persona (nombre, identificacion, correo, telefono) VALUES (?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (id, fecha_ingreso, puntos) VALUES (?, ?, ?)";
        Connection con = ConexionDB.getInstancia().getConexion();

        try {
            con.setAutoCommit(false); 
            int idGenerado;
            try (PreparedStatement ps = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getIdentificacion());
                ps.setString(3, c.getCorreo());
                ps.setString(4, c.getTelefono());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    idGenerado = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setInt(1, idGenerado);
                ps.setDate(2, new java.sql.Date(c.getFechaIngreso().getTime()));
                ps.setInt(3, c.getPuntos());
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Cliente insertado con id " + idGenerado);

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            System.err.println("Error al insertar cliente: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
            }
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "c.fecha_ingreso, c.puntos " +
                     "FROM persona p " +
                     "JOIN cliente c ON p.id = c.id " +
                     "ORDER BY p.id";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearClienteDesdeRS(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "c.fecha_ingreso, c.puntos " +
                     "FROM persona p " +
                     "JOIN cliente c ON p.id = c.id " +
                     "WHERE p.id = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = crearClienteDesdeRS(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        Cliente cliente = null;
        String sql = "SELECT p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "c.fecha_ingreso, c.puntos " +
                     "FROM persona p " +
                     "JOIN cliente c ON p.id = c.id " +
                     "WHERE p.identificacion = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, identificacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = crearClienteDesdeRS(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    public void actualizar(Cliente c) {
        String sqlPersona = "UPDATE persona SET nombre=?, identificacion=?, correo=?, telefono=? WHERE id=?";
        String sqlCliente = "UPDATE cliente SET fecha_ingreso=?, puntos=? WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getIdentificacion());
                ps.setString(3, c.getCorreo());
                ps.setString(4, c.getTelefono());
                ps.setInt(5, c.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setDate(1, new java.sql.Date(c.getFechaIngreso().getTime()));
                ps.setInt(2, c.getPuntos());
                ps.setInt(3, c.getId());
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Cliente actualizado");

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
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
                System.out.println("Cliente eliminado");
            } else {
                System.out.println("No se encontro el cliente con ese id");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    private Cliente crearClienteDesdeRS(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setIdentificacion(rs.getString("identificacion"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getString("telefono"));
        c.setFechaIngreso(rs.getDate("fecha_ingreso"));
        c.setPuntos(rs.getInt("puntos"));
        return c;
    }
}
