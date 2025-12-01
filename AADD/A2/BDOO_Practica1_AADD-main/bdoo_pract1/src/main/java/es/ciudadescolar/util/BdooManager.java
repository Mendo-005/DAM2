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
import com.db4o.query.Query;

import es.ciudadescolar.clases.Alumno;
import es.ciudadescolar.clases.Instituto;

public class BdooManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(BdooManager.class);
    private static ObjectContainer bd = null;
    private File ficheroBd = null;
    
    public BdooManager(File fich, boolean sobrescribir)
    {
        this.ficheroBd = fich;

        if (sobrescribir && ficheroBd.exists())
        {
            LOG.warn("Se procede a borrar la BD anterior");
            ficheroBd.delete();
        }

        // Configuración requerida: Cascade On Delete 
        // Añadimos también Cascade On Update para facilitar la persistencia de las listas
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Instituto.class).cascadeOnDelete(true);
        config.common().objectClass(Instituto.class).cascadeOnUpdate(true);

        bd = Db4oEmbedded.openFile(config, ficheroBd.getName());
        LOG.debug("Abierta base de datos: " + ficheroBd.getAbsolutePath());
    }

    // --- MÉTODOS DE LÓGICA DE INSTITUTO (Movidos aquí) ---

    // Equivalente a matricularAlumno (PDF pág 4) [cite: 40]
    public boolean matricularAlumno(Instituto insti, Alumno al)
    {
        if (bd != null) 
        {
            try 
            {
                // Agregamos a la lista en memoria
                insti.getListaAlumnos().add(al);
                // Guardamos el instituto (por cascadeOnUpdate se guarda el alumno)
                bd.store(insti);
                LOG.info("Alumno matriculado en " + insti.getNombre() + ": " + al.getNombre());
                return true;
            } 
            catch (Exception e) 
            {
                LOG.error("Error al matricular alumno", e);
                return false;
            }
        }
        return false;
    }

    // Equivalente a expulsarAlumno (PDF pág 4) [cite: 40]
    public boolean expulsarAlumno(Instituto insti, Alumno al)
    {
        if (bd != null) 
        {
            try 
            {
                if (insti.getListaAlumnos().remove(al)) 
                {
                    bd.store(insti); // Actualizamos la lista del instituto
                    bd.delete(al);   // Borramos fisicamente al alumno de la BD
                    LOG.info("Alumno expulsado: " + al.getNombre());
                    return true;
                }
            } 
            catch (Exception e) 
            {
                LOG.error("Error al expulsar alumno", e);
                return false;
            }
        }
        return false;
    }

    // Equivalente a cambiarIdInstituto (PDF pág 4) [cite: 41]
    public boolean cambiarIdInstituto(Instituto insti, int nuevoId)
    {
        if (bd != null)
        {
            insti.setIdentificador(nuevoId);
            bd.store(insti);
            LOG.info("ID del instituto actualizado a: " + nuevoId);
            return true;
        }
        return false;
    }

    // --- MÉTODOS DE GESTIÓN BD (PDF pág 4) ---

    // [cite: 45]
    public boolean guardarInstituto(Instituto insti)
    {
        boolean status = false;
        if (bd != null) 
        {
            try 
            {
                bd.store(insti);
                status = true;
                LOG.info("Instituto guardado: " + insti);
            }
            catch (Exception e)
            {
                LOG.error("Error guardando instituto", e);
            }
        }
        return status;
    }

    // Metodo mantenido por compatibilidad con tu Main original
    public boolean generarInstituto(Instituto insti)
    {
        return guardarInstituto(insti);
    }

    // [cite: 43]
    public void guardarAlumno(Alumno al)
    {
        if (bd != null)
        {
            bd.store(al);
            LOG.info("Alumno guardado: " + al);
        }
    }

    // [cite: 44]
    public List<Alumno> getTodosAlumnos(String nombreInstituto)
    {
        List<Alumno> listaResultado = new ArrayList<>();
        if (bd != null)
        {
            Instituto proto = new Instituto(nombreInstituto, null, null);
            ObjectSet<Instituto> result = bd.queryByExample(proto);
            
            if (result.hasNext()) 
            {
                Instituto inst = result.next();
                // Activamos el objeto para asegurar que la lista viene cargada
                bd.ext().activate(inst, 5); 
                if (inst.getListaAlumnos() != null) 
                {
                    listaResultado.addAll(inst.getListaAlumnos());
                }
            }
        }
        return listaResultado;
    }

    // [cite: 46]
    public Instituto getInstituto(Instituto institutoBuscado)
    {
        if (bd != null)
        {
            ObjectSet<Instituto> result = bd.queryByExample(institutoBuscado);
            if (result.hasNext()) 
            {
                return result.next();
            }
        }
        return null;
    }

    // [cite: 47] - Uso de borrado en cascada
    public boolean borrarInstituto(Instituto institutoABorrar)
    {
        if (bd != null)
        {
            ObjectSet<Instituto> result = bd.queryByExample(institutoABorrar);
            if (result.hasNext()) 
            {
                // Al tener cascadeOnDelete(true) configurado, borrará los alumnos asociados
                bd.delete(result.next());
                LOG.info("Instituto borrado correctamente.");
                return true;
            }
        }
        return false;
    }

    // [cite: 49] - Borrado transaccional
    public boolean expulsarATodosAlumnos(String nombreInstituto)
    {
        if (bd != null)
        {
            try 
            {
                Instituto proto = new Instituto(nombreInstituto, null, null);
                ObjectSet<Instituto> result = bd.queryByExample(proto);
                
                if (result.hasNext()) 
                {
                    Instituto inst = result.next();
                    bd.ext().activate(inst, 5);
                    
                    List<Alumno> alumnos = inst.getListaAlumnos();
                    // Creamos copia para evitar ConcurrentModificationException
                    List<Alumno> copiaAlumnos = new ArrayList<>(alumnos);
                    
                    for (Alumno al : copiaAlumnos) 
                    {
                        bd.delete(al); // Borrado físico
                    }
                    
                    alumnos.clear(); // Limpiado lógico de la lista
                    bd.store(inst);  // Guardamos cambios en instituto
                    
                    LOG.info("Se han expulsado todos los alumnos de " + nombreInstituto);
                    return true;
                }
            } 
            catch (Exception e) 
            {
                LOG.error("Error expulsando alumnos masivamente", e);
                rollbackTransaction(); 
                return false;
            }
        }
        return false;
    }

    // [cite: 50]
    public Instituto consultaMatriculasInstituto(String idInstituto)
    {
        if (bd != null)
        {
            try 
            {
                Integer id = Integer.parseInt(idInstituto);
                Instituto proto = new Instituto(null, id, null);
                return getInstituto(proto);
            } 
            catch (NumberFormatException e) 
            {
                LOG.warn("ID proporcionado no válido: " + idInstituto);
            }
        }
        return null;
    }

    // [cite: 51] - Consulta SODA
    public Instituto consultaInstiMatriculado(String nomAlumno)
    {
        if (bd != null)
        {
            Query query = bd.query();
            query.constrain(Instituto.class);
            // Navegamos: Instituto -> listaAlumnos -> nombre == nomAlumno
            query.descend("listaAlumnos").descend("nombre").constrain(nomAlumno);
            
            ObjectSet<Instituto> result = query.execute();
            if (result.hasNext()) 
            {
                return result.next();
            }
        }
        return null;
    }
    
    // Método original para actualizar ID (ahora redundante con cambiarIdInstituto pero mantenido)
    public boolean modificarInstituto(Instituto institutoAModificar, Integer nuevoId)
    {
        return cambiarIdInstituto(institutoAModificar, nuevoId);
    }

    // --- TRANSACCIONES Y CIERRE ---

    public boolean cerrar()
    {
        if (bd != null)
        {
            bd.close();
            LOG.debug("Cerrada base de datos");
            return true;
        }
        return false;
    }

    public void rollbackTransaction() 
    {
        if (bd != null) 
        {
            bd.rollback();
            LOG.info("Rollback ejecutado");
        }
    }

    public void commitTransaction() 
    {
        if (bd != null) 
        {
            bd.commit();
            LOG.info("Commit ejecutado");
        }
    }
}