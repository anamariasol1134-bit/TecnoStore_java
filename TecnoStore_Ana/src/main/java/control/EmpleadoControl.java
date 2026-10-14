package control;

import dao.EmpleadoDao;
import java.util.Date;
import java.util.List;
import modelo.Empleado;
import util.ArchivoUtils;
import util.DatoInvalido;
import util.Validador;
import vistaValidaciones.Decimal;
import vistaValidaciones.Entero;
import vistaValidaciones.Texto;

public class EmpleadoControl {

    static EmpleadoDao empleadoDao = new EmpleadoDao();

    public static void registrar() {
        System.out.println("\n--- REGISTRAR EMPLEADO ---");
        String nombre = Texto.leer("Nombre: ");
        String identificacion = Texto.leer("Identificacion: ");

        if (empleadoDao.buscarPorIdentificacion(identificacion) != null) {
            System.out.println("Ya existe un empleado con esa identificacion.");
            return;
        }

        String correo = Texto.leer("Correo: ");
        String telefono = Texto.leer("Telefono: ");
        String cargo = Texto.leer("Cargo: ");
        double salario = Decimal.leer("Salario: ");

        try {
            Validador.validarNombre(nombre);
            Validador.validarIdentificacion(identificacion);
            Validador.validarCorreo(correo);
            Validador.validarTelefono(telefono);
            Validador.validarSalario(salario);
        } catch (DatoInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        Empleado emp = new Empleado();
        emp.setNombre(nombre);
        emp.setIdentificacion(identificacion);
        emp.setCorreo(correo);
        emp.setTelefono(telefono);
        emp.setCargo(cargo);
        emp.setFechaIngreso(new Date());
        emp.setSalario(salario);

        empleadoDao.insertar(emp);
    }

    public static void listar() {
        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados.");
        } else {
            System.out.println("\n--- EMPLEADOS ---");
            for (Empleado e : empleados) {
                System.out.println("ID: " + e.getId() + " - " + e.getNombre()
                        + " - " + e.getCargo()
                        + " - " + ArchivoUtils.formatearMoneda(e.getSalario()));
            }
        }
    }

    public static void buscar() {
        int id = Entero.leer("ID: ");
        Empleado e = empleadoDao.buscarPorId(id);
        if (e != null) {
            System.out.println(e.getNombre() + " - " + e.getCargo()
                    + " - " + ArchivoUtils.formatearMoneda(e.getSalario()));
        } else {
            System.out.println("No existe.");
        }
    }

    public static void actualizar() {
        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para actualizar.");
            return;
        }
        System.out.println("\n--- EMPLEADOS ---");
        for (Empleado e : empleados) {
            System.out.println("ID: " + e.getId() + " - " + e.getNombre()
                    + " - " + e.getCargo()
                    + " - " + ArchivoUtils.formatearMoneda(e.getSalario()));
        }
        int id = Entero.leer("ID a actualizar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Empleado e = empleadoDao.buscarPorId(id);
        if (e == null) { System.out.println("No existe."); return; }

        String c = Texto.leer("Nuevo cargo (Enter = mantener): ");
        if (!c.isEmpty()) e.setCargo(c);

        double s = Decimal.leer("Nuevo salario (0 = mantener): ");
        if (s > 0) {
            try {
                Validador.validarSalario(s);
                e.setSalario(s);
            } catch (DatoInvalido ex) {
                System.out.println("Error: " + ex.getMessage());
                return;
            }
        }

        empleadoDao.actualizar(e);
    }

    public static void eliminar() {
        List<Empleado> empleados = empleadoDao.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para eliminar.");
            return;
        }
        System.out.println("\n--- EMPLEADOS ---");
        for (Empleado e : empleados) {
            System.out.println("ID: " + e.getId() + " - " + e.getNombre()
                    + " - " + e.getCargo());
        }
        int id = Entero.leer("ID a eliminar (0 = cancelar): ");
        if (id == 0) { System.out.println("Cancelada"); return; }

        Empleado e = empleadoDao.buscarPorId(id);
        if (e == null) { System.out.println("No existe."); return; }

        int op = Entero.leer("Confirma eliminar a " + e.getNombre() + "? (1=Si, 2=No): ");
        if (op == 1) {
            try {
                empleadoDao.eliminar(id);
            } catch (Exception ex) {
                System.out.println("No se puede eliminar este empleado porque tiene ventas asociadas.");
            }
        } else {
            System.out.println("Cancelada");
        }
    }
}