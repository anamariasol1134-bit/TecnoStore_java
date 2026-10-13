package modelo;
import java.util.Date;
public class Empleado extends Persona{
    private String cargo;
    private Date fechaIngreso;
    private long salario;
    public Empleado(){
        super(); 
    }
    public Empleado(String cargo, Date fechaIngreso, long salario, int id, String nombre, String identificacion, String correo, String telefono) {
        super(id, nombre, identificacion, correo, telefono);
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
    }
    public String getCargo() {
        return cargo;
    }
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public long getSalario() {
        return salario;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public void setSalario(long salario) {
        this.salario = salario;
    }
    @Override
    public String toString() {
        return "Empleado{"
                + super.toString()+
                ", cargo='" + cargo + '\'' +
                ", fechaIngreso=" + fechaIngreso+
                ", salario=" + salario
                + '}';
    }
}
