
package main;
import dao.MarcaDao;
import java.util.List;
import java.util.Scanner;
import modelo.Marca;
public class MenuPrincipal {
    static Scanner sc= new Scanner(System.in);
    static MarcaDao marcaDao= new MarcaDao();
    
    public static void main (String[] args){
        int opcion=0;
        while (opcion !=5){
            System.out.println("\n====TECNOSTORE====");
            System.out.println("1. Gestion de Marcas");
            System.out.println("2. Gestion de Celulares");
            System.out.println("3. Gestion de Cliente");
            System.out.println("4. Gestion de Ventas");
            System.out.println("5. salir");
            System.out.println("Elige una opcion.");
            
            opcion=sc.nextInt();
            sc.nextLine();
            
            switch (opcion){
                case 1: menuMarcas();break;
                case 2: menuMarcas();break;
                case 3: menuMarcas();break;
                case 4: menuMarcas();break;
                case 5: menuMarcas();break;
                default: System.out.println("opcion invalida");
                
            }
        }
    }
    public static void menuMarcas(){
        int opcion=0;
        while (opcion !=4){
            System.out.println("\n----Gestion de Marcas----");
            System.out.println("1. Gestion de Marcas");
            System.out.println("2. Listar Marcas");
            System.out.println("3. Buscar Marca por id");
            System.out.println("4. volver");
            System.out.println("Marca una Opcion");
            
            opcion=sc.nextInt();
            sc.nextLine();
            
            Switch (opcion){
            case 1: RegistrarMarca();break;
            case 2: ListarMarca();break;
            case 3: menuMarcas();break;
            case 4: menuMarcas();break;
            
        }
                  
                    
                
                    
        }
    }
}
