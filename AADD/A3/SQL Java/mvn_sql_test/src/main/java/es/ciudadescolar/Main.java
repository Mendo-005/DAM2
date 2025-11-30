package es.ciudadescolar;

import java.sql.Date;
import java.time.LocalDate;
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

            Alumno alumnoBuscado = manager.getAlumnoPorExp(1, "Francisco");
            if (alumnoBuscado != null) 
            {
                LOG.info("Alumno encontrado por expediente: " + alumnoBuscado);    
            }
            else
            {
                LOG.warn("No se ha encontrado a ningun alumno con ese expediente");
            }
            
            Alumno alumnoNuevo = new Alumno(8,"Carlos", Date.valueOf(LocalDate.of(2001,10,23)));
            if (!manager.altaDeAlumno(alumnoNuevo)) 
            {
                LOG.warn("No se ha podido dar de alta al alumno: " + alumnoNuevo);    
            }
            

    }
    
}
