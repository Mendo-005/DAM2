package es.ciudadescolar.clases;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String nombre;
    private String descripcion;
    private Integer ingresos;
    private List<Empleado> listaEmpleados;
    
    public Equipo(String nombre, String descripcion, Integer ingresos, List<Empleado> listaEmpleados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingresos = ingresos;
        // inicializamos con array si no existe
        if (listaEmpleados == null) 
        {
            this.listaEmpleados = new ArrayList<>();
        }
        else
        {
            this.listaEmpleados = listaEmpleados;
        }
        
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getIngresos() {
        return ingresos;
    }
    public void setIngresos(Integer ingresos) {
        this.ingresos = ingresos;
    }
    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }
    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    @Override
    public String toString() {
        return "Equipo: " + nombre + " | " + descripcion + " | " + ingresos
                + " | " + listaEmpleados;
    }

    
    
}
