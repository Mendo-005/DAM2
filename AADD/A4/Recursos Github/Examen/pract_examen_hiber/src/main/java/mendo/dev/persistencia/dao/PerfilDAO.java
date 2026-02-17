package mendo.dev.persistencia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mendo.dev.dominio.modelo.Perfil;

public class PerfilDAO {

    /**
     * Constructor con EntityManager
     */
    private final EntityManager entityManager;
    public PerfilDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    /**
     *  Utiliza entityManager.persist() para guardar un perfil
     * @param perfil
     */
    public void crearPerfil(Perfil perfil){
        entityManager.persist(perfil);
    }

    /**
     *  Utiliza entityManager.remove() para borrar un perfil
     * @param perfil
     */
    public void borrarPerfil(Perfil perfil){
        entityManager.remove(perfil);
    }

    /**
     *  Utiliza entityManager.merge() para actualizar o crear un perfil
     * @param perfil
     */
    public void actualizarPerfil(Perfil perfil){
        entityManager.merge(perfil);
    }

    /**
     *  Utiliza TypedQuery para buscar un perfil por userName
     * @param userName
     * @return Perfil encontrado o null
     */
    public Perfil buscarPerfilPorUserId(String userName){
        TypedQuery<Perfil> consulta = entityManager.createQuery("SELECT p FROM Perfil p WHERE p.userName = :userName", Perfil.class);
        consulta.setParameter("userName", userName);
        try {
            return consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
