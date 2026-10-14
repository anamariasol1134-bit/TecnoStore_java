
    package modelo;

    public class SistemaOperativo {
        private int id;
        private String nombre;
        private String version;
        
    public SistemaOperativo(){
    }

    public SistemaOperativo(int id, String nombre, String version) {
        this.id = id;
        this.nombre = nombre;
        this.version = version;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getVersion() {
        return version;
    }
    @Override
    public String toString() {
        return "SistemaOperativo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
    }
