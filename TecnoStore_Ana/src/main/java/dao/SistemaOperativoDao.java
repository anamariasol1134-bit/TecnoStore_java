package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.SistemaOperativo;
import patron.ConexionDB;

public class SistemaOperativoDao {

    public List<SistemaOperativo> listar() {
        List<SistemaOperativo> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, version FROM sistema_operativo";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SistemaOperativo so = new SistemaOperativo();
                so.setId(rs.getInt("id"));
                so.setNombre(rs.getString("nombre"));
                so.setVersion(rs.getString("version"));
                lista.add(so);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar sistemas operativos: " + e.getMessage());
        }

        return lista;
    }

    public SistemaOperativo buscarPorId(int id) {
        SistemaOperativo so = null;
        String sql = "SELECT id, nombre, version FROM sistema_operativo ORDER BY id";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    so = new SistemaOperativo();
                    so.setId(rs.getInt("id"));
                    so.setNombre(rs.getString("nombre"));
                    so.setVersion(rs.getString("version"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar sistema operativo: " + e.getMessage());
        }

        return so;
    }
}
