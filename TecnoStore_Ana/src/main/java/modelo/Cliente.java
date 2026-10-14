package modelo;

import java.util.Date;

public class Cliente extends Persona {

    private Date fechaIngreso;
    private int puntos;

    public Cliente() {
        super();
    }

    public Cliente(Date fechaIngreso, int puntos, int id, String nombre, String identificacion, String correo, String telefono) {
        super(id, nombre, identificacion, correo, telefono);
        this.fechaIngreso = fechaIngreso;
        this.puntos = puntos;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Cliente{"
                + super.toString()
                + ", fechaIngreso=" + fechaIngreso
                + ", puntos=" + puntos
                + '}';
    }

}
