package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.utils.Bbddoo;

public class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("static-access")
    public static void main(String[] args) 
    {
        Bbddoo bd = new Bbddoo(new File("alumnos.db4o"), true);
        

        Alumno alumno = new Alumno("Paco","999", 23);
        Alumno alumno2 = new Alumno("Fermin","888", 20);
        Alumno alumno3 = new Alumno("David","777", 19);

        // Guardamos los alumnos
        bd.guardarAlumno(alumno);
        bd.guardarAlumno(alumno2);
        bd.guardarAlumno(alumno3);

        List<Alumno> alumnoEnBD = bd.recuperaTodosAlumnos();
        LOG.info("Alumnos: " + alumnoEnBD);
        bd.borrarAlumno(alumno);
        bd.recuperaTodosAlumnos();
        LOG.info("Recuperar alumno 888: " + bd.recuperaAlumnoPorExp("888"));
        // Borrar todos los alumos
        bd.borrarTodosAlumnos();
        LOG.info("Borrados todos los alumnos");

        // Modificar alumnosa
        Alumno alumno4 = new Alumno("Dario","3435", 23);
        Alumno alumno5 = new Alumno("Maria","2212", 23);
        bd.guardarAlumno(alumno4);
        bd.guardarAlumno(alumno5);

        bd.modificarAlumno(alumno5, 20);
        // Cerramos base de datos
        bd.cerrar();
    }
}