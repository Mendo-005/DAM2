package mendo.dev.persistencia.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mendo.dev.dominio.modelo.Usuario;

public class UsuarioDAO {
    
     /**
     * Constructor con EntityManager
     */
    private final EntityManager entityManager;
    public UsuarioDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    /**
     *  Utiliza entityManager.persist() para guardar un Usuario
     * @param Usuario
     */
    public void crearUsuario(Usuario usuario){
        entityManager.persist(usuario);
    }

    /**
     *  Utiliza entityManager.remove() para borrar un Usuario
     * @param Usuario
     */
    public void borrarUsuario(Usuario usuario){
        Usuario usuarioManaged = entityManager.merge(usuario);
        entityManager.remove(usuarioManaged);
    }

    /**
     *  Utiliza entityManager.merge() para actualizar o crear un Usuario
     * @param Usuario
     */
    public void actualizarUsuario(Usuario usuario){
        entityManager.merge(usuario);
    }

    /**
     *  Utiliza entityManager.find() para buscar un Usuario
     * @param Usuario
     */
    public Usuario buscarUsuarioPorId(Long id){
        return entityManager.find(Usuario.class, id);
    }

    /**
     * Creamos una TypedQuery para generar una entityManager.createQuery()
     * devolvemos todos los Usuarios
     * @return List<Usuario>
     */
    public List<Usuario> buscarTodosLosUsuarios(){
        TypedQuery<Usuario> consulta = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
        List<Usuario> listaDeUsuarios = consulta.getResultList();
        return listaDeUsuarios;
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        TypedQuery<Usuario> consulta = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        consulta.setParameter("email", email);
        try {
            return consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
