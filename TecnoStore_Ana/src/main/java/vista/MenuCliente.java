package vista;

import control.ClienteControl;
import vistaValidaciones.Entero;

public class MenuCliente {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por id");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1:
                    ClienteControl.registrar();
                    break;
                case 2:
                    ClienteControl.listar();
                    break;
                case 3:
                    ClienteControl.buscar();
                    break;
                case 4:
                    ClienteControl.actualizar();
                    break;
                case 5:
                    ClienteControl.eliminar();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}
