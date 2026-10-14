package vista;

import control.VentaControl;
import vistaValidaciones.Entero;

public class MenuVenta {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n--- GESTION DE VENTAS ---");
            System.out.println("1. Registrar venta");
            System.out.println("2. Listar ventas");
            System.out.println("3. Buscar venta por id");
            System.out.println("4. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1:
                    VentaControl.registrar();
                    break;
                case 2:
                    VentaControl.listar();
                    break;
                case 3:
                    VentaControl.buscar();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}
