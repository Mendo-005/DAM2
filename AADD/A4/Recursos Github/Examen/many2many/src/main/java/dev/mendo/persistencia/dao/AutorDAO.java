package dev.mendo.persistencia.dao;

import java.util.List;

import dev.mendo.dominio.modelo.Autor;
import dev.mendo.dominio.modelo.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AutorDAO {
    
    private EntityManager entityManager;

    public AutorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persistAutor(Autor autor)
    {
        entityManager.persist(autor);
    }

    public void mergeAutor(Autor autor)
    {
        entityManager.merge(autor);
    }

    public Autor findAutorById(Long id)
    {
        return entityManager.find(Autor.class, id);
    }

    public void removeAutor(Autor autor)
    {
        Autor managed = entityManager.contains(autor) ? autor : entityManager.merge(autor);
        entityManager.remove(managed); 
    }

    public List<Libro> getAllLibrosFromAutor(Autor autor)
    {
        List<Libro> libros = null;
        TypedQuery<Libro> query = entityManager.createQuery("SELECT l FROM Libro l WHERE :a MEMBER OF l.autores",Libro.class);
        query.setParameter("a", autor);
        libros = query.getResultList();
        return libros;
    }

    public List<String> getReporte()
    {
        List<String> reporte = null;
        TypedQuery<String> query = entityManager.createQuery(
            "SELECT CONCAT(a.nombre, ' | ', l.titulo) " +
            "FROM Autor a " +
            "JOIN a.libros l " +
            "ORDER BY a.nombre, l.titulo", 
            String.class);
        reporte = query.getResultList();
        return reporte;
    }

}
