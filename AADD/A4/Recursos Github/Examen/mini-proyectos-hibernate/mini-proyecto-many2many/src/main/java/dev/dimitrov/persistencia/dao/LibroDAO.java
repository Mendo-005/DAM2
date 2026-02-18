package dev.dimitrov.persistencia.dao;

import java.util.HashSet;
import java.util.Set;

import dev.dimitrov.dominio.modelo.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LibroDAO {
    private EntityManager em;

    public LibroDAO(EntityManager e){
        em = e;
    }

    public void saveLibro(Libro l){
        em.persist(l);
    }

    public Libro getLibroById(Long id){
        return em.find(Libro.class, id);
    }

    public Libro mergeLibro(Libro l){
        return em.merge(l);
    }
    
    public void deleteLibro(Libro l){
        em.remove(l);
    }

    public Set<Libro> getAll(){
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l",Libro.class);
        return new HashSet<>(query.getResultList());
    }
}
