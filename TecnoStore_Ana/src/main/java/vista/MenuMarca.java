package vista;

import control.MarcaControl;
import vistaValidaciones.Entero;

public class MenuMarca {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE MARCAS ---");
            System.out.println("1. Registrar marca");
            System.out.println("2. Listar marcas");
            System.out.println("3. Buscar marca por id");
            System.out.println("4. Actualizar marca");
            System.out.println("5. Eliminar marca");
            System.out.println("6. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1:
                    MarcaControl.registrar();
                    break;
                case 2:
                    MarcaControl.listar();
                    break;
                case 3:
                    MarcaControl.buscar();
                    break;
                case 4:
                    MarcaControl.actualizar();
                    break;
                case 5:
                    MarcaControl.eliminar();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}
