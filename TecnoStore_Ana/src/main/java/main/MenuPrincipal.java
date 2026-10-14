package main;

import dao.CelularDao;
import dao.ClienteDao;
import dao.EmpleadoDao;
import dao.GamaDao;
import dao.MarcaDao;
import dao.SistemaOperativoDao;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Marca;
import modelo.ItemVenta;
import modelo.SistemaOperativo;
import dao.VentaDao;
import modelo.Venta;
import util.ArchivoUtils;
import util.ReporteUtils;
public class MenuPrincipal {

    static Scanner sc = new Scanner(System.in);
    static VentaDao ventaDao = new VentaDao();
    static MarcaDao marcaDao = new MarcaDao();
    static SistemaOperativoDao soDao = new SistemaOperativoDao();
    static GamaDao gamaDao = new GamaDao();
    static CelularDao celularDao = new CelularDao();
    static ClienteDao clienteDao = new ClienteDao();
    static EmpleadoDao empleadoDao = new EmpleadoDao();

    public static void main(String[] args) {

        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n===== TECNOSTORE =====");
            System.out.println("1. Gestion de Marcas");
            System.out.println("2. Gestion de Celulares");
            System.out.println("3. Gestion de Empleados");
            System.out.println("4. Gestion de Clientes");
            System.out.println("5. Gestion de Ventas");
            System.out.println("6. reportes");
            System.out.println("7. salir");
            System.out.print("Elige una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: menuMarcas(); break;
                case 2: menuCelulares(); break;
                case 3: menuEmpleados(); break;
                case 4: menuClientes(); break;
                case 5: menuVentas(); break;
                case 6: menuReportes(); break;
                case 7: System.out.println("Hasta luego!"); break;
                default: System.out.println("Opcion invalida");
            }
        }
    }


    public static void menuMarcas() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE MARCAS ---");
            System.out.println("1. Registrar marca");
            System.out.println("2. Listar marcas");
            System.out.println("3. Buscar marca por id");
            System.out.println("4. Actualizar marca");
            System.out.println("5. Eliminar marca");
            System.out.println("6. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1: registrarMarca(); break;
                case 2: listarMarcas(); break;
                case 3: buscarMarca(); break;
                case 4: actualizarMarca(); break;
                case 5: eliminarMarca(); break;
                case 6: System.out.println("Hasta luego!"); break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void registrarMarca() {
        System.out.print("Nombre de la marca: ");
        String nombre = sc.nextLine();
        Marca m = new Marca();
        m.setNombre(nombre);
        marcaDao.insertar(m);
    }

    public static void listarMarcas() {
        List<Marca> marcas = marcaDao.listar();
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas.");
        } else {
            System.out.println("\n--- LISTA DE MARCAS ---");
            for (Marca m : marcas) {
                System.out.println("ID: " + m.getId() + " - " + m.getNombre());
            }
        }
    }

    public static void buscarMarca() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m != null) {
            System.out.println("Encontrada: " + m.getNombre());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizarMarca() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m == null) { System.out.println("No existe."); return; }
        System.out.print("Nuevo nombre: ");
        m.setNombre(sc.nextLine());
        marcaDao.actualizar(m);
    }

    public static void eliminarMarca() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m == null) { System.out.println("No existe."); return; }
        System.out.print("Confirma? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) marcaDao.eliminar(id);
        else System.out.println("Cancelada");
    }

    public static void menuCelulares() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE CELULARES ---");
            System.out.println("1. Registrar celular");
            System.out.println("2. Listar celulares");
            System.out.println("3. Buscar celular por id");
            System.out.println("4. Actualizar celular");
            System.out.println("5. Eliminar celular");
            System.out.println("6. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt(); sc.nextLine();
            switch (opcion) {
                case 1: registrarCelular(); break;
                case 2: listarCelulares(); break;
                case 3: buscarCelular(); break;
                case 4: actualizarCelular(); break;
                case 5: eliminarCelular(); break;
                case 6: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void registrarCelular() {
        List<Marca> marcas = marcaDao.listar();
        List<SistemaOperativo> sistemas = soDao.listar();
        List<CategoriaGama> gamas = gamaDao.listar();

        if (marcas.isEmpty() || sistemas.isEmpty() || gamas.isEmpty()) {
            System.out.println("Faltan datos (marcas/SO/gamas).");
            return;
        }

        System.out.println("\n--- REGISTRAR CELULAR ---");
        System.out.println("\nMarcas:");
        for (int i = 0; i < marcas.size(); i++) System.out.println((i+1) + ". " + marcas.get(i).getNombre());
        System.out.print("Elige: ");
        int idxMarca = sc.nextInt() - 1; sc.nextLine();

        System.out.println("\nSistemas:");
        for (int i = 0; i < sistemas.size(); i++) {
            SistemaOperativo s = sistemas.get(i);
            System.out.println((i+1) + ". " + s.getNombre() + " " + s.getVersion());
        }
        System.out.print("Elige: ");
        int idxSo = sc.nextInt() - 1; sc.nextLine();

        System.out.println("\nGamas:");
        for (int i = 0; i < gamas.size(); i++) System.out.println((i+1) + ". " + gamas.get(i));
        System.out.print("Elige: ");
        int idxGama = sc.nextInt() - 1; sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble(); sc.nextLine();
        System.out.print("Stock: ");
        int stock = sc.nextInt(); sc.nextLine();

        Celular c = new Celular();
        c.setMarca(marcas.get(idxMarca));
        c.setSistemaOperativo(sistemas.get(idxSo));
        c.setGama(gamas.get(idxGama));
        c.setModelo(modelo);
        c.setPrecio(precio);
        c.setStock(stock);
        celularDao.insertar(c);
    }

    public static void listarCelulares() {
        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares.");
        } else {
            System.out.println("\n--- CELULARES ---");
            for (Celular c : celulares) {
                System.out.println("ID: " + c.getId() + " - " + c.getMarca().getNombre()
                                    + " " + c.getModelo() + " - $" + c.getPrecio()
                                    + " - Stock: " + c.getStock());
            }
        }
    }

    public static void buscarCelular() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c != null) {
            System.out.println(c.getMarca().getNombre() + " " + c.getModelo()
                                + " - $" + c.getPrecio() + " - Stock: " + c.getStock());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizarCelular() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }
        System.out.print("Nuevo precio (0=mantener): ");
        double p = sc.nextDouble(); sc.nextLine();
        if (p > 0) c.setPrecio(p);
        System.out.print("Nuevo stock (-1=mantener): ");
        int s = sc.nextInt(); sc.nextLine();
        if (s >= 0) c.setStock(s);
        celularDao.actualizar(c);
    }

    public static void eliminarCelular() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }
        System.out.print("Confirma? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) celularDao.eliminar(id);
        else System.out.println("Cancelada");
    }

    public static void menuEmpleados() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Buscar empleado por id");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("6. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt(); sc.nextLine();
            switch (opcion) {
                case 1: registrarEmpleado(); break;
                case 2: listarEmpleados(); break;
                case 3: buscarEmpleado(); break;
                case 4: actualizarEmpleado(); break;
                case 5: eliminarEmpleado(); break;
                case 6: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void registrarEmpleado() {
        System.out.println("\n--- REGISTRAR EMPLEADO ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Identificacion: ");
        String identificacion = sc.nextLine();

        if (empleadoDao.buscarPorIdentificacion(identificacion) != null) {
            System.out.println("Ya existe un empleado con esa identificacion.");
            return;
        }

        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Telefono: ");
        String telefono = sc.nextLine();
        System.out.print("Cargo: ");
        String cargo = sc.nextLine();
        System.out.print("Salario: ");
        double salario = sc.nextDouble(); sc.nextLine();

        Empleado e = new Empleado();
        e.setNombre(nombre);
        e.setIdentificacion(identificacion);
        e.setCorreo(correo);
        e.setTelefono(telefono);
        e.setCargo(cargo);
        e.setFechaIngreso(new Date());
        e.setSalario(salario);

        empleadoDao.insertar(e);
    }

    public static void listarEmpleados() {
        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados.");
        } else {
            System.out.println("\n--- EMPLEADOS ---");
            for (Empleado e : empleados) {
                System.out.println("ID: " + e.getId() + " - " + e.getNombre()
                                    + " - " + e.getCargo() + " - $" + e.getSalario());
            }
        }
    }

    public static void buscarEmpleado() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Empleado e = empleadoDao.buscarPorId(id);
        if (e != null) {
            System.out.println(e.getNombre() + " - " + e.getCargo() + " - $" + e.getSalario());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizarEmpleado() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Empleado e = empleadoDao.buscarPorId(id);
        if (e == null) { System.out.println("No existe."); return; }

        System.out.print("Nuevo cargo (Enter=mantener): ");
        String c = sc.nextLine();
        if (!c.isEmpty()) e.setCargo(c);

        System.out.print("Nuevo salario (0=mantener): ");
        double s = sc.nextDouble(); sc.nextLine();
        if (s > 0) e.setSalario(s);

        empleadoDao.actualizar(e);
    }

    public static void eliminarEmpleado() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Empleado e = empleadoDao.buscarPorId(id);
        if (e == null) { System.out.println("No existe."); return; }
        System.out.print("Confirma? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) empleadoDao.eliminar(id);
        else System.out.println("Cancelada");
    }

    public static void menuClientes() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por id");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt(); sc.nextLine();
            switch (opcion) {
                case 1: registrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
                case 4: actualizarCliente(); break;
                case 5: eliminarCliente(); break;
                case 6: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void registrarCliente() {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Identificacion: ");
        String identificacion = sc.nextLine();

        if (clienteDao.buscarPorIdentificacion(identificacion) != null) {
            System.out.println("Ya existe un cliente con esa identificacion.");
            return;
        }

        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Telefono: ");
        String telefono = sc.nextLine();

        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        c.setFechaIngreso(new Date());
        c.setPuntos(0);

        clienteDao.insertar(c);
    }

    public static void listarClientes() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes.");
        } else {
            System.out.println("\n--- CLIENTES ---");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + " - " + c.getNombre()
                                    + " - " + c.getIdentificacion() + " - " + c.getCorreo());
            }
        }
    }

    public static void buscarCliente() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c != null) {
            System.out.println(c.getNombre() + " - " + c.getIdentificacion() + " - " + c.getCorreo());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizarCliente() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }
        System.out.print("Nuevo correo (Enter=mantener): ");
        String correo = sc.nextLine();
        if (!correo.isEmpty()) c.setCorreo(correo);
        System.out.print("Nuevo telefono (Enter=mantener): ");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) c.setTelefono(tel);
        clienteDao.actualizar(c);
    }

    public static void eliminarCliente() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }
        System.out.print("Confirma? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) clienteDao.eliminar(id);
        else System.out.println("Cancelada");
    }
     public static void menuVentas() {
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n--- GESTION DE VENTAS ---");
            System.out.println("1. Registrar venta");
            System.out.println("2. Listar ventas");
            System.out.println("3. Buscar venta por id");
            System.out.println("4. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: registrarVenta(); break;
                case 2: listarVentas(); break;
                case 3: buscarVenta(); break;
                case 4: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void registrarVenta() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares en el catalogo.");
            return;
        }

        System.out.println("\n--- REGISTRAR VENTA ---");

        // 1. Elegir cliente
        System.out.println("\nClientes disponibles:");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getIdentificacion() + ")");
        }
        System.out.print("Elige cliente: ");
        int idxCliente = sc.nextInt() - 1;
        sc.nextLine();
        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Opcion invalida");
            return;
        }

        // 2. Elegir empleado
        System.out.println("\nEmpleados disponibles:");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " - " + e.getCargo());
        }
        System.out.print("Elige empleado: ");
        int idxEmpleado = sc.nextInt() - 1;
        sc.nextLine();
        if (idxEmpleado < 0 || idxEmpleado >= empleados.size()) {
            System.out.println("Opcion invalida");
            return;
        }

        // 3. Crear venta
        Venta venta = new Venta();
        venta.setCliente(clientes.get(idxCliente));
        venta.setEmpleado(empleados.get(idxEmpleado));
        venta.setFecha(new Date());

        // 4. Agregar items
        boolean agregarMas = true;
        while (agregarMas) {
            System.out.println("\nCelulares disponibles (con stock):");
            for (int i = 0; i < celulares.size(); i++) {
                Celular c = celulares.get(i);
                if (c.getStock() > 0) {
                    System.out.println((i + 1) + ". " + c.getMarca().getNombre() + " " + c.getModelo()
                                        + " - $" + c.getPrecio() + " - Stock: " + c.getStock());
                }
            }
            System.out.print("Elige celular: ");
            int idxCel = sc.nextInt() - 1;
            sc.nextLine();
            if (idxCel < 0 || idxCel >= celulares.size()) {
                System.out.println("Opcion invalida");
                break;
            }

            Celular celularElegido = celulares.get(idxCel);

            System.out.print("Cantidad: ");
            int cantidad = sc.nextInt();
            sc.nextLine();

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

            System.out.print("¿Agregar otro celular? (s/n): ");
            agregarMas = sc.nextLine().equalsIgnoreCase("s");
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
                                + " = $" + item.getSubtotal());
        }
        System.out.println("\nSubtotal: $" + venta.getSubtotal());
        System.out.println("IVA (19%): $" + venta.getIva());
        System.out.println("TOTAL: $" + venta.getTotal());

        System.out.print("\n¿Confirmar venta? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            ventaDao.insertar(venta);
        } else {
            System.out.println("Venta cancelada.");
        }
    }

    public static void listarVentas() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            System.out.println("\n--- LISTA DE VENTAS ---");
            for (Venta v : ventas) {
                System.out.println("\nVenta ID: " + v.getId());
                System.out.println("  Fecha: " + v.getFecha());
                System.out.println("  Cliente: " + v.getCliente().getNombre());
                System.out.println("  Empleado: " + v.getEmpleado().getNombre());
                System.out.println("  Items:");
                for (ItemVenta item : v.getItems()) {
                    System.out.println("    - " + item.getCelular().getModelo()
                                        + " x" + item.getCantidad()
                                        + " = $" + item.getSubtotal());
                }
                System.out.println("  Subtotal: $" + v.getSubtotal());
                System.out.println("  IVA: $" + v.getIva());
                System.out.println("  TOTAL: $" + v.getTotal());
            }
        }
    }

    public static void buscarVenta() {
        System.out.print("ID de la venta: ");
        int id = sc.nextInt();
        sc.nextLine();

        Venta v = ventaDao.buscarPorId(id);
        if (v != null) {
            System.out.println("\nVenta ID: " + v.getId());
            System.out.println("  Fecha: " + v.getFecha());
            System.out.println("  Cliente: " + v.getCliente().getNombre());
            System.out.println("  Empleado: " + v.getEmpleado().getNombre());
            System.out.println("  Items:");
            for (ItemVenta item : v.getItems()) {
                System.out.println("    - " + item.getCelular().getModelo()
                                    + " x" + item.getCantidad()
                                    + " = $" + item.getSubtotal());
            }
            System.out.println("  Subtotal: $" + v.getSubtotal());
            System.out.println("  IVA: $" + v.getIva());
            System.out.println("  TOTAL: $" + v.getTotal());
        } else {
            System.out.println("No se encontro la venta.");
        }
        
}
    public static void menuReportes() {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n--- REPORTES ---");
            System.out.println("1. Celulares con stock bajo");
            System.out.println("2. Top 3 celulares mas vendidos");
            System.out.println("3. Ventas totales por mes");
            System.out.println("4. Generar reporte_ventas.txt");
            System.out.println("5. Volver");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: reporteStockBajo(); break;
                case 2: reporteTop3(); break;
                case 3: reporteVentasPorMes(); break;
                case 4: reporteArchivo(); break;
                case 5: break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    public static void reporteStockBajo() {
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

    public static void reporteTop3() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        java.util.Map<String, Integer> top3 = ReporteUtils.top3CelularesMasVendidos(ventas);

        System.out.println("\n--- TOP 3 CELULARES MAS VENDIDOS ---");
        int pos = 1;
        for (java.util.Map.Entry<String, Integer> e : top3.entrySet()) {
            System.out.println(pos + ". " + e.getKey() + " - " + e.getValue() + " unidades");
            pos++;
        }
    }

    public static void reporteVentasPorMes() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        java.util.Map<java.time.Month, Double> porMes = ReporteUtils.ventasTotalesPorMes(ventas);

        System.out.println("\n--- VENTAS TOTALES POR MES ---");
        for (java.util.Map.Entry<java.time.Month, Double> e : porMes.entrySet()) {
            System.out.println(e.getKey() + ": $" + e.getValue());
        }
    }

    public static void reporteArchivo() {
        List<Venta> ventas = ventaDao.listar();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para generar reporte.");
            return;
        }
        ArchivoUtils.generarReporteVentas(ventas, "reporte_ventas.txt");
    }}