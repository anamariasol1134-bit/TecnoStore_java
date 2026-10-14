package patron;

import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;

public class FactoryCelular {

    public static Celular crear(Marca marca, SistemaOperativo so,
                                CategoriaGama gama, String modelo,
                                double precio, int stock) {
        Celular c = new Celular();
        c.setMarca(marca);
        c.setSistemaOperativo(so);
        c.setGama(gama);
        c.setModelo(modelo);
        c.setPrecio(precio);
        c.setStock(stock);
        return c;
    }
}
