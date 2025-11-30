package es.ciudadescolar.clases;

import java.sql.Date;
import java.time.LocalDate;

public class Alumno {

    private Integer expediente;
    private String nombre;
    private LocalDate fecha_nac;
    
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
    
    // Constructores 
    public Alumno() 
    {
    
    }
    public Alumno(int expediente, String nombre, Date fecha_nac) {
        this.setExpediente(Integer.valueOf(expediente));
        this.setNombre(nombre);
        this.setFecha_nac(fecha_nac.toLocalDate());
    }

}