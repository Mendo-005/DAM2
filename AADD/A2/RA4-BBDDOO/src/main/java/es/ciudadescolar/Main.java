package es.ciudadescolar;

import java.io.File;
import java.util.List;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.utils.Bbddoo;

public class Main {
    public static void main(String[] args) 
    {
        Bbddoo bd = new Bbddoo(new File("alumnos.db4o"), true);
        

        Alumno alumno = new Alumno("Paco","999",Integer.valueOf(23));
        Alumno alumno2 = new Alumno("Fermin","888",Integer.valueOf(20));
        Alumno alumno3 = new Alumno("David","777",Integer.valueOf(19));

        // Guardamos los alumnos
        bd.guardarAlumno(alumno);
        bd.guardarAlumno(alumno2);
        bd.guardarAlumno(alumno3);

        List<Alumno> alumnoEnBD = bd.recuperaTodosAlumnos();
        // Cerramos base de datos
        bd.cerrar();
    }
}