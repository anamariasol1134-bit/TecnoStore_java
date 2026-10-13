
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Marca;
import patron.ConexionDB;

public class MarcaDao {
    public void insertar(Marca marca){
        String sql = "INSERT INTO marca (nombre) VALUES(?)";
        try (Connection con = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, marca.getNombre());
            ps.executeUpdate();
            System.out.print("marca insertada");
        }catch (SQLException e){
            System.err.println("error al insertar marca:"+ e.getMessage());
        }
    }
    public List<Marca> listar(){
        List<Marca> lista = new ArrayList<>();
        String sql= "SELECT id, nombre FROM marca";
        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                Marca m = new Marca();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                lista.add(m);
            }
        }catch (SQLException e){
            System.err.println("error al listar marcas:"+ e.getMessage());
        }
        return lista;
    }
    public Marca buscarPorId(int id){
        Marca marca= null;
        String sql= "SELECT id, nombre FROM marca WHERE id=?";
        try (Connection con= ConexionDB.getInstancia().getConexion();
                PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1, id);
            
            try(ResultSet rs=ps.executeQuery()){
                if (rs.next()){
                    marca=new Marca();
                    marca.setId(rs.getInt("id"));
                    marca.setNombre(rs.getString("nombre"));
                }
            }
        }catch (SQLException e){
            System.err.println("Error al buscar marca:"+ e.getMessage());
        }
        return marca;
    }
    public void actualizar(Marca marca){
        String sql= "UPDATE marca SET nombre =? WHERE id =?";
        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getId());
            
            int filas= ps.executeUpdate();
            if (filas>0){
                System.out.println("marca actualizada");
            }else{
                System.out.println("no se encontro la marca con eses id");
            }
        }catch(SQLException e){
             System.err.println("error al actualizar marca:"+ e.getMessage());

        }
    }
    public void eliminar (int id){
        String sql = "DELETE FROM marca WHERE id=?";
        
        try (Connection con= ConexionDB.getInstancia().getConexion();
        PreparedStatement ps= con.prepareStatement(sql)){
            ps.setInt(1, id);
            
            int filas=ps.executeUpdate();
            if(filas>0){
                System.out.print("Marca eliminada");
            }else {
                System.err.print("No se encontro la marca con ese id");
            }
        }catch (SQLException e){
                System.err.println("Error al eliminar marca:"+ e.getMessage());
        }   
    }
}
