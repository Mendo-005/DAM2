package es.ciudadescolar.hospital;

public class Paciente 
{
    private String id;

    private String habitacion;

    private String seguro;

    private String nombre;

    private String apellido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
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
        return "Paciente [id=" + id + ", habitacion=" + habitacion + ", seguro=" + seguro + ", nombre=" + nombre
                + ", apellido=" + apellido + "]";
    }

    
    
	
}
