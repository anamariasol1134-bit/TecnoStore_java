
package main;

import dao.CelularDao;
import dao.GamaDao;
import dao.MarcaDao;
import dao.SistemaOperativoDao;
import java.util.List;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;

public class TestCelular {
         public static void main(String[] args) {

        CelularDao celularDao = new CelularDao();
        MarcaDao marcaDao = new MarcaDao();
        SistemaOperativoDao soDao = new SistemaOperativoDao();
        GamaDao gamaDao = new GamaDao();

        // 1. Listar marcas disponibles
        System.out.println("=== MARCAS DISPONIBLES ===");
        List<Marca> marcas = marcaDao.listar();
        for (Marca m : marcas) {
            System.out.println(m.getId() + " - " + m.getNombre());
        }

        // 2. Listar sistemas operativos
        System.out.println("\n=== SISTEMAS OPERATIVOS ===");
        List<SistemaOperativo> sistemas = soDao.listar();
        for (SistemaOperativo s : sistemas) {
            System.out.println(s.getId() + " - " + s.getNombre() + " " + s.getVersion());
        }

        // 3. Listar gamas
        System.out.println("\n=== GAMAS DISPONIBLES ===");
        List<CategoriaGama> gamas = gamaDao.listar();
        for (CategoriaGama g : gamas) {
            System.out.println(g);
        }

        // 4. Insertar 3 celulares de prueba
        System.out.println("\n=== INSERTANDO CELULARES ===");

        // Celular 1: Samsung Galaxy S24
        Celular c1 = new Celular();
        c1.setMarca(marcas.get(0));              // Samsung
        c1.setSistemaOperativo(sistemas.get(0)); // Android 14
        c1.setGama(CategoriaGama.ALTA);
        c1.setModelo("Galaxy S24");
        c1.setPrecio(3800000);
        c1.setStock(10);
        celularDao.insertar(c1);

        // Celular 2: Apple iPhone 15
        Celular c2 = new Celular();
        c2.setMarca(marcas.get(1));              // Apple
        c2.setSistemaOperativo(sistemas.get(2)); // iOS 17
        c2.setGama(CategoriaGama.ALTA);
        c2.setModelo("iPhone 15");
        c2.setPrecio(4500000);
        c2.setStock(5);
        celularDao.insertar(c2);

        // Celular 3: Xiaomi Redmi Note 13
        Celular c3 = new Celular();
        c3.setMarca(marcas.get(2));              // Xiaomi
        c3.setSistemaOperativo(sistemas.get(1)); // Android 13
        c3.setGama(CategoriaGama.MEDIA);
        c3.setModelo("Redmi Note 13");
        c3.setPrecio(900000);
        c3.setStock(15);
        celularDao.insertar(c3);

        // 5. Listar todos los celulares
        System.out.println("\n=== LISTA DE CELULARES ===");
        List<Celular> celulares = celularDao.listar();
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

        // 6. Buscar uno por id
        System.out.println("=== BUSCANDO CELULAR CON id=2 ===");
        Celular encontrado = celularDao.buscarPorId(2);
        if (encontrado != null) {
            System.out.println("Encontrado: " + encontrado.getMarca().getNombre()
                                + " " + encontrado.getModelo());
        } else {
            System.out.println("No se encontro");
        }
    }
}
