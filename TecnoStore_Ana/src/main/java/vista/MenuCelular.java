package vista;

import control.CelularControl;
import vistaValidaciones.Entero;

public class MenuCelular {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE CELULARES ---");
            System.out.println("1. Registrar celular");
            System.out.println("2. Listar celulares");
            System.out.println("3. Buscar celular por id");
            System.out.println("4. Actualizar celular");
            System.out.println("5. Eliminar celular");
            System.out.println("6. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1:
                    CelularControl.registrar();
                    break;
                case 2:
                    CelularControl.listar();
                    break;
                case 3:
                    CelularControl.buscar();
                    break;
                case 4:
                    CelularControl.actualizar();
                    break;
                case 5:
                    CelularControl.eliminar();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}
