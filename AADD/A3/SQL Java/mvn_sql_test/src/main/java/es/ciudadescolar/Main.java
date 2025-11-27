package es.ciudadescolar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Alumno;
import es.ciudadescolar.util.DbManager;

public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
    
            DbManager manager = new DbManager();
            List<Alumno> listaAlumnos = manager.mostrarAlumnos();

            for (Alumno al : listaAlumnos) 
            {
                LOG.info("Alumno recuerado: " + al);    
            }
        
    }
    
}
