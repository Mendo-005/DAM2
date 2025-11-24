public class Empleado {

    private String nombre;
    private String apellidos;
    private String departamento;
    private boolean activo;
    private String fechaContratacion; // Extra por el punto 10 de la rúbrica

    public Empleado(String nombre, String apellidos, String departamento, boolean activo, String fechaContratacion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
        this.activo = activo;
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public String toString() {
        String estado = activo ? "Activo" : "Inactivo";
        return "Nombre: " + nombre + " " + apellidos + 
               " | Dept: " + departamento + 
               " | Estado: " + estado + 
               " | Fecha: " + fechaContratacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }
    
    // Getters si fueran necesarios para una tabla compleja
    
}
