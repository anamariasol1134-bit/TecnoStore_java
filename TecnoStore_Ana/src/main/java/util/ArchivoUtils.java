package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import modelo.ItemVenta;
import modelo.Venta;

public class ArchivoUtils {

    public static void generarReporteVentas(List<Venta> ventas, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            writer.write("===========================================\n");
            writer.write("       REPORTE DE VENTAS - TECNOSTORE\n");
            writer.write("===========================================\n\n");

            double totalGeneral = 0;

            for (Venta v : ventas) {
                writer.write("Venta ID: " + v.getId() + "\n");
                writer.write("Fecha: " + formatearFecha(v.getFecha()) + "\n");
                writer.write("Cliente: " + v.getCliente().getNombre() + "\n");
                writer.write("Empleado: " + v.getEmpleado().getNombre() + "\n");
                writer.write("Items:\n");

                for (ItemVenta item : v.getItems()) {
                    writer.write("  - " + item.getCelular().getMarca().getNombre()
                                    + " " + item.getCelular().getModelo()
                                    + " x" + item.getCantidad()
                                    + " = " + formatearMoneda(item.getSubtotal()) + "\n");
                }

                writer.write("Subtotal: " + formatearMoneda(v.getSubtotal()) + "\n");
                writer.write("IVA:      " + formatearMoneda(v.getIva()) + "\n");
                writer.write("TOTAL:    " + formatearMoneda(v.getTotal()) + "\n");
                writer.write("-------------------------------------------\n\n");

                totalGeneral += v.getTotal();
            }

            writer.write("===========================================\n");
            writer.write("TOTAL GENERAL DE VENTAS: " + formatearMoneda(totalGeneral) + "\n");
            writer.write("===========================================\n");

            System.out.println("Archivo generado: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al generar archivo: " + e.getMessage());
        }
    }

    public static String formatearMoneda(double valor) {
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CO"));
        formato.setMaximumFractionDigits(0);
        return "$" + formato.format(valor);
    }

    public static String formatearFecha(java.util.Date fecha) {
        if (fecha == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(fecha);
    }
}