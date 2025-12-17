package es.ciudadescolar.model;


// en el paquete model meto las entidades de negocio, sin lógica de persistencia

public class Actor {

    private Integer idActor;
    private String nombre;
    private String apellido;
    public Integer getIdActor() {
        return idActor;
    }
    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
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
    @Override
    public String toString() {
        return "Actor [idActor=" + idActor + ", nombre=" + nombre + ", apellido=" + apellido + "]";
    }
    
}
