package control;

import dao.ClienteDao;
import java.util.Date;
import java.util.List;
import modelo.Cliente;
import util.DatoInvalido;
import util.Validador;
import vistaValidaciones.Entero;
import vistaValidaciones.Texto;

public class ClienteControl {

    static ClienteDao clienteDao = new ClienteDao();

    public static void registrar() {
        System.out.println("--- REGISTRAR CLIENTE ---");
        String nombre = Texto.leer("Nombre: ");
        String identificacion = Texto.leer("Identificacion: ");

        if (clienteDao.buscarPorIdentificacion(identificacion) != null) {
            System.out.println("Ya existe un cliente con esa identificacion.");
            return;
        }

        String correo = Texto.leer("Correo: ");
        String telefono = Texto.leer("Telefono: ");

        try {
            Validador.validarNombre(nombre);
            Validador.validarIdentificacion(identificacion);
            Validador.validarCorreo(correo);
            Validador.validarTelefono(telefono);
        } catch (DatoInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        c.setFechaIngreso(new Date());
        c.setPuntos(0);

        clienteDao.insertar(c);
    }

    public static void listar() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes.");
        } else {
            System.out.println("--- CLIENTES ---");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + " - " + c.getNombre()
                        + " - " + c.getIdentificacion() + " - " + c.getCorreo());
            }
        }
    }

    public static void buscar() {
        int id = Entero.leer("ID: ");
        Cliente c = clienteDao.buscarPorId(id);
        if (c != null) {
            System.out.println(c.getNombre() + " - " + c.getIdentificacion()
                    + " - " + c.getCorreo());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizar() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para actualizar.");
            return;
        }
        System.out.println("--- CLIENTES ---");
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId() + " - " + c.getNombre()
                    + " - " + c.getIdentificacion() + " - " + c.getCorreo());
        }
        int id = Entero.leer("ID a actualizar (0 = cancelar): ");
        if (id == 0) {
            System.out.println("Cancelada");
            return;
        }

        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) {
            System.out.println("No existe.");
            return;
        }

        String correo = Texto.leer("Nuevo correo (Enter = mantener): ");
        if (!correo.isEmpty()) {
            try {
                Validador.validarCorreo(correo);
                c.setCorreo(correo);
            } catch (DatoInvalido e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        String tel = Texto.leer("Nuevo telefono (Enter = mantener): ");
        if (!tel.isEmpty()) {
            try {
                Validador.validarTelefono(tel);
                c.setTelefono(tel);
            } catch (DatoInvalido e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        clienteDao.actualizar(c);
    }

    public static void eliminar() {
        List<Cliente> clientes = clienteDao.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para eliminar.");
            return;
        }
        System.out.println("--- CLIENTES ---");
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId() + " - " + c.getNombre()
                    + " - " + c.getIdentificacion());
        }
        int id = Entero.leer("ID a eliminar (0 = cancelar): ");
        if (id == 0) {
            System.out.println("Cancelada");
            return;
        }

        Cliente c = clienteDao.buscarPorId(id);
        if (c == null) {
            System.out.println("No existe.");
            return;
        }

        int op = Entero.leer("Confirma eliminar a " + c.getNombre() + "? (1=Si, 2=No): ");
        if (op == 1) {
            try {
                clienteDao.eliminar(id);
            } catch (Exception e) {
                System.out.println("No se puede eliminar este cliente porque tiene ventas asociadas.");
            }
        } else {
            System.out.println("Cancelada");
        }
    }
}
