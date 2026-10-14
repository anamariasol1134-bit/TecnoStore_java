package main;

import dao.CelularDao;
import dao.ClienteDao;
import dao.GamaDao;
import dao.MarcaDao;
import dao.SistemaOperativoDao;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Cliente;
import modelo.Marca;
import modelo.SistemaOperativo;

public class MenuPrincipal {

    static Scanner sc = new Scanner(System.in);

    static MarcaDao marcaDao = new MarcaDao();
    static SistemaOperativoDao soDao = new SistemaOperativoDao();
    static GamaDao gamaDao = new GamaDao();
    static CelularDao celularDao = new CelularDao();
    static ClienteDao clienteDao = new ClienteDao();

    public static void main(String[] args) {

        int opcion = 0;

        while (opcion != 5) {
            System.out.println("\n===== TECNOSTORE =====");
            System.out.println("1. Gestion de Marcas");
            System.out.println("2. Gestion de Celulares");
            System.out.println("3. Gestion de Clientes");
            System.out.println("4. Gestion de Ventas");
            System.out.println("5. Salir");
            System.out.print("Elige una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: menuMarcas(); break;
                case 2: menuCelulares(); break;
                case 3: menuClientes(); break;
                case 4: System.out.println("Proximamente..."); break;
                case 5: System.out.println("Hasta luego!"); break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    // ==========================================================
    // MENU DE MARCAS
    // ==========================================================

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
                case 6: break;
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
            System.out.println("No hay marcas registradas.");
        } else {
            System.out.println("\n--- LISTA DE MARCAS ---");
            for (Marca m : marcas) {
                System.out.println("ID: " + m.getId() + " - " + m.getNombre());
            }
        }
    }

    public static void buscarMarca() {
        System.out.print("ID de la marca: ");
        int id = sc.nextInt();
        sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m != null) {
            System.out.println("Encontrada: " + m.getId() + " - " + m.getNombre());
        } else {
            System.out.println("No se encontro ninguna marca con ese id.");
        }
    }

    public static void actualizarMarca() {
        System.out.print("ID de la marca a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m == null) {
            System.out.println("No se encontro la marca con ese id");
            return;
        }
        System.out.println("Marca actual: " + m.getNombre());
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = sc.nextLine();
        m.setNombre(nuevoNombre);
        marcaDao.actualizar(m);
    }

    public static void eliminarMarca() {
        System.out.print("ID de la marca a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Marca m = marcaDao.buscarPorId(id);
        if (m == null) {
            System.out.println("No se encontro la marca con ese id");
            return;
        }
        System.out.println("Va a eliminar: " + m.getNombre());
        System.out.print("Confirma? (s/n): ");
        String confirmacion = sc.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            marcaDao.eliminar(id);
        } else {
            System.out.println("Operacion cancelada");
        }
    }

    // ==========================================================
    // MENU DE CELULARES
    // ==========================================================

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
            opcion = sc.nextInt();
            sc.nextLine();
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
        if (marcas.isEmpty()) { System.out.println("Primero registre marcas."); return; }
        List<SistemaOperativo> sistemas = soDao.listar();
        if (sistemas.isEmpty()) { System.out.println("No hay SO."); return; }
        List<CategoriaGama> gamas = gamaDao.listar();
        if (gamas.isEmpty()) { System.out.println("No hay gamas."); return; }

        System.out.println("\n--- REGISTRAR CELULAR ---");
        System.out.println("\nMarcas disponibles:");
        for (int i = 0; i < marcas.size(); i++) {
            System.out.println((i + 1) + ". " + marcas.get(i).getNombre());
        }
        System.out.print("Elige marca: ");
        int idxMarca = sc.nextInt() - 1; sc.nextLine();

        System.out.println("\nSistemas disponibles:");
        for (int i = 0; i < sistemas.size(); i++) {
            SistemaOperativo s = sistemas.get(i);
            System.out.println((i + 1) + ". " + s.getNombre() + " " + s.getVersion());
        }
        System.out.print("Elige sistema: ");
        int idxSo = sc.nextInt() - 1; sc.nextLine();

        System.out.println("\nGamas disponibles:");
        for (int i = 0; i < gamas.size(); i++) {
            System.out.println((i + 1) + ". " + gamas.get(i));
        }
        System.out.print("Elige gama: ");
        int idxGama = sc.nextInt() - 1; sc.nextLine();

        System.out.print("\nModelo: ");
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
            System.out.println("\n--- LISTA DE CELULARES ---");
            for (Celular c : celulares) {
                System.out.println("ID: " + c.getId());
                System.out.println("  Marca: " + c.getMarca().getNombre());
                System.out.println("  Modelo: " + c.getModelo());
                System.out.println("  Sistema: " + c.getSistemaOperativo().getNombre()
                                    + " " + c.getSistemaOperativo().getVersion());
                System.out.println("  Gama: " + c.getGama());
                System.out.println("  Precio: " + c.getPrecio());
                System.out.println("  Stock: " + c.getStock());
                System.out.println();
            }
        }
    }

    public static void buscarCelular() {
        System.out.print("ID del celular: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c != null) {
            System.out.println("Encontrado: " + c.getMarca().getNombre() + " " + c.getModelo());
            System.out.println("  Precio: " + c.getPrecio() + " - Stock: " + c.getStock());
        } else {
            System.out.println("No se encontro.");
        }
    }

    public static void actualizarCelular() {
        System.out.print("ID del celular: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        System.out.println("Actual: " + c.getMarca().getNombre() + " " + c.getModelo());

        System.out.print("Nuevo precio (0 = mantener): ");
        double precio = sc.nextDouble(); sc.nextLine();
        if (precio > 0) c.setPrecio(precio);

        System.out.print("Nuevo stock (-1 = mantener): ");
        int stock = sc.nextInt(); sc.nextLine();
        if (stock >= 0) c.setStock(stock);

        celularDao.actualizar(c);
    }

    public static void eliminarCelular() {
        System.out.print("ID del celular a eliminar: ");
        int id = sc.nextInt(); sc.nextLine();
        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        System.out.println("Va a eliminar: " + c.getMarca().getNombre() + " " + c.getModelo());
        System.out.print("Confirma? (s/n): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("s")) {
            celularDao.eliminar(id);
        } else {
            System.out.println("Cancelada");
        }
    }

    // ==========================================================
    // MENU DE CLIENTES
    // ==========================================================

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
            opcion = sc.nextInt();
            sc.nextLine();
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

        // Validar que no exista
        Cliente existente = clienteDao.buscarPorIdentificacion(identificacion);
        if (existente != null) {
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
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\n--- LISTA DE CLIENTES ---");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId());
                System.out.println("  Nombre: " + c.getNombre());
                System.out.println("  Identificacion: " + c.getIdentificacion());
                System.out.println("  Correo: " + c.getCorreo());
                System.out.println("  Telefono: " + c.getTelefono());
                System.out.println("  Fecha ingreso: " + c.getFechaIngreso());
                System.out.println("  Puntos: " + c.getPuntos());
                System.out.println();
            }
        }
    }

    public static void buscarCliente() {
        System.out.print("ID del cliente: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c != null) {
            System.out.println("Encontrado:");
            System.out.println("  Nombre: " + c.getNombre());
            System.out.println("  Identificacion: " + c.getIdentificacion());
            System.out.println("  Correo: " + c.getCorreo());
            System.out.println("  Telefono: " + c.getTelefono());
            System.out.println("  Puntos: " + c.getPuntos());
        } else {
            System.out.println("No se encontro.");
        }
    }

    public static void actualizarCliente() {
        System.out.print("ID del cliente: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        System.out.println("Actual: " + c.getNombre());
        System.out.print("Nuevo correo (Enter = mantener): ");
        String correo = sc.nextLine();
        if (!correo.isEmpty()) c.setCorreo(correo);

        System.out.print("Nuevo telefono (Enter = mantener): ");
        String telefono = sc.nextLine();
        if (!telefono.isEmpty()) c.setTelefono(telefono);

        clienteDao.actualizar(c);
    }

    public static void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        int id = sc.nextInt(); sc.nextLine();
        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        System.out.println("Va a eliminar a: " + c.getNombre());
        System.out.print("Confirma? (s/n): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("s")) {
            clienteDao.eliminar(id);
        } else {
            System.out.println("Cancelada");
        }
    }
}