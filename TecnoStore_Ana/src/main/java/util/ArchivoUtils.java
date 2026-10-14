package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import modelo.ItemVenta;
import modelo.Venta;

public class ArchivoUtils {

    /**
     * Genera el archivo reporte_ventas.txt con el resumen de todas las ventas.
     */
    public static void generarReporteVentas(List<Venta> ventas, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            writer.write("===========================================\n");
            writer.write("       REPORTE DE VENTAS - TECNOSTORE\n");
            writer.write("===========================================\n\n");

            double totalGeneral = 0;

            for (Venta v : ventas) {
                writer.write("Venta ID: " + v.getId() + "\n");
                writer.write("Fecha: " + v.getFecha() + "\n");
                writer.write("Cliente: " + v.getCliente().getNombre() + "\n");
                writer.write("Empleado: " + v.getEmpleado().getNombre() + "\n");
                writer.write("Items:\n");

                for (ItemVenta item : v.getItems()) {
                    writer.write("  - " + item.getCelular().getMarca().getNombre()
                                    + " " + item.getCelular().getModelo()
                                    + " x" + item.getCantidad()
                                    + " = $" + item.getSubtotal() + "\n");
                }

                writer.write("Subtotal: $" + v.getSubtotal() + "\n");
                writer.write("IVA:      $" + v.getIva() + "\n");
                writer.write("TOTAL:    $" + v.getTotal() + "\n");
                writer.write("-------------------------------------------\n\n");

                totalGeneral += v.getTotal();
            }

            writer.write("===========================================\n");
            writer.write("TOTAL GENERAL DE VENTAS: $" + totalGeneral + "\n");
            writer.write("===========================================\n");

            System.out.println("Archivo generado: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al generar archivo: " + e.getMessage());
        }
    }
}
