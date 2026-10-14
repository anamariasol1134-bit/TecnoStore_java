
package util;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.Celular;
import modelo.ItemVenta;
import modelo.Venta;
public class ReporteUtils {
    public static List<Celular> celularesStockBajo(List<Celular> celulares) {
        return celulares.stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
    }

   
    public static Map<String, Integer> top3CelularesMasVendidos(List<Venta> ventas) {
        return ventas.stream()
                .flatMap(v -> v.getItems().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getCelular().getMarca().getNombre() + " " + item.getCelular().getModelo(),
                        Collectors.summingInt(ItemVenta::getCantidad)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        java.util.LinkedHashMap::new
                ));
    }

    public static Map<Month, Double> ventasTotalesPorMes(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> {
                            return v.getFecha().toInstant()
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .getMonth();
                        },
                        Collectors.summingDouble(Venta::getTotal)
                ));
    }

}
