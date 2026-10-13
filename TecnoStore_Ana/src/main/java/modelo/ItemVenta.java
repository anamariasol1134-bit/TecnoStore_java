
package modelo;

public class ItemVenta {
    private int id;
    private Celular celular;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    
    public ItemVenta(){
        
    }

    public ItemVenta(int id, Celular celular, int cantidad, double precioUnitario, double subtotal) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public Celular getCelular() {
        return celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    @Override
    public String toString() {
        return "Celular{" +
                "id=" + id +
                ", celular=" + celular +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
