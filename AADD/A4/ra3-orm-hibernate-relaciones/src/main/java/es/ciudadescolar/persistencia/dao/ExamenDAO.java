package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Examen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ExamenDAO 
{

    private final EntityManager entityManager;

    public ExamenDAO (EntityManager entity)
    {
        this.entityManager = entity;
    }

    public void guardarExamen(Examen examen)
    {
        entityManager.persist(examen);
    }

    public void eliminarExamen(Examen examen)
    {
        entityManager.remove(examen);
    }

    public void actualizarExamen(Examen examen)
    {
        entityManager.merge(examen);
    }

    public Examen buscarExamen(Long idExamen)
    {
        return entityManager.find(Examen.class, idExamen);
    }

    public List<Examen> buscarExamenesPorAlumno(Alumno alumnoABuscar)
    {
        TypedQuery<Examen> query = entityManager.createQuery("SELECT e from Examen e WHERE e.alumno = :alumno", Examen.class); // JPQL
        query.setParameter("alumno", alumnoABuscar);

        return query.getResultList();
    }
}
