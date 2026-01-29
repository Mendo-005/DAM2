package es.ciudadescolar.servicios;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Examen;
import es.ciudadescolar.persistencia.dao.AlumnoDAO;
import es.ciudadescolar.persistencia.dao.ExamenDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ExamenServicio 
{
    private static final Logger LOG = LoggerFactory.getLogger(ExamenServicio.class);

    public void aniadirExamenAAlumno(Long idAlumno, String modulo, LocalDate fecha, Double nota)
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            ExamenDAO examenDAO = new ExamenDAO(em);

            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null) 
            {
                LOG.warn("El alumno al que se quiere añadir examen, no existe");
                trans.rollback();
                return;
            }
            
            Examen examen = new Examen();
            examen.setModulo(modulo);
            examen.setFecha(fecha);
            examen.setNota(nota);
                
            // Debemos relacionar las entidades Alumno y Examen
            examen.setAlumno(alumno);
            alumno.añadirExamenes(examen);

            examenDAO.guardarExamen(examen);

            trans.commit();
            LOG.info("Examen registrado en el alumno: "+ examen.toString()); // Cuidado en las relaciones bidireccionales, el toString no sea  un bucle
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error durante la transacción: "+ e.getMessage());

            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        } 
        finally 
        {
            try 
            {
                if (em != null && em.isOpen()) 
                {
                    em.close();
                    LOG.debug("Cerrado EntityManager");
                }
            } 
            catch (RuntimeException e) 
            {
                LOG.error("Error cerrando EntityManager:: "+ e.getMessage());
                // La transacción ya se ha intentado commit o rollback. 
                // Aquí no propagamos la excepción para evitar ocultar la excepción original que pudo motivar el rollback o fallo de negocio.
            }
        }

    }
}
