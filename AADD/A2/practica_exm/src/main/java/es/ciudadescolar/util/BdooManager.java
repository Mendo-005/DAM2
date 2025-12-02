package es.ciudadescolar.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Query;

import es.ciudadescolar.clases.Empleado;
import es.ciudadescolar.clases.Equipo;

public class BdooManager
{
    private static final Logger LOG = LoggerFactory.getLogger(BdooManager.class);
    private static ObjectContainer bd = null;
    private File ficheroBd = null;
    
    // Constructor de la clase
    public BdooManager(File fichero, boolean status)
    {
        this.ficheroBd  = fichero;
        
        if (status && ficheroBd.exists()) 
        {
            LOG.warn("Se procede a borrar la BD anterior");
            ficheroBd.delete();
        }

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Equipo.class).cascadeOnDelete(true);
        config.common().objectClass(Equipo.class).cascadeOnUpdate(true);

        bd = Db4oEmbedded.openFile(config, ficheroBd.getName());
        LOG.debug("Abierta base de datos: " + ficheroBd.getAbsolutePath());
    }

    // Crear un elemento padre (puede tener elementos dentro)
    public boolean generarEquipo(Equipo equipo)
    {
        boolean status = false;

        if (bd != null) 
        {
            try 
            {
                bd.store(equipo);
                status = true;
                LOG.debug("Equipo guardado: " + equipo);
            } 
            catch (DatabaseClosedException e) 
            {
                LOG.error("Error al guardar equipo" + equipo.getNombre(), e);
            } 
            catch (DatabaseReadOnlyException e) 
            {
                LOG.error("Error al guardar equipo" + equipo.getNombre(), e);
            }
        }

        return status; 
    }

    // Añadir un elemento a una lista 
    public boolean añadirEmpleado(Equipo equipo, Empleado emp)
    {
        boolean status = false;

        if (bd != null)
        {
            try 
            {
                equipo.getListaEmpleados().add(emp);
                bd.store(equipo);
                LOG.debug("Empleado " + emp + " guardado en el equipo " + equipo);
            } 
            catch (DatabaseClosedException e) 
            {
                LOG.error("Error al guardar empleado" + emp.getNombre(), e);
            } 
            catch (DatabaseReadOnlyException e) 
            {
                LOG.error("Error al guardar empleado" + emp.getNombre(), e);
            }    
        }
        
        return status;
    }

    // Consulta de todos los elementos en una lista 
    public List<Empleado> getTodosEmpleados(String nombreEquipo)
    {
        List<Empleado> listaEmpleados = new ArrayList<>();

        if (bd != null) 
        {
            Equipo equipo = new Equipo(nombreEquipo, null, null, null);
            ObjectSet<Equipo> result = bd.queryByExample(equipo);
            
            if (result.hasNext())
            {
                Equipo equ = result.next();
                bd.ext().activate(equ);
                if (equ.getListaEmpleados() != null) 
                {
                    listaEmpleados.addAll(equ.getListaEmpleados());    
                }    
            }
        }

        return listaEmpleados;
    }

    public Empleado getEmpleadoPorExp(Integer exp)
    {

        if (bd != null) 
        {
            Query query = bd.query();
            query.constrain(Empleado.class);
            query.descend("expediente").constrain(exp);
            
            ObjectSet<Empleado> result = query.execute();

            if (result.hasNext()) 
            {
                Empleado empleado = result.next();
                LOG.debug("Empleado con expediente (" + exp + "): " + empleado );  
                return empleado; 
            }
        }
        return null;
    }

    public boolean cerrarBd()
    {   
        boolean status = false;

        if (bd != null) 
        {
            bd.close();
            status = true;
            LOG.debug("Cerrando la base de datos: " + ficheroBd.getName());
        }

        return status;
    }
}
