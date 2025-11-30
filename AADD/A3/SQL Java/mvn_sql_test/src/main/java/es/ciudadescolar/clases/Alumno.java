package es.ciudadescolar.clases;

import java.sql.Date;
import java.time.LocalDate;

<<<<<<< HEAD
public class Alumno 
{
=======
public class Alumno {

>>>>>>> 8fc9065ae11502455ba61e8d4458e2ce4fb02e26
    private Integer expediente;
    private String nombre;
    private LocalDate fecha_nac;
    
<<<<<<< HEAD
    public Alumno()
    {

    }

    public Alumno(int exp, String nom, Date fechaNac)
    {
        this.setExpediente(Integer.valueOf(exp));
        this.setNombre(nom);
        this.setFecha_nac(fechaNac.toLocalDate());
    }

=======
>>>>>>> 8fc9065ae11502455ba61e8d4458e2ce4fb02e26
    public Integer getExpediente() {
        return expediente;
    }
    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LocalDate getFecha_nac() {
        return fecha_nac;
    }
    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }
    @Override
    public String toString() {
        return "Alumno [expediente=" + expediente + ", nombre=" + nombre + ", fecha_nac=" + fecha_nac + "]";
    }
<<<<<<< HEAD
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expediente == null) ? 0 : expediente.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((fecha_nac == null) ? 0 : fecha_nac.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alumno other = (Alumno) obj;
        if (expediente == null) {
            if (other.expediente != null)
                return false;
        } else if (!expediente.equals(other.expediente))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (fecha_nac == null) {
            if (other.fecha_nac != null)
                return false;
        } else if (!fecha_nac.equals(other.fecha_nac))
            return false;
        return true;
    }
    

=======
    
    // Constructores 
    public Alumno() 
    {
    
    }
    public Alumno(int expediente, String nombre, Date fecha_nac) {
        this.setExpediente(Integer.valueOf(expediente));
        this.setNombre(nombre);
        this.setFecha_nac(fecha_nac.toLocalDate());
    }
>>>>>>> 8fc9065ae11502455ba61e8d4458e2ce4fb02e26

}