
package patron;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/tecnostore_anasuarez";
    private static final String USUARIO= "root";
    private static final String PASSWORD = "1095304303";
    
    private static ConexionDB instancia;
    private Connection conexion;
    
    private ConexionDB(){
        try{
            this.conexion=DriverManager.getConnection(URL,USUARIO,PASSWORD);
            System.out.println("conexion establecida");   
       } catch (SQLException e){
           throw new RuntimeException("error, no se conecto:" + e.getMessage(), e);
       }
        
    }
    public static ConexionDB getInstancia(){
        if (instancia == null){
            instancia= new ConexionDB();
        }
        return instancia;
        
    }
    public Connection getConexion(){
        return conexion;
    }
    public void cerrar(){
        try{
            if (conexion !=null && !conexion.isClosed()){
                conexion.close();
                System.out.println("conecion cerrada");
            }
        }catch (SQLException e){
            System.err.println("error al cerra;"+ e.getMessage());
        }
    }   
}
