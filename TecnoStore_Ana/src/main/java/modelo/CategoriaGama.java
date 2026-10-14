/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
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
