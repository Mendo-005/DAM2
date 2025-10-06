package es.ciudadescolar.clases;

import java.io.Serializable;

public class Modulos implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private String moduloProfesional;
    private int horas;
    private String profesor;
    private int curso;
    private transient String password;
    public Modulos(String mod, int h, String prof, int cur, String pass)
    {
        this.moduloProfesional = mod;
        this.horas = h;
        this.profesor = prof;
        this.curso = cur;
        this.password = pass;
    }

    public String getModulo(){
        return moduloProfesional;
    }

    public void setModulo(String moduloProfesional){
        this.moduloProfesional = moduloProfesional;
    }

    public int getHoras(){
        return horas;
    }

    public void setHoras(int horas){
        this.horas = horas;
    }

    public String getProfesor(){
        return profesor;
    }

    public void setProfesor(String profesor){
        this.profesor = profesor;
    }

    public int getCurso(){
        return curso;
    }

    public void setCurso(int curso){
        this.curso = curso;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Módulo profesional: " + moduloProfesional + "|Horas: " + horas + "|Profesor: " + profesor + "|Curso: " + curso + "|Contraseña: " + password;
    }

    
}
