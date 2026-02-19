package dev.mendo.servicio;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Alumno;
import dev.mendo.dominio.modelo.Contacto;
import dev.mendo.persistencia.dao.AlumnoDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlumnoService {

    private static final Logger LOG = LoggerFactory.getLogger(AlumnoService.class);

    public void añadirAlumno(String nombre, String apellido, Integer id, LocalDate fechaNac, String email, String telefono){
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = JPAUtil.getEntityTransaction(entityManager);
        
        try {           

            transaction.begin();
            AlumnoDAO alumnoDAO = new AlumnoDAO(entityManager);

            Alumno buscarAlumno = alumnoDAO.findAlumnoId(id);
            
            if (buscarAlumno == null) {
                // El alumno NO existe, crear uno nuevo
                Alumno nuevAlumno = new Alumno();
                nuevAlumno.setNomApe(nombre + " " + apellido);
                nuevAlumno.setFechaNac(fechaNac);
                
                // Persistir primero el alumno
                alumnoDAO.persistAlumno(nuevAlumno);
                
                // Crear contacto vinculado al alumno (Contacto es propietario)
                Contacto contacto = new Contacto(email, telefono);
                contacto.setAlumno(nuevAlumno);
                nuevAlumno.setContacto(contacto);
                
                // Persistir el Contacto. Su cascada ALL persistirá el Alumno nuevamente si es necesario
                entityManager.persist(contacto);
                transaction.commit();
                LOG.info("Se ha realizado el guardado del alumno: " + id + " con datos de contacto");
            }
            else{
                LOG.warn("El alumno con id " + id + " ya esta en la base de datos");
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
