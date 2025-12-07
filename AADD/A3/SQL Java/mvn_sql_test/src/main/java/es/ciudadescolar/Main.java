package es.ciudadescolar;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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


        // SP con parametro entrada
        int expMostrar = 9;
        if (!manager.muestraAlumno(expMostrar)) 
        {
            LOG.warn("No se pudo invocar el SP con expediente: " + expMostrar);    
        }
        else
        {
            LOG.info("Invocado el SP correctamente con expediente: " +expMostrar);
        }
        

        // SP con parametro salida
        int numAlumnos = manager.recuperaAlumno();
        if (numAlumnos < 0) 
        {
            LOG.warn("No se pudo invocar el SP ");    
        }
        else
        {
            LOG.info("Numero de alumnos en la base de datos: " + numAlumnos);
        }

        // Funcion con parametro entrada y salida
        int exNota = 2;
        int nota = manager.getNotaAlumno(exNota);
        if (nota < 0) 
        {
            LOG.warn("No se pudo invocar la funcion con expediente: " + exNota);    
        }
        else
        {
            LOG.info("Invocado la funcion correctamente con expediente: " +exNota + " y nota: " + nota);
        }


        // Transacciones
        List<Alumno> nuevosAlumnos = new ArrayList<>();
        nuevosAlumnos.add(new Alumno(1242, "Joselu", Date.valueOf(LocalDate.of(2000, 1, 24))));
        nuevosAlumnos.add(new Alumno(8665, "Carlos", Date.valueOf(LocalDate.of(2000, 2, 24))));
        nuevosAlumnos.add(new Alumno(3242, "Nacho", Date.valueOf(LocalDate.of(2000, 3, 24))));

        if (!manager.altaAlumnoTransac(nuevosAlumnos)) 
        {
            LOG.error("No se han dado de alta los alumnos solicitados");    
        }

        manager.cerrarBd();
    }
}
