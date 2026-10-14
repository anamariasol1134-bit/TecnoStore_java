package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.CategoriaGama;
import patron.ConexionDB;

public class GamaDao {

    public List<CategoriaGama> listar() {
        List<CategoriaGama> lista = new ArrayList<>();
        String sql = "SELECT nombre FROM gama";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                CategoriaGama gama = convertirTextoAEnum(nombre);
                if (gama != null) {
                    lista.add(gama);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar gamas: " + e.getMessage());
        }

        return lista;
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
