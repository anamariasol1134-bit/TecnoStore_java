package control;

import dao.MarcaDao;
import java.util.List;
import modelo.Marca;
import util.DatoInvalido;
import util.Validador;
import vistaValidaciones.Entero;
import vistaValidaciones.Texto;

public class MarcaControl {

    static MarcaDao marcaDao = new MarcaDao();

    public static void registrar() {
        String nombre = Texto.leer("Nombre de la marca: ");
        try {
            Validador.validarNombre(nombre);
        } catch (DatoInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        Marca m = new Marca();
        m.setNombre(nombre);
        marcaDao.insertar(m);
    }

    public static void listar() {
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

    public static void buscar() {
        int id = Entero.leer("ID: ");
        Marca m = marcaDao.buscarPorId(id);
        if (m != null) {
            System.out.println("Encontrada: " + m.getNombre());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizar() {
        List<Marca> marcas = marcaDao.listar();
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas para actualizar.");
            return;
        }
        System.out.println("\n--- MARCAS ---");
        for (Marca m : marcas) {
            System.out.println("ID: " + m.getId() + " - " + m.getNombre());
        }
        int id = Entero.leer("ID a actualizar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Marca m = marcaDao.buscarPorId(id);
        if (m == null) { System.out.println("No existe."); return; }

        String nuevo = Texto.leer("Nuevo nombre: ");
        try {
            Validador.validarNombre(nuevo);
        } catch (DatoInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        m.setNombre(nuevo);
        marcaDao.actualizar(m);
    }

    public static void eliminar() {
        List<Marca> marcas = marcaDao.listar();
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas para eliminar.");
            return;
        }
        System.out.println("\n--- MARCAS ---");
        for (Marca m : marcas) {
            System.out.println("ID: " + m.getId() + " - " + m.getNombre());
        }
        int id = Entero.leer("ID a eliminar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Marca m = marcaDao.buscarPorId(id);
        if (m == null) { System.out.println("No existe."); return; }

        int op = Entero.leer("Confirma eliminar " + m.getNombre() + "? (1=Si, 2=No): ");
        if (op == 1) {
            try {
                marcaDao.eliminar(id);
            } catch (Exception e) {
                System.out.println("No se puede eliminar esta marca porque tiene celulares asociados.");
            }
        } else {
            System.out.println("Cancelada");
        }
    }
}