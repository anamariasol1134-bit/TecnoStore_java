
package modelo;

public enum CategoriaGama {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");
    
    private final String descripcion;
    CategoriaGama(String descripcion){
        this.descripcion=descripcion;   
    }
    public String getDescripcion(){
        return descripcion;
    }
    @Override 
    public String toString(){
        return descripcion;
    }
    public static CategoriaGama desdeTexto(String texto) {
    for (CategoriaGama g : values()) {
        if (g.getDescripcion().equalsIgnoreCase(texto)) {
            return g;
        }
    }
    return null;
}
}
