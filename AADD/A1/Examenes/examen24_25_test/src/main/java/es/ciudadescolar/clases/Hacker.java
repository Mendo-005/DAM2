package es.ciudadescolar.clases;

public class Hacker {

    private String id;  
    private String nombre;
    private String apellido;
    private double nota;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    // Contructor vacío para Jackson
    public Hacker() {
    }
    
    public Hacker(String id, String nombre, String apellido, double nota) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nota = nota;
    }
    @Override
    public String toString() {
        return "Hacker [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", nota=" + nota + "]";
    }
    
}
