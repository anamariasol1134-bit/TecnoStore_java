package util;

public class Validador {

    public static void validarPrecio(double precio) throws DatoInvalido {
        if (precio < 50000) {
            throw new DatoInvalido("El precio debe ser mayor o igual a $50.000");
        }
        if (precio > 20000000) {
            throw new DatoInvalido("El precio no puede ser mayor a $20.000.000");
        }
    }

    public static void validarStock(int stock) throws DatoInvalido {
        if (stock < 0) {
            throw new DatoInvalido("El stock no puede ser negativo");
        }
        if (stock > 10000) {
            throw new DatoInvalido("El stock no puede ser mayor a 10.000 unidades");
        }
    }

    public static void validarSalario(double salario) throws DatoInvalido {
        if (salario < 1000000) {
            throw new DatoInvalido("El salario debe ser mayor o igual a $1.000.000");
        }
        if (salario > 100000000) {
            throw new DatoInvalido("El salario no puede ser mayor a $100.000.000");
        }
    }

    public static void validarCorreo(String correo) throws DatoInvalido {
        if (correo == null || correo.isEmpty()) {
            throw new DatoInvalido("El correo no puede estar vacio");
        }
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!correo.matches(regex)) {
            throw new DatoInvalido("El correo no tiene formato valido");
        }
    }

    public static void validarTelefono(String telefono) throws DatoInvalido {
        if (telefono == null || telefono.isEmpty()) {
            throw new DatoInvalido("El telefono no puede estar vacio");
        }
        if (!telefono.matches("^[0-9]{7,15}$")) {
            throw new DatoInvalido("El telefono debe tener entre 7 y 15 digitos");
        }
    }

    public static void validarNombre(String nombre) throws DatoInvalido {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalido("El nombre no puede estar vacio");
        }
    }

    public static void validarIdentificacion(String identificacion) throws DatoInvalido {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new DatoInvalido("La identificacion no puede estar vacia");
        }
    }
}