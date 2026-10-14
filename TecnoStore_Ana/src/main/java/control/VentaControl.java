package control;

import dao.CelularDao;
import dao.ClienteDao;
import dao.EmpleadoDao;
import dao.VentaDao;
import java.util.Date;
import java.util.List;
import modelo.Celular;
import modelo.Cliente;
import modelo.Empleado;
import modelo.ItemVenta;
import modelo.Venta;
import util.ArchivoUtils;
import vistaValidaciones.Entero;

public class VentaControl {

    static VentaDao ventaDao = new VentaDao();
    static ClienteDao clienteDao = new ClienteDao();
    static EmpleadoDao empleadoDao = new EmpleadoDao();
    static CelularDao celularDao = new CelularDao();

    public static void registrar() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) { System.out.println("No hay clientes."); return; }

        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) { System.out.println("No hay empleados."); return; }

        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) { System.out.println("No hay celulares."); return; }

        System.out.println("\n--- REGISTRAR VENTA ---");

        System.out.println("\nClientes:");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getIdentificacion() + ")");
        }
        int idxCliente = Entero.leer("Elige cliente: ") - 1;

        System.out.println("\nEmpleados:");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " - " + e.getCargo());
        }
        int idxEmpleado = Entero.leer("Elige empleado: ") - 1;

        Venta venta = new Venta();
        venta.setCliente(clientes.get(idxCliente));
        venta.setEmpleado(empleados.get(idxEmpleado));
        venta.setFecha(new Date());

        boolean agregarMas = true;
        while (agregarMas) {
            System.out.println("\nCelulares disponibles:");
            for (int i = 0; i < celulares.size(); i++) {
                Celular c = celulares.get(i);
                if (c.getStock() > 0) {
                    System.out.println((i + 1) + ". " + c.getMarca().getNombre() + " " + c.getModelo()
                            + " - " + ArchivoUtils.formatearMoneda(c.getPrecio())
                            + " - Stock: " + c.getStock());
                }
            }
            int idxCel = Entero.leer("Elige celular: ") - 1;

            if (idxCel < 0 || idxCel >= celulares.size()) {
                System.out.println("Opcion invalida");
                break;
            }

            Celular celularElegido = celulares.get(idxCel);
            int cantidad = Entero.leer("Cantidad: ");

            if (cantidad <= 0 || cantidad > celularElegido.getStock()) {
                System.out.println("Cantidad invalida o stock insuficiente.");
                continue;
            }

            ItemVenta item = new ItemVenta();
            item.setCelular(celularElegido);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(celularElegido.getPrecio());
            item.setSubtotal(cantidad * celularElegido.getPrecio());

            venta.agregarItem(item);

            int op = Entero.leer("Agregar otro celular? (1=Si, 2=No): ");
            agregarMas = (op == 1);
        }

        if (venta.getItems().isEmpty()) {
            System.out.println("No se agrego ningun celular. Venta cancelada.");
            return;
        }

        System.out.println("\n=== RESUMEN DE VENTA ===");
        System.out.println("Cliente: " + venta.getCliente().getNombre());
        System.out.println("Empleado: " + venta.getEmpleado().getNombre());
        System.out.println("\nItems:");
        for (ItemVenta item : venta.getItems()) {
            System.out.println("- " + item.getCelular().getMarca().getNombre()
                    + " " + item.getCelular().getModelo()
                    + " x" + item.getCantidad()
                    + " = " + ArchivoUtils.formatearMoneda(item.getSubtotal()));
        }
        System.out.println("\nSubtotal: " + ArchivoUtils.formatearMoneda(venta.getSubtotal()));
        System.out.println("IVA (19%): " + ArchivoUtils.formatearMoneda(venta.getIva()));
        System.out.println("TOTAL: " + ArchivoUtils.formatearMoneda(venta.getTotal()));

        int op = Entero.leer("\nConfirmar venta? (1=Si, 2=No): ");
        if (op == 1) {
            ventaDao.insertar(venta);
        } else {
            System.out.println("Venta cancelada.");
        }
    }

    public static void listar() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            System.out.println("\n--- LISTA DE VENTAS ---");
            for (Venta v : ventas) {
                System.out.println("\nVenta ID: " + v.getId());
                System.out.println("  Fecha: " + ArchivoUtils.formatearFecha(v.getFecha()));
                System.out.println("  Cliente: " + v.getCliente().getNombre());
                System.out.println("  Empleado: " + v.getEmpleado().getNombre());
                System.out.println("  Items:");
                for (ItemVenta item : v.getItems()) {
                    System.out.println("    - " + item.getCelular().getModelo()
                            + " x" + item.getCantidad()
                            + " = " + ArchivoUtils.formatearMoneda(item.getSubtotal()));
                }
                System.out.println("  Subtotal: " + ArchivoUtils.formatearMoneda(v.getSubtotal()));
                System.out.println("  IVA: " + ArchivoUtils.formatearMoneda(v.getIva()));
                System.out.println("  TOTAL: " + ArchivoUtils.formatearMoneda(v.getTotal()));
            }
        }
    }

    public static void buscar() {
        int id = Entero.leer("ID de la venta: ");
        Venta v = ventaDao.buscarPorId(id);
        if (v != null) {
            System.out.println("\nVenta ID: " + v.getId());
            System.out.println("  Fecha: " + ArchivoUtils.formatearFecha(v.getFecha()));
            System.out.println("  Cliente: " + v.getCliente().getNombre());
            System.out.println("  Empleado: " + v.getEmpleado().getNombre());
            System.out.println("  Items:");
            for (ItemVenta item : v.getItems()) {
                System.out.println("    - " + item.getCelular().getModelo()
                        + " x" + item.getCantidad()
                        + " = " + ArchivoUtils.formatearMoneda(item.getSubtotal()));
            }
            System.out.println("  Subtotal: " + ArchivoUtils.formatearMoneda(v.getSubtotal()));
            System.out.println("  IVA: " + ArchivoUtils.formatearMoneda(v.getIva()));
            System.out.println("  TOTAL: " + ArchivoUtils.formatearMoneda(v.getTotal()));
        } else {
            System.out.println("No se encontro la venta.");
        }
    }
}