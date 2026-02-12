package es.ciudadescolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("PersistenciaInstituto");
    public static void main(String[] args) 
    {
        Alumno al = new Alumno("Juan", "juan3@ciudadescolar.es");

        guardarAlumno(al);

        actualizarEmailAlumno(2L, "juan4@ciudadescolar.es");

        if (EMF.isOpen())
        {
            EMF.close();
            LOG.debug("Se ha cerrado el pool de conexiones con la BD");
        }
    }

    private static boolean guardarAlumno(Alumno al)
    {
        boolean estado = false;
        EntityManager em = null;
        EntityTransaction trans = null;

        
        // la instancia 'al' está en estado transitorio (en la RAM, si apago lo pierdo)
        LOG.info(al.toString());
        try
        {
            // le pedimos al pool de conexiones una conexión existente sin usar
            em = EMF.createEntityManager();

            trans = em.getTransaction();
            // siempre necesito una transacción SIEMPRE para modificar la BD (altas, bajas y modificaciones)
            trans.begin();

            em.persist(al);
            LOG.debug("la instancia de alumno pasa a estar administrada o persistente");

            trans.commit();
            LOG.debug("El estado de la instancia se ha guardado en la BD");

            estado = true;

            LOG.info(al.toString());
            
        }
        catch (RuntimeException e)
        {
            // las excepciones propagadas por motivos de persistencia extienden de RuntimeException (no poner Exception)
            // dichas excepciones son NO COMPROBADAS.

            LOG.error("Error durante la persistencia de datos: "+e.getMessage());
            if (trans != null && trans.isActive())
            {
                trans.rollback();
                LOG.debug("Se ha llevado a cabo el rollback de la transacción");
            }
        }
        finally
        {
            if (em != null && em.isOpen())
            {
                em.close();
                LOG.debug("Se ha cerrado la conexión con la BD");
            }

        }
        return estado;
    }

    private static boolean actualizarEmailAlumno (Long idAlumno, String nuevoCorreo)
    {
        boolean estado = false;

        EntityManager em = null;
        
        EntityTransaction trans =  null;

        Alumno alumnoBuscado = null;

        try
        {
            em =EMF.createEntityManager();
            trans = em.getTransaction();

            trans.begin();
            // lógica de actualización
            // find busca por PK y devuelve instancia administrada
            alumnoBuscado = em.find(Alumno.class, idAlumno); 
            if (alumnoBuscado != null)
                alumnoBuscado.setEmail(nuevoCorreo);
            else
                LOG.warn("El alumno buscado con ID: "+ idAlumno + " no se ha encontrado");
            trans.commit();

             LOG.info(alumnoBuscado.toString());
        }
        catch(RuntimeException e)
        {
            if(trans != null && trans.isActive())
                trans.rollback();
        }
        finally
        {
            if (em != null && em.isOpen())
                em.close();
        }

        return estado;
    }
}