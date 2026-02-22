package dev.mendo.servicio;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Perfil;
import dev.mendo.dominio.modelo.Usuario;
import dev.mendo.persistencia.dao.PerfilDAO;
import dev.mendo.persistencia.dao.UsuarioDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UsuarioServicio {
    
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServicio.class);

    public void crearUsuarioCascadePerfil(Usuario usuario, Perfil perfil)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        UsuarioDAO usuarioDAO = null;
        PerfilDAO perfilDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            usuarioDAO = new UsuarioDAO(entityManager);
            perfilDAO = new PerfilDAO(entityManager);

            transaction.begin();    

            usuarioDAO.persistUsuario(usuario);
            perfilDAO.persistPerfil(perfil);

            perfil.setUsuario(usuario);
            transaction.commit();
            LOG.debug("Se ha creado correctamente el usuario y el perfil");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de del usuario y su perfil: " + e.getMessage());
            try {
                transaction.rollback();
                LOG.debug("Rollback realizado");
            } catch (Exception e1) {
                LOG.error("Error en el rollback: " + e1.getMessage());
            }
        }
        finally
        {
            if (entityManager != null) 
            {
                entityManager.close();
            }
        }
    }

    public void actualizarPrefilDelUsuario(Usuario usuario, Perfil newPerfil)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        UsuarioDAO usuarioDAO = null;
        PerfilDAO perfilDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            usuarioDAO = new UsuarioDAO(entityManager);
            perfilDAO = new PerfilDAO(entityManager);
            transaction.begin();    

            // Verificar que el usuario exista (Para actualizar hay que buscar en la BD)
            usuario = usuarioDAO.buscarUsuarioPorId(usuario.getId());
            if (usuario == null) {
                throw new RuntimeException("El usuario no existe en la DB");
            }
            
            Perfil perfilAActualizar = perfilDAO.getPerfilPorUsuario(usuario);
            perfilAActualizar.setUsername(newPerfil.getUsername());
            perfilAActualizar.setPassword_hash(newPerfil.getPassword_hash());

            perfilDAO.mergePerfil(perfilAActualizar);

            transaction.commit();
            LOG.debug("Se ha actualizado correctamente el usuario");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la actualizacion de del usuario: " + e.getMessage());
            try {
                transaction.rollback();
                LOG.debug("Rollback realizado");
            } catch (Exception e1) {
                LOG.error("Error en el rollback: " + e1.getMessage());
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) 
            {
                entityManager.close();
            }
        }
    }

    public void borrarUsuarioYPerfil(Usuario usuario)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        UsuarioDAO usuarioDAO = null;
        PerfilDAO perfilDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            usuarioDAO = new UsuarioDAO(entityManager);
            perfilDAO = new PerfilDAO(entityManager);

            transaction.begin();    

            // Verificar que el usuario exista (Para actualizar hay que buscar en la BD)
            usuario = usuarioDAO.buscarUsuarioPorId(usuario.getId());
            if (usuario == null) {
                throw new RuntimeException("El usuario no existe en la DB");
            }

            //perfilDAO.removePerfil(perfilDAO.getPerfilPorUsuario(usuario));
            usuarioDAO.removeUsuario(usuario);

            transaction.commit();
            LOG.debug("Se ha borrado correctamente el usuario y el perfil");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la borrado de del usuario y su perfil: " + e.getMessage());
            try {
                transaction.rollback();
                LOG.debug("Rollback realizado");
            } catch (Exception e1) {
                LOG.error("Error en el rollback: " + e1.getMessage());
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) 
            {
                entityManager.close();
            }
        }
    }

    public List<String> reporteDeUsuarios()
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        UsuarioDAO usuarioDAO = null;
        PerfilDAO perfilDAO = null;
        List<String> reporte =  new ArrayList<>();

        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            usuarioDAO = new UsuarioDAO(entityManager);
            perfilDAO = new PerfilDAO(entityManager);
            transaction.begin();    
            
            List<Usuario> listaUsuarios = usuarioDAO.getAll();
            for (Usuario usuario : listaUsuarios) 
            {
                String username = perfilDAO.getPerfilPorUsuario(usuario).getUsername();
                String entrada = usuario.getNombre() + " | " + usuario.getApellidos() + " | " + usuario.getEmail() + " | " + username;
                reporte.add(entrada);
            }
            transaction.commit();
            LOG.debug("Se ha creado del reporte");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion del reporte: " + e.getMessage());
            try {
                transaction.rollback();
                LOG.debug("Rollback realizado");
            } catch (Exception e1) {
                LOG.error("Error en el rollback: " + e1.getMessage());
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) 
            {
                entityManager.close();
            }
        }
        return reporte;
    }
}
