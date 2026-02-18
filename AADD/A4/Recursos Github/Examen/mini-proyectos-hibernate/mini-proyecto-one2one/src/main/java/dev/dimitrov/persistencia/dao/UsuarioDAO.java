package dev.dimitrov.persistencia.dao;

import java.util.List;

import dev.dimitrov.dominio.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UsuarioDAO {
    private EntityManager em;

    public UsuarioDAO(EntityManager e){
        em = e;
    }

    public void addUsuario(Usuario p){
        em.persist(p);
    }

    public Usuario getUsuarioById(long id){
        return em.find(Usuario.class, id);
    }

    public Usuario getUsuarioByEmail(String email){
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email",Usuario.class);
        query.setParameter("email", email);
        List<Usuario> usrs = query.getResultList();
        return usrs.size() != 0 ? usrs.get(0) : null;
    }

    public void mergeUsuario(Usuario p){
        em.merge(p);
    }

    public void removeUsuario(Usuario p){
        em.remove(p);
    }

    public List<Usuario> getAll(){
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);

        return query.getResultList();
    }
}
