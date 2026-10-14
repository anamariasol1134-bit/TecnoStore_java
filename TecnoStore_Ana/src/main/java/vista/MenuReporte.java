package vista;

import control.ReporteControl;
import vistaValidaciones.Entero;

public class MenuReporte {

    public static void mostrar() {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n--- REPORTES ---");
            System.out.println("1. Celulares con stock bajo");
            System.out.println("2. Top 3 celulares mas vendidos");
            System.out.println("3. Ventas totales por mes");
            System.out.println("4. Generar reporte_ventas.txt");
            System.out.println("5. Volver");

            opcion = Entero.leer("Elige: ");

            switch (opcion) {
                case 1: ReporteControl.stockBajo(); break;
                case 2: ReporteControl.top3(); break;
                case 3: ReporteControl.ventasPorMes(); break;
                case 4: ReporteControl.generarArchivo(); break;
                case 5: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }
}