package dev.mendo.persistencia.dao;

import java.util.List;

import dev.mendo.dominio.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UsuarioDAO {
    
    private EntityManager entityManager;
    public UsuarioDAO(EntityManager entityManager) 
    {
        this.entityManager = entityManager;
    }

    public void persistUsuario(Usuario usuario)
    {
        entityManager.persist(usuario);
    } 

    public void mergeUsuario(Usuario usuario)
    {
        entityManager.merge(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id)
    {
        return entityManager.find(Usuario.class, id);
    }

    public void removeUsuario(Usuario usuario)
    {
        Usuario managed = entityManager.contains(usuario) ? usuario : entityManager.merge(usuario);
        entityManager.remove(managed);
    }

    public List<Usuario> getAll(){
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);

        return query.getResultList();
    }
}
