package dev.mendo.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Alumno;
import dev.mendo.persistencia.dao.AlumnoDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlumnoService {

    private static final Logger LOG = LoggerFactory.getLogger(AlumnoService.class);

    public void añadirAlumno(String nombre, String apellido, Integer id){
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = JPAUtil.getEntityTransaction(entityManager);
        
        try {           

            transaction.begin();
            AlumnoDAO alumnoDAO = new AlumnoDAO(entityManager);

            Alumno buscarAlumno = alumnoDAO.findAlumnoId(id); 
            if (buscarAlumno != null) {

                Alumno nuevAlumno = new Alumno();
                nuevAlumno.setNomApe(nombre+apellido);
                nuevAlumno.setId(id);
                alumnoDAO.persistAlumno(nuevAlumno);
                transaction.commit();
            }
            else{
                LOG.warn("El alumno ya esta en la base de datos");
            }

        } catch (RuntimeException e) {
           LOG.error("Error añadiendo al alumno: " + e.getMessage());
           try {
            transaction.rollback();
            LOG.debug("Se ha realizado el rollback con exito");
        } catch (Exception e1) {
            LOG.error("Error en la ejecucion del rollback: " + e.getMessage());
        }
        }finally{

            if (entityManager.isOpen() || entityManager != null) 
            {
                entityManager.close();
            }
        }
    }

}
