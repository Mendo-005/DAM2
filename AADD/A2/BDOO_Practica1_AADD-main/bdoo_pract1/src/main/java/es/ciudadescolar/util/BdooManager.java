package es.ciudadescolar.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import es.ciudadescolar.clases.Alumno;
import es.ciudadescolar.clases.Instituto;

public class BdooManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(BdooManager.class);
    private static ObjectContainer bd = null;
    private File ficheroBd = null;
    
    public BdooManager(File fich, boolean sobrescribir)
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
    // Generar Instituto
    public boolean generarInstituto(Instituto instituto)
    {
        boolean status = false;

        if (bd != null) 
        {
            bd.store(instituto);
            status = true;
            LOG.info("Instituto: " + instituto);
        }

        return status;
    }

    // Matricular Alumno
    public boolean matricularAlumno(Alumno al)
    {
        boolean status = false;

        if (bd != null) 
        {
            bd.store(al);
            status = true;
            LOG.info("Alumno matriculado: " + al);
        }

        return status;
    }

    // Expulsar Alumno
    public boolean expulsarAlumno(Alumno al)
    {
        boolean status = false;
        ObjectSet<Alumno> alumnosRecuperados = null;
        if (bd != null) 
        {
            alumnosRecuperados = bd.queryByExample(al);
            switch (alumnosRecuperados.size()) {
                case 1 -> {
                    bd.delete(al);
                    status = true;
                    LOG.info("Expulsado alumno: " + al.getNombre());
                }
                case 0 -> LOG.warn("No se ha localizado objetos a borrar: " + al);
                default -> LOG.warn("Se  han localizado varios objetos a borrar: " + alumnosRecuperados.size());
            }
        }
        return status;
    }

    // Modificar Instituto
    public boolean  modificarInstituto(Instituto institutoAModificar, Integer nuevoId)
    {
        boolean status = false;

        ObjectSet<Instituto> institutosAModificar = bd.queryByExample(institutoAModificar);
        int totalInstitutosAModificar = institutosAModificar.size();

        Instituto instituto = null;

        switch (totalInstitutosAModificar) {
            case 0 -> LOG.warn("No hay alumnos en la BD");
            case 1 -> {
                instituto = institutosAModificar.next();
                instituto.setIdentificador(nuevoId);
                bd.store(instituto);
                LOG.info("Se ha actualizado el id del Instituto: " + instituto);
                status = true;
            }
            default -> LOG.warn("No se pueden actualizar mas de un objeto a la vez");
        }
        return status;
    }

    // Cerrar Base de Datos
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

    public void rollbackTransaction() 
    {
        bd.rollback();
        LOG.info("Rollback ejecutado");
    }

    public void commitTransaction() 
    {
        bd.commit();
        LOG.info("Commit ejecutado");
    }
}
