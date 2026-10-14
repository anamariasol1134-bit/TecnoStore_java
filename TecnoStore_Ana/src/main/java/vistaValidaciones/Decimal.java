package vistaValidaciones;

import java.util.InputMismatchException;

public class Decimal extends Entrada {

    public static double leer(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debes escribir un numero. Intenta de nuevo.");
                sc.nextLine();
            }
        }
    }
}