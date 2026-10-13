
package modelo;

public class Celular {
    private int id;
    private Marca marca;
    private SistemaOperativo sistemaOperativo;
    private CategoriaGama gama;
    private String modelo;
    private double precio;
    private int stock;
    
    public Celular(){
        
    }

    public Celular(int id, Marca marca, SistemaOperativo sistemaOperativo, CategoriaGama gama, String modelo, double precio, int stock) {
        this.id = id;
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
        this.gama = gama;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public SistemaOperativo getSistemaOperativo() {
        return sistemaOperativo;
    }

    public CategoriaGama getGama() {
        return gama;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public void setGama(CategoriaGama gama) {
        this.gama = gama;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "Celular{" +
                "id=" + id +
                ", marca=" + marca +
                ", sistemaOperativo=" + sistemaOperativo +
                ", gama=" + gama +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
