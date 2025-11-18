package es.ciudadescolar.clases;

public class Medico {
    private String id;
    private String turno;
    private String especialidad;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    // Constructor vacío
    public Medico() {
    }

    // Constructor con parámetros
    public Medico(String id, String turno, String especialidad, String nombre, 
                  String apellido, String telefono, String email) {
        this.id = id;
        this.turno = turno;
        this.especialidad = especialidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTurno() {
        return turno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id='" + id + '\'' +
                ", turno='" + turno + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
