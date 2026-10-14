package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;
import patron.ConexionDB;

public class CelularDao {

    public void insertar(Celular c) {
        String sql = "INSERT INTO celulares (marca_fk, sistema_fk, gama_fk, modelo, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getMarca().getId());
            ps.setInt(2, c.getSistemaOperativo().getId());
            ps.setInt(3, obtenerIdGama(c.getGama()));
            ps.setString(4, c.getModelo());
            ps.setDouble(5, c.getPrecio());
            ps.setInt(6, c.getStock());

            ps.executeUpdate();
            System.out.println("Celular insertado");

        } catch (SQLException e) {
            System.err.println("Error al insertar celular: " + e.getMessage());
        }
    }

    public List<Celular> listar() {
        List<Celular> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.modelo, c.precio, c.stock, " +
                     "m.id AS marca_id, m.nombre AS marca_nombre, " +
                     "s.id AS so_id, s.nombre AS so_nombre, s.version AS so_version, " +
                     "g.id AS gama_id, g.nombre AS gama_nombre " +
                     "FROM celulares c " +
                     "JOIN marca m ON c.marca_fk = m.id " +
                     "JOIN sistema_operativo s ON c.sistema_fk = s.id " +
                     "JOIN gama g ON c.gama_fk = g.id " +   
                     "ORDER BY c.id";                        
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearCelularDesdeRS(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar celulares: " + e.getMessage());
        }

        return lista;
    }

    public Celular buscarPorId(int id) {
        Celular celular = null;
        String sql = "SELECT c.id, c.modelo, c.precio, c.stock, " +
                     "m.id AS marca_id, m.nombre AS marca_nombre, " +
                     "s.id AS so_id, s.nombre AS so_nombre, s.version AS so_version, " +
                     "g.id AS gama_id, g.nombre AS gama_nombre " +
                     "FROM celulares c " +
                     "JOIN marca m ON c.marca_fk = m.id " +
                     "JOIN sistema_operativo s ON c.sistema_fk = s.id " +
                     "JOIN gama g ON c.gama_fk = g.id " +
                     "WHERE c.id = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    celular = crearCelularDesdeRS(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar celular: " + e.getMessage());
        }

        return celular;
    }

    public void actualizar(Celular c) {
        String sql = "UPDATE celulares SET marca_fk=?, sistema_fk=?, gama_fk=?, modelo=?, precio=?, stock=? WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getMarca().getId());
            ps.setInt(2, c.getSistemaOperativo().getId());
            ps.setInt(3, obtenerIdGama(c.getGama()));
            ps.setString(4, c.getModelo());
            ps.setDouble(5, c.getPrecio());
            ps.setInt(6, c.getStock());
            ps.setInt(7, c.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Celular actualizado");
            } else {
                System.out.println("No se encontro el celular con ese id");
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar celular: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM celulares WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Celular eliminado");
            } else {
                System.out.println("No se encontro el celular con ese id");
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar celular: " + e.getMessage());
        }
    }

    public void actualizarStock(int idCelular, int nuevoStock) {
        String sql = "UPDATE celulares SET stock=? WHERE id=?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idCelular);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
        }
    }

    private Celular crearCelularDesdeRS(ResultSet rs) throws SQLException {
        Celular c = new Celular();
        c.setId(rs.getInt("id"));
        c.setModelo(rs.getString("modelo"));
        c.setPrecio(rs.getDouble("precio"));
        c.setStock(rs.getInt("stock"));

        Marca m = new Marca();
        m.setId(rs.getInt("marca_id"));
        m.setNombre(rs.getString("marca_nombre"));
        c.setMarca(m);

        SistemaOperativo so = new SistemaOperativo();
        so.setId(rs.getInt("so_id"));
        so.setNombre(rs.getString("so_nombre"));
        so.setVersion(rs.getString("so_version"));
        c.setSistemaOperativo(so);

        c.setGama(convertirTextoAEnum(rs.getString("gama_nombre")));
        return c;
    }

    private int obtenerIdGama(CategoriaGama gama) {
        String sql = "SELECT id FROM gama WHERE nombre=?";
        int id = 0;
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gama.getDescripcion());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener id de gama: " + e.getMessage());
        }

        return id;
    }
    private CategoriaGama convertirTextoAEnum(String texto) {
    for (CategoriaGama gama : CategoriaGama.values()) {
        if (gama.getDescripcion().equalsIgnoreCase(texto)) {
            return gama;
        }
    }
    return null;
}
}
