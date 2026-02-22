package dev.mendo.persistencia.dao;

import dev.mendo.dominio.modelo.Alumno;
import jakarta.persistence.EntityManager;

public class AlumnoDAO {

    private EntityManager entityManager;

    public AlumnoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persistAlumno(Alumno alumno){
        entityManager.persist(alumno);
    }

    public Alumno findAlumnoId(Integer id){
        return entityManager.find(Alumno.class, id);
    }

}
