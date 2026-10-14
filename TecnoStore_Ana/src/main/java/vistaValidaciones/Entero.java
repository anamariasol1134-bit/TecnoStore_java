package vistaValidaciones;

import java.util.InputMismatchException;

public class Entero extends Entrada {

    public static int leer(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debes escribir un numero. Intenta de nuevo.");
                sc.nextLine();
            }
        }
    }
}
