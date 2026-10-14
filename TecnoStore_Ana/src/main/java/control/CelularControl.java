package control;

import dao.CelularDao;
import dao.GamaDao;
import dao.MarcaDao;
import dao.SistemaOperativoDao;
import java.util.List;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;
import util.ArchivoUtils;
import util.DatoInvalido;
import util.Validador;
import vistaValidaciones.Decimal;
import vistaValidaciones.Entero;
import vistaValidaciones.Texto;
import patron.FactoryCelular;

public class CelularControl {

    static CelularDao celularDao = new CelularDao();
    static MarcaDao marcaDao = new MarcaDao();
    static SistemaOperativoDao soDao = new SistemaOperativoDao();
    static GamaDao gamaDao = new GamaDao();

    public static void registrar() {
        List<Marca> marcas = marcaDao.listar();
        List<SistemaOperativo> sistemas = soDao.listar();
        List<CategoriaGama> gamas = gamaDao.listar();

        if (marcas.isEmpty() || sistemas.isEmpty() || gamas.isEmpty()) {
            System.out.println("Faltan datos (marcas/SO/gamas).");
            return;
        }

        System.out.println("--- REGISTRAR CELULAR ---");

        System.out.println("Marcas:");
        for (int i = 0; i < marcas.size(); i++) {
            System.out.println((i + 1) + ". " + marcas.get(i).getNombre());
        }
        int idxMarca = Entero.leer("Elige: ") - 1;

        System.out.println("Sistemas:");
        for (int i = 0; i < sistemas.size(); i++) {
            SistemaOperativo s = sistemas.get(i);
            System.out.println((i + 1) + ". " + s.getNombre() + " " + s.getVersion());
        }
        int idxSo = Entero.leer("Elige: ") - 1;

        System.out.println("Gamas:");
        for (int i = 0; i < gamas.size(); i++) {
            System.out.println((i + 1) + ". " + gamas.get(i));
        }
        int idxGama = Entero.leer("Elige: ") - 1;

        String modelo = Texto.leer("Modelo: ");
        double precio = Decimal.leer("Precio: ");
        int stock = Entero.leer("Stock: ");

        try {
            Validador.validarNombre(modelo);
            Validador.validarPrecio(precio);
            Validador.validarStock(stock);
        } catch (DatoInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

            Celular c = FactoryCelular.crear(
            marcas.get(idxMarca),
            sistemas.get(idxSo),
            gamas.get(idxGama),
            modelo, precio, stock);
            celularDao.insertar(c);
    }

    public static void listar() {
        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares.");
        } else {
            System.out.println("--- CELULARES ---");
            for (Celular c : celulares) {
                System.out.println("ID: " + c.getId() + " - " + c.getMarca().getNombre()
                        + " " + c.getModelo()
                        + " - " + ArchivoUtils.formatearMoneda(c.getPrecio())
                        + " - Stock: " + c.getStock());
            }
        }
    }

    public static void buscar() {
        int id = Entero.leer("ID: ");
        Celular c = celularDao.buscarPorId(id);
        if (c != null) {
            System.out.println(c.getMarca().getNombre() + " " + c.getModelo()
                    + " - " + ArchivoUtils.formatearMoneda(c.getPrecio())
                    + " - Stock: " + c.getStock());
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizar() {
        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares para actualizar.");
            return;
        }
        System.out.println("--- CELULARES ---");
        for (Celular c : celulares) {
            System.out.println("ID: " + c.getId() + " - " + c.getMarca().getNombre()
                    + " " + c.getModelo()
                    + " - " + ArchivoUtils.formatearMoneda(c.getPrecio())
                    + " - Stock: " + c.getStock());
        }
        int id = Entero.leer("ID a actualizar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        double p = Decimal.leer("Nuevo precio (0 = mantener): ");
        if (p > 0) {
            try {
                Validador.validarPrecio(p);
                c.setPrecio(p);
            } catch (DatoInvalido e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        int s = Entero.leer("Nuevo stock (-1 = mantener): ");
        if (s >= 0) {
            try {
                Validador.validarStock(s);
                c.setStock(s);
            } catch (DatoInvalido e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        celularDao.actualizar(c);
    }

    public static void eliminar() {
        List<Celular> celulares = celularDao.listar();
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares para eliminar.");
            return;
        }
        System.out.println("--- CELULARES ---");
        for (Celular c : celulares) {
            System.out.println("ID: " + c.getId() + " - " + c.getMarca().getNombre()
                    + " " + c.getModelo() + " - Stock: " + c.getStock());
        }
        int id = Entero.leer("ID a eliminar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Celular c = celularDao.buscarPorId(id);
        if (c == null) { System.out.println("No existe."); return; }

        int op = Entero.leer("Confirma eliminar " + c.getModelo() + "? (1=Si, 2=No): ");
        if (op == 1) {
            try {
                celularDao.eliminar(id);
            } catch (Exception e) {
                System.out.println("No se puede eliminar este celular porque tiene ventas asociadas.");
            }
        } else {
            System.out.println("Cancelada");
        }
    }
}