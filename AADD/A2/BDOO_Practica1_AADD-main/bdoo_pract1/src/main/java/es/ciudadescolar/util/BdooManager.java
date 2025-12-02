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
    
    /**
     * Constructor. Configura y abre la conexión con la base de datos db4o.
     * Establece la configuración de borrado y actualización en cascada.
     */
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

    /**
     * Modifica el instituto añadiendo un alumno a su lista y guarda el cambio.
     * @return true si se matriculó y guardó correctamente, false si hubo error.
     */
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
                LOG.debug("Alumno matriculado en " + insti.getNombre() + ": " + al.getNombre());
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

    /**
     * Modifica la lista del instituto (quita al alumno) y borra físicamente al alumno de la BD.
     * @return true si el alumno se eliminó de la lista y de la BD, false si falló.
     */
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
                    LOG.debug("Alumno expulsado: " + al.getNombre());
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

    /**
     * Modifica el identificador de un instituto y actualiza el objeto en la BD.
     * @return true si la actualización fue correcta.
     */
    public boolean cambiarIdInstituto(Instituto insti, int nuevoId)
    {
        if (bd != null)
        {
            insti.setIdentificador(nuevoId);
            bd.store(insti);
            LOG.debug("ID del instituto actualizado a: " + nuevoId);
            return true;
        }
        return false;
    }

    // --- MÉTODOS DE GESTIÓN BD (PDF pág 4) ---

    /**
     * Guarda (persiste) un nuevo objeto Instituto en la base de datos.
     * @return true si se guardó correctamente, false si hubo excepción.
     */
    public boolean guardarInstituto(Instituto insti)
    {
        boolean status = false;
        if (bd != null) 
        {
            try 
            {
                bd.store(insti);
                status = true;
                LOG.debug("Instituto guardado: " + insti);
            }
            catch (Exception e)
            {
                LOG.error("Error guardando instituto", e);
            }
        }
        return status;
    }

    /**
     * Wrapper de guardarInstituto.
     * @return true si se guardó correctamente.
     */
    public boolean generarInstituto(Instituto insti)
    {
        return guardarInstituto(insti);
    }

    /**
     * Guarda (persiste) un objeto Alumno individualmente.
     * No retorna nada (void).
     */
    public void guardarAlumno(Alumno al)
    {
        if (bd != null)
        {
            bd.store(al);
            LOG.debug("Alumno guardado: " + al);
        }
    }

    /**
     * Consulta un instituto por nombre y recupera su lista de alumnos.
     * @return Una lista de objetos Alumno (puede estar vacía), nunca null.
     */
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

    /**
     * Consulta la base de datos buscando un instituto específico (Query By Example).
     * @return El objeto Instituto encontrado o null si no existe.
     */
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

    /**
     * Borra un instituto de la base de datos (y sus alumnos por cascada).
     * @return true si encontró y borró el instituto, false en caso contrario.
     */
    public boolean borrarInstituto(Instituto institutoABorrar)
    {
        if (bd != null)
        {
            ObjectSet<Instituto> result = bd.queryByExample(institutoABorrar);
            if (result.hasNext()) 
            {
                // Al tener cascadeOnDelete(true) configurado, borrará los alumnos asociados
                bd.delete(result.next());
                LOG.debug("Instituto borrado correctamente.");
                return true;
            }
        }
        return false;
    }

    /**
     * Transacción compleja: Consulta el instituto, borra todos sus alumnos uno a uno y actualiza el instituto vacío.
     * @return true si todo el proceso fue exitoso, false si falló (hace rollback).
     */
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
                    
                    LOG.debug("Se han expulsado todos los alumnos de " + nombreInstituto);
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

    /**
     * Consulta un instituto buscando por su ID (parseado de String).
     * @return El objeto Instituto encontrado o null si no existe o el ID es inválido.
     */
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

    /**
     * Consulta SODA: Busca el instituto que contiene un alumno con el nombre indicado.
     * @return El objeto Instituto que contiene al alumno, o null si no se encuentra.
     */
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

    /**
     * Consulta nativa/SODA: Busca un Alumno por nombre y MODIFICA su expediente.
     * @return true si encontró al alumno y actualizó el expediente, false en caso contrario.
     */
    public boolean modificarAlumnoExp(String nomAlumno, String expNuevo) 
    {
        boolean status = false;
        if (bd != null) 
        {
            Query query = bd.query();
            // 1. Buscamos directamente ALUMNOS
            query.constrain(Alumno.class); 

            // 2. Filtramos por su nombre
            query.descend("nombre").constrain(nomAlumno); 

            ObjectSet<Alumno> result = query.execute();

            if (result.hasNext()) 
            {
                Alumno al = result.next(); // Ahora sí, result trae Alumnos
                al.setExpediente(expNuevo);

                bd.store(al); // Guardamos los cambios
                status = true;
                LOG.debug("Expediente actualizado para: " + nomAlumno);
            }
        }
        return status;
    }

    /**
     * Operación compleja SODA: Busca Instituto por expediente de alumno, saca al alumno de la lista y lo borra de la BD.
     * @return true si se encontró el instituto/alumno y se realizó el borrado correctamente.
     */
    public boolean expulsarAlumnoExp(String exp)
    {
        boolean status = false;
        if (bd != null) 
        {
            // 1. Buscamos el INSTITUTO que contiene al alumno con ese expediente
            Query query = bd.query();
            query.constrain(Instituto.class);
            
            // Navegamos: Instituto -> listaAlumnos -> expediente
            query.descend("listaAlumnos").descend("expediente").constrain(exp);

            ObjectSet<Instituto> result = query.execute();
            
            if (result.hasNext()) 
            {
                Instituto insti = result.next();
                
                // 2. Buscamos al alumno concreto dentro de la lista para sacarlo
                // (Necesitamos el objeto exacto para usar remove)
                List<Alumno> lista = insti.getListaAlumnos();
                Alumno alumnoABorrar = null;
                
                for (Alumno al : lista) 
                {
                    if (al.getExpediente().equals(exp)) 
                    {
                        alumnoABorrar = al;
                        break;
                    }
                }

                if (alumnoABorrar != null)
                {
                    // 3. Lo quitamos de la lista y guardamos el Instituto (actualiza la lista)
                    lista.remove(alumnoABorrar);
                    bd.store(insti); 
                    
                    // 4. Ahora sí, borramos el objeto Alumno de la BD
                    bd.delete(alumnoABorrar);
                    
                    status = true;
                    LOG.debug("Se ha expulsado del instituto y borrado al alumno con expediente: " + exp);
                }
            }
            else
            {
                LOG.warn("No se encontró ningún instituto con un alumno con expediente: " + exp);
            }
        }
        return status;
    }

    /**
     * Wrapper de cambiarIdInstituto. Modifica el ID de un instituto.
     * @return true si la operación fue exitosa.
     */
    public boolean modificarInstituto(Instituto institutoAModificar, Integer nuevoId)
    {
        return cambiarIdInstituto(institutoAModificar, nuevoId);
    }

    // --- TRANSACCIONES Y CIERRE ---

    /**
     * Cierra la conexión con la base de datos.
     * @return true si se cerró correctamente.
     */
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

    /**
     * Deshace la transacción actual (Rollback).
     */
    public void rollbackTransaction() 
    {
        if (bd != null) 
        {
            bd.rollback();
            LOG.debug("Rollback ejecutado");
        }
    }

    /**
     * Confirma la transacción actual (Commit).
     */
    public void commitTransaction() 
    {
        if (bd != null) 
        {
            bd.commit();
            LOG.debug("Commit ejecutado");
        }
    }
}