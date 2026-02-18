package dev.dimitrov.persistencia.dao;
import java.util.HashSet;
import java.util.Set;

import dev.dimitrov.dominio.modelo.Autor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AutorDAO {
    private EntityManager em;

    public AutorDAO(EntityManager e){
        em = e;
    }

    public void saveAutor(Autor a){
        em.persist(a);
    }

    public Autor getAutorById(Long id){
        return em.find(Autor.class, id);
    }

    public Autor mergeAutor(Autor a){
        return em.merge(a);
    }
    
    public void deleteAutor(Autor a){
        em.remove(a);
    }

    public Set<Autor> getAll(){
        TypedQuery<Autor> query = em.createQuery("SELECT a FROM Autor a",Autor.class);
        return new HashSet<>(query.getResultList());
    }
}

