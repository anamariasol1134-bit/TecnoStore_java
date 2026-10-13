
package main;
import patron.ConexionDB;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args){
    System.out.println("iniciando");
    ConexionDB db = ConexionDB.getInstancia();
    Connection con = db.getConexion();
    
    if (con != null){
        System.out.println("conexion exitosa a la base de datos");
    }else{
        System.out.println("no se pudo conectar");
    }
    db.cerrar();
    System.out.println("fin");
            
            }
}
