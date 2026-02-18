package mendo.dev.servicios;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mendo.dev.dominio.modelo.Usuario;
import mendo.dev.persistencia.dao.UsuarioDAO;
import mendo.dev.util.JPAUtil;

public class UsuarioServicio {
    
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServicio.class);

    /**
     * Crea un nuevo usuario en la base de datos
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param fecha_nacimiento Fecha de nacimiento
     * @param email Email del usuario
     */
    public void crearNuevoUsuario(String nombre, String apellidos, LocalDate fecha_nacimiento, String email) {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            
            Usuario usuario = new Usuario(nombre, apellidos, fecha_nacimiento, email);
            
            usuarioDao.crearUsuario(usuario);
            
            transaction.commit();
            LOG.debug("Se ha guardado el usuario: " + usuario.toString());
        } 
        catch (RuntimeException e) {
            LOG.error("Error en la creación del nuevo usuario: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                LOG.debug("Rollback de la transacción");
            }
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public Usuario buscarUsuarioPorId(Long id) {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        
        try {
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            return usuarioDao.buscarUsuarioPorId(id);
        }
        catch (RuntimeException e) {
            LOG.error("Error al buscar usuario por ID: " + e.getMessage());
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        
        try {
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            return usuarioDao.buscarUsuarioPorEmail(email);
        }
        catch (RuntimeException e) {
            LOG.error("Error al buscar usuario por ID: " + e.getMessage());
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene todos los usuarios de la base de datos
     * @return Lista de usuarios
     */
    public List<Usuario> listarTodosLosUsuarios() {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        
        try {
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            return usuarioDao.buscarTodosLosUsuarios();
        }
        catch (RuntimeException e) {
            LOG.error("Error al listar usuarios: " + e.getMessage());
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }

    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con los datos actualizados
     */
    public void actualizarUsuario(Usuario usuario) {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            usuarioDao.actualizarUsuario(usuario);
            
            transaction.commit();
            LOG.debug("Se ha actualizado el usuario: " + usuario.toString());
        }
        catch (RuntimeException e) {
            LOG.error("Error al actualizar usuario: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                LOG.debug("Rollback de la transacción");
            }
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }

    /**
     * Elimina un usuario de la base de datos
     * @param usuario Usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
        
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            
            UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
            usuarioDao.borrarUsuario(usuario);
            
            transaction.commit();
            LOG.debug("Se ha eliminado el usuario: " + usuario.toString());
        }
        catch (RuntimeException e) {
            LOG.error("Error al eliminar usuario: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                LOG.debug("Rollback de la transaccion");
            }
            throw e;
        }
        finally {
            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }
            catch (Exception e) {
                LOG.error("Error cerrando EntityManager: " + e.getMessage());
            }
        }
    }
}
