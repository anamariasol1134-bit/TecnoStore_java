package vista;

import control.EmpleadoControl;
import vistaValidaciones.Entero;

public class MenuEmpleado {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Buscar empleado por id");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("6. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1: EmpleadoControl.registrar(); break;
                case 2: EmpleadoControl.listar(); break;
                case 3: EmpleadoControl.buscar(); break;
                case 4: EmpleadoControl.actualizar(); break;
                case 5: EmpleadoControl.eliminar(); break;
                case 6: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }
}
