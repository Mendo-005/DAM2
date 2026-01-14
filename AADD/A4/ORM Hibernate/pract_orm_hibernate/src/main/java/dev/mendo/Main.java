package dev.mendo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.instituto.Alumno;
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

        Alumno alumno1 = new Alumno("Juan", "juan@c.escolar.es");
        // La instancia alumno1 esta en estado transitorio (En la RAM)
        try 
        {
            em = EMF.createEntityManager();
            trans = em.getTransaction();
            // Siempre necesito una transacción para modificar la BD 
            trans.begin();

            // Hacemos que el alumno se persista (Se ha guardado)
            em.persist(alumno1);
            LOG.debug("La instancia alumno1 pasa a estar administrada o persistente");

            alumno1.setEmail("j.a@c.escolar.org");


            trans.commit();
            LOG.info("El estado de la instancia se ha guardado en la BD");
            /*  
                - A partir del commit, la instancia pasa a estar separada (detached)
                - Cambios posteriores no se vuelcan a la BD
            */
            alumno1.setNombre("Paco");
            LOG.info(alumno1.toString());
        }
        catch (RuntimeException e) 
        {
            // Las excepsciones propagadas por motivos de persistencia extienden de RuntimeException (no usar Exception)
            LOG.error("Error durante la persistencia de datos: " + e.getMessage());
            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Se ha llevado a cabo el rollback de la transaccion");
            }
        }
        finally
        {
            if (em != null && em.isOpen()) 
            {
                em.close();
                LOG.debug("Se ha cerrado la conexion con la BD");
            }
            if (EMF.isOpen()) 
            {
                EMF.close();
                LOG.debug("Se ha cerrado el pool de conexiones con la BD");    
            }
        }
    }
}