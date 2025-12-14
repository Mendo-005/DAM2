package mendo.dev.clases;

public class IngredienteReceta {
    
    // NOTA: eliminamos los campos id_ingrediente y cod_ingred para que la BD los gestione.
    private String nombre_ingrediente;
    private Double cantidad; 
    
    // Constructor de ejemplo usado en el Main
    public IngredienteReceta(String nombre, double cantidad) {
        this.nombre_ingrediente = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public Double getCantidad() {
        return cantidad;
    }
}