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

        Alumno alumno1 = new Alumno("Juan", "juan@c.escolar.es");
        // La instancia alumno1 esta en estado transitorio (En la RAM)        
        guardarAlumno(alumno1);
        actualizarEmailAlumno(1L, "jose.c@educa.org");

        if (EMF.isOpen()) 
            {
                EMF.close();
                LOG.debug("Se ha cerrado el pool de conexiones con la BD");    
            }
    }

    private static boolean guardarAlumno(Alumno alumno)
    {

        boolean status = false;
        EntityManager em = null;
        EntityTransaction trans = null;

        try 
        {
            em = EMF.createEntityManager();
            trans = em.getTransaction();
            // Siempre necesito una transacción para modificar la BD 
            trans.begin();

            // Hacemos que el alumno se persista (Se ha guardado)
            em.persist(alumno);
            LOG.debug("La instancia alumno pasa a estar administrada o persistente");

            alumno.setEmail("j.a@c.escolar.org");


            trans.commit();
            LOG.info("El estado de la instancia se ha guardado en la BD");
            /*  
                - A partir del commit, la instancia pasa a estar separada (detached)
                - Cambios posteriores no se vuelcan a la BD
            */
            alumno.setNombre("Paco");

            status = true;
            LOG.info(alumno.toString());

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
        }

        return status;
    }

    private static boolean actualizarEmailAlumno(Long idAlumno, String nuevoEmail)
    {
        boolean status = false;

        EntityManager em = null;
        EntityTransaction trans = null;

        Alumno alumnoBuscado = null;
        try 
        {
            em = EMF.createEntityManager();
            trans = em.getTransaction();
            
            alumnoBuscado = em.find(Alumno.class, idAlumno); // Busca por PK y devuelve el alumno o null
            if (alumnoBuscado != null) 
            {
                alumnoBuscado.setEmail(nuevoEmail);    
            }
            
            trans.commit();
            LOG.debug("Commit realizado con exito ");
            LOG.info(alumnoBuscado.toString());
        } 
        catch (Exception e) 
        {
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
        }
        return  status;
    }
}