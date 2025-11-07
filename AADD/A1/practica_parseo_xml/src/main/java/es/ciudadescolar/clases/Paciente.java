package es.ciudadescolar.clases;

public class Paciente {
    private String id;
    private int edad;
    private String nombre;
    private String apellido;
    private String telefono;
    private String diagnostico;

    // Constructor vacío
    public Paciente() {
    }

    // Constructor con parámetros
    public Paciente(String id, int edad, String nombre, String apellido, 
                    String telefono, String diagnostico) {
        this.id = id;
        this.edad = edad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.diagnostico = diagnostico;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getEdad() {
        return edad;
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

    public String getDiagnostico() {
        return diagnostico;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", edad=" + edad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
