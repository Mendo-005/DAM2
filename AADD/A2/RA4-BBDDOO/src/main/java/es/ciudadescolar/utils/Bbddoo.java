package es.ciudadescolar.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Bbddoo 
{
    private static final Logger LOG = LoggerFactory.getLogger(Bbddoo.class);
    private ObjectContainer bd = null;
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

}
