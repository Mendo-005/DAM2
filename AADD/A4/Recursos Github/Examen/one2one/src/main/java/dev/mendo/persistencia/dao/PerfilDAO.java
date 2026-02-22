package dev.mendo.persistencia.dao;

import dev.mendo.dominio.modelo.Perfil;
import dev.mendo.dominio.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PerfilDAO {

    private EntityManager entityManager;
    public PerfilDAO(EntityManager entityManager) 
    {
        this.entityManager = entityManager;
    }

    public void persistPerfil(Perfil perfil)
    {
        entityManager.persist(perfil);
    }

    public Perfil getPerfilPorUsuario(Usuario usuario)
    {
        TypedQuery<Perfil> query = entityManager.createQuery("SELECT p FROM Perfil p WHERE p.usuario = :usuario", Perfil.class);
        query.setParameter("usuario", usuario);
        return query.getSingleResult();
    }

    public void mergePerfil(Perfil perfil)
    {
        entityManager.merge(perfil);
    }

    public void removePerfil(Perfil perfil)
    {
        Perfil managed = entityManager.contains(perfil) ? perfil : entityManager.merge(perfil);
        entityManager.remove(managed);
    }
}
