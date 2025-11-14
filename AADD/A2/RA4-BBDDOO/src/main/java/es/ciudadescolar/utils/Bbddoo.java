package es.ciudadescolar.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import es.ciudadescolar.instituto.Alumno;

public class Bbddoo 
{
    private static final Logger LOG = LoggerFactory.getLogger(Bbddoo.class);
    private static ObjectContainer bd = null;
    private File ficheroBd = null;

    public Bbddoo(File fich, boolean sobrescribir)
    {
        this.ficheroBd=fich;

        if (sobrescribir)
        {
            if(ficheroBd.exists())
            {
                LOG.warn("Se procede a borrar la BD");
                ficheroBd.delete();
            }
        }

        bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), ficheroBd.getName());
        LOG.debug("Abierta base de datos: "+ficheroBd.getAbsolutePath());

    }

    public boolean cerrar()
    {
        if (bd != null)
        {
            bd.close();
            LOG.debug("Cerrada base de datos");
            return true;
        }
        LOG.warn("No se puede cerrar base de datos no instanciada");
        return false;
    }

    public static boolean guardarAlumno(Alumno alumno) 
    {
        boolean status = false;

        if(bd != null)
        {
            bd.store(alumno);
            status = true;
            LOG.info("Alumno guardado: " + alumno);
        }
        return status;
    }

    public List<Alumno> recuperaTodosAlumnos()
    {
        List<Alumno> alumnos = null;
        // Preparamos una plantilla inclusivas (que todos los alumnos dela BD satisfagan)
        // String = null
        // Int = 0
        Alumno alumnoBuscado = new Alumno(null, null, 0);
        
        ObjectSet<Alumno> alumnosRecuperados = bd.queryByExample(alumnoBuscado);

        if (!alumnosRecuperados.isEmpty()) 
        {
            alumnos = new  ArrayList<>();
            for (Alumno alumno : alumnosRecuperados) 
            {
                LOG.info("Alumno recuprado: "+ alumno);
                alumnos.add(alumno);    
            }
        }
        return alumnos;
    }

    public Alumno recuperaAlumnoPorExp(String expediente)
    {
        // Preparamos una plantilla inclusivas (que todos los alumnos dela BD satisfagan)
        // String = null
        // Int = 0
        Alumno alumnoBuscado = new Alumno(null, expediente, 0);
        Alumno alumnoRecuprado = null;
        ObjectSet<Alumno> alumnosRecuperados = null;

        if (!alumnosRecuperados.isEmpty()) 
        {
            alumnosRecuperados = bd.queryByExample(alumnoBuscado);
            alumnos = new  ArrayList<>();
            for (Alumno alumno : alumnosRecuperados) 
            {
                LOG.info("Alumno recuprado: "+ alumno);
                alumnos.add(alumno);    
            }
        }
        return alumnos;
    }
}
