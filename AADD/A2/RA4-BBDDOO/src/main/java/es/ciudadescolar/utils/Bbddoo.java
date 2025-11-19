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

        public List<Alumno> recuperaTodosAlumnos() {
            List<Alumno> alumnos = new ArrayList<>();

            if (bd == null) {
                LOG.warn("BD no inicializada");
                return alumnos;
            }
        
            ObjectSet<Alumno> resultados = bd.queryByExample(new Alumno());
        
            for (Alumno a : resultados) {
                LOG.info("Alumno recuperado: " + a);
                alumnos.add(a);
            }
        
            return alumnos;
        }

        public List<Alumno> recuperaAlumnoPorExp(String expediente) {
        
            List<Alumno> alumnos = new ArrayList<>();
        
            if (bd == null) {
                LOG.warn("BD no inicializada");
                return alumnos;
            }
        
            // Plantilla solo con el expediente
            Alumno alumno = new Alumno();
            alumno.setExpediente(expediente);
        
            ObjectSet<Alumno> resultados = bd.queryByExample(alumno);
        
            for (Alumno a : resultados) {
                LOG.info("Alumno recuperado: " + a);
                alumnos.add(a);
            }
        
            return alumnos;
        }



    public boolean borrarAlumno(Alumno alumno)
    {
        boolean status = false;
        ObjectSet<Alumno> alumnosRecuperados = null;
        if (bd != null) {
            
            alumnosRecuperados = bd.queryByExample(alumno);
            
            switch (alumnosRecuperados.size()) {
                case 1 -> {
                    bd.delete(alumno);
                    status = true;
                    LOG.info("Borrado alumno: " + alumno);
                }
                case 0 -> LOG.warn("No se ha localizado objetos a borrar: " + alumno);
                default -> LOG.warn("Se  han localizado varios objetos a borrar: " + alumnosRecuperados.size());
            }
            
        }
        return status;
    }

    public boolean  borrarTodosAlumnos()
    {
        boolean status = false;
        List<Alumno> alumnosABorrar = recuperaTodosAlumnos();

        if (alumnosABorrar.size() == 0) 
        {
            LOG.warn("No hay alumnos a borrar");    
        }

        for (Alumno al : alumnosABorrar) 
        {
            bd.delete(al);
            LOG.info("Borrado alumno:" + al);    
        }
        return status;

    }

    public boolean  modificarAlumno(Alumno alumnoAModificar, Integer edadNueva)
    {
        boolean status = false;

        ObjectSet<Alumno> alumnosAModificar = bd.queryByExample(alumnoAModificar);
        int totalAlumnosAModificar = alumnosAModificar.size();

        Alumno alumno = null;

        switch (totalAlumnosAModificar) {
            case 0 -> LOG.warn("No hay alumnos en la BD");
            case 1 -> {
                alumno = alumnosAModificar.next();
                alumno.setEdad(edadNueva);
                bd.store(alumno);
                LOG.info("Se ha actualizado la edad del alumno: " + alumno);
                status = true;
            }
            default -> LOG.warn("No se pueden actualizar mas de un objeto a la vez");
        }
        return status;
    }
}
