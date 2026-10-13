
package modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private Cliente cliente;
    private Empleado empleado;
    private Date fecha;
    private List<ItemVenta> items;
    private double subtotal;
    private double iva;
    private double total;
    
    public Venta(){
        this.items=new ArrayList<>();
    }

    public Venta(int id, Cliente cliente, Empleado empleado, Date fecha, double subtotal, double iva, double total) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.items=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public void agregarItem(ItemVenta item){
        items.add(item);
        recalcularTotales();
    }
    public void recalcularTotales(){
        double suma=0;
        for (ItemVenta item :items ){
            suma=suma + item.getSubtotal();
    }
        this.subtotal= suma;
        this.iva=this.subtotal* 0.19;
        this.total= this.subtotal + this.iva;
    }
      @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", empleado=" + empleado +
                ", fecha=" + fecha +
                ", items=" + items.size() + " items"+
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", total=" + total +
                '}';
    }
}
