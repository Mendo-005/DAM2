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
        EntityManager em = null;
        EntityTransaction trans = null;

        Alumno al = new Alumno("Juan", "juan3@ciudadescolar.es");
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

            al.setEmail("jg@ciudadescolar.es");

            trans.commit();
            LOG.debug("El estado de la instancia se ha guardado en la BD");

            /*  a partir de aquí (porque se ha cerrado la sesión) la instancia pasa a estar separada (detached). 
                Cambios posteriores no se vuelcan a la BD
            */
           
            al.setNombre("Paco");
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

            if (EMF.isOpen())
            {
                EMF.close();
                LOG.debug("Se ha cerrado el pool de conexiones con la BD");
            }
        }

    }
}