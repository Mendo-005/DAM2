package dev.dimitrov.persistencia.dao;
import java.util.List;

import dev.dimitrov.dominio.modelo.Perfil;
import dev.dimitrov.dominio.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PerfilDAO {
    private EntityManager em;

    public PerfilDAO(EntityManager e){
        em = e;
    }

    public void addPerfil(Perfil p){
        em.persist(p);
    }

    public Perfil getPerfilById(long id){
        return em.find(Perfil.class, id);
    }

    public Perfil getPerfilByUser(Usuario u){
        TypedQuery<Perfil> query = em.createQuery("SELECT p FROM Perfil p WHERE usuario = :usr",Perfil.class);
        query.setParameter("usr", u);
        List<Perfil> profiles = query.getResultList();
        return profiles.isEmpty() ? null : profiles.get(0);
    }

    public void mergePerfil(Perfil p){
        em.merge(p);
    }

    public void removePerfil(Perfil p){
        em.remove(p);
    }

    public List<Perfil> getAll(){
        TypedQuery<Perfil> query = em.createQuery("SELECT p FROM Perfil p;", Perfil.class);

        return query.getResultList();
    }
}
