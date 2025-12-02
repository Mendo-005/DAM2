package es.ciudadescolar.clases;

public class Empleado
{
    private String nombre;
    private String apellido;
    private Integer expediente;

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
    public Integer getExpediente() {
        return expediente;
    }
    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }
    

    // Constructores
    public Empleado() {
    }
    public Empleado(String nombre, String apellido, Integer expediente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.expediente = expediente;
    }

    @Override
    public String toString() {
        return nombre + " | " + apellido + " | " + expediente
                + " | ";
    }
}
