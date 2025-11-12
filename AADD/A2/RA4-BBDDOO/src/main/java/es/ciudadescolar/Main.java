package es.ciudadescolar;

import java.io.File;

import es.ciudadescolar.utils.Bbddoo;

public class Main {
    public static void main(String[] args) 
    {
        Bbddoo bd = new Bbddoo(new File("alumnos.db4o"), true);
        bd.cerrar();
    }
}