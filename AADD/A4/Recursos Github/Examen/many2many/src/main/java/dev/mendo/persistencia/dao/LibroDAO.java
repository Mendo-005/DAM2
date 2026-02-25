package dev.mendo.persistencia.dao;

import java.util.List;

import dev.mendo.dominio.modelo.Autor;
import dev.mendo.dominio.modelo.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LibroDAO {
    
    private EntityManager entityManager;

    public LibroDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persistLibro(Libro libro)
    {
        entityManager.persist(libro);
    }

    public void mergeLibro(Libro libro)
    {
        entityManager.merge(libro);
    }

    public Libro findLibroById(Long id)
    {
        return entityManager.find(Libro.class, id);
    }

    public void removeLibro(Libro libro)
    {
        Libro managed = entityManager.contains(libro) ? libro : entityManager.merge(libro);
        entityManager.remove(managed); 
    }

    public List<Autor> getAllAutorFromLibro(Libro libro)
    {
        List<Autor> autores = null;
        TypedQuery<Autor> query = entityManager.createQuery("SELECT a FROM Autor a JOIN a.libros l WHERE l = :l",Autor.class);
        query.setParameter("l", libro);
        autores = query.getResultList();
        return autores;
    }

}
