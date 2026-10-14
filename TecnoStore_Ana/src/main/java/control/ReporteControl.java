package control;

import dao.CelularDao;
import dao.VentaDao;
import java.util.List;
import java.util.Map;
import modelo.Celular;
import modelo.Venta;
import util.ArchivoUtils;
import util.ReporteUtils;

public class ReporteControl {

    static CelularDao celularDao = new CelularDao();
    static VentaDao ventaDao = new VentaDao();

    public static void stockBajo() {
        List<Celular> celulares = celularDao.listar();
        List<Celular> stockBajo = ReporteUtils.celularesStockBajo(celulares);

        if (stockBajo.isEmpty()) {
            System.out.println("No hay celulares con stock bajo.");
        } else {
            System.out.println("\n--- CELULARES CON STOCK BAJO (< 5) ---");
            for (Celular c : stockBajo) {
                System.out.println(c.getMarca().getNombre() + " " + c.getModelo()
                        + " - Stock: " + c.getStock());
            }
        }
    }

    public static void top3() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        Map<String, Integer> top3 = ReporteUtils.top3CelularesMasVendidos(ventas);

        System.out.println("\n--- TOP 3 CELULARES MAS VENDIDOS ---");
        int pos = 1;
        for (Map.Entry<String, Integer> e : top3.entrySet()) {
            System.out.println(pos + ". " + e.getKey() + " - " + e.getValue() + " unidades");
            pos++;
        }
    }

    public static void ventasPorMes() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        Map<java.time.Month, Double> porMes = ReporteUtils.ventasTotalesPorMes(ventas);

        System.out.println("\n--- VENTAS TOTALES POR MES ---");
        for (Map.Entry<java.time.Month, Double> e : porMes.entrySet()) {
            System.out.println(e.getKey() + ": " + ArchivoUtils.formatearMoneda(e.getValue()));
        }
    }

    public static void generarArchivo() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para generar reporte.");
            return;
        }
        ArchivoUtils.generarReporteVentas(ventas, "reporte_ventas.txt");
    }
}