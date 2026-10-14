package control;

import vista.MenuCelular;
import vista.MenuCliente;
import vista.MenuEmpleado;
import vista.MenuMarca;
import vista.MenuReporte;
import vista.MenuVenta;
import vistaValidaciones.Entero;

public class GeneralControl {

    public static void menuPrincipal() {
        int opcion = 0;
        while (opcion != 7) {
            System.out.println("===== TECNOSTORE =====");
            System.out.println("1. Gestion de Marcas");
            System.out.println("2. Gestion de Celulares");
            System.out.println("3. Gestion de Empleados");
            System.out.println("4. Gestion de Clientes");
            System.out.println("5. Gestion de Ventas");
            System.out.println("6. Reportes");
            System.out.println("7. Salir");

            opcion = Entero.leer("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    MenuMarca.mostrar();
                    break;
                case 2:
                    MenuCelular.mostrar();
                    break;
                case 3:
                    MenuEmpleado.mostrar();
                    break;
                case 4:
                    MenuCliente.mostrar();
                    break;
                case 5:
                    MenuVenta.mostrar();
                    break;
                case 6:
                    MenuReporte.mostrar();
                    break;
                case 7:
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}
