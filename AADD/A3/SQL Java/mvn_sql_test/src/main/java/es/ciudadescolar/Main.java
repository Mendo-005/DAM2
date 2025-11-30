package es.ciudadescolar;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import es.ciudadescolar.util.DbManager;
import es.ciudadescolar.clases.Alumno;
public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        DbManager manager = new DbManager();

        List<Alumno> alumnos = manager.getAllAlumnos();

        if (alumnos != null)
        {
            for (Alumno al: alumnos )
            {
                LOG.info(al.toString());
            }
        }

        Alumno albuscado = manager.getAlumnoPorExpYNom(1009,"paco");
        if (albuscado != null)
        {
            LOG.info("Alumno localizado: "+ albuscado);
        }
        else
        {
            LOG.warn("Alumno buscado no encontrado");
        }

        Alumno alumnoNuevo = new Alumno(3004,"David", Date.valueOf(LocalDate.of(2001,10,23)));
        if (!manager.altaDeAlumno(alumnoNuevo))
        {
            LOG.warn("No se ha podido dar de alta el alumno: "+alumnoNuevo);
        }

        Alumno alumnoX = new Alumno(3,"Francisco",Date.valueOf(LocalDate.of(2001,10,23)));

        alumnoX.setNombre("Manuel");

        manager.modificarNombreDeAlumno(alumnoX);

        manager.borrarAlumno(3004);


        manager.cerrarBd();
    }
}
=======
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
>>>>>>> 8fc9065ae11502455ba61e8d4458e2ce4fb02e26
