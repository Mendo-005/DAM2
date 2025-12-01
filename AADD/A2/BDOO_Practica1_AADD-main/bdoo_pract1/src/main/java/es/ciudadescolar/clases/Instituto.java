package es.ciudadescolar.clases;

import java.util.ArrayList;
import java.util.List;

public class Instituto 
{
    private String nombre;
    private Integer identificador;
    private List<Alumno> listaAlumnos;
    
    public Instituto(String nombre, Integer identificador, List<Alumno> listaAlumnos) 
    {
        this.nombre = nombre;
        this.identificador = identificador;
        // Inicializamos la lista si es nula para evitar NullPointerException
        if (listaAlumnos == null) 
        {
            this.listaAlumnos = new ArrayList<>();
        } 
        else 
        {
            this.listaAlumnos = listaAlumnos;
        }
    }
    
    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public Integer getIdentificador() 
    {
        return identificador;
    }

    public void setIdentificador(Integer identificador) 
    {
        this.identificador = identificador;
    }

    public List<Alumno> getListaAlumnos() 
    {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) 
    {
        this.listaAlumnos = listaAlumnos;
    }

    @Override
    public String toString() 
    {
        return nombre + "|" + identificador + "| Alumnos: " + (listaAlumnos != null ? listaAlumnos.size() : 0);
    }
}