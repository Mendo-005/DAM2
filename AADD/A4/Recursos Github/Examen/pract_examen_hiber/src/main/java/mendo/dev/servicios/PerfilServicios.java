package mendo.dev.servicios;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mendo.dev.dominio.modelo.Perfil;
import mendo.dev.dominio.modelo.Usuario;
import mendo.dev.persistencia.dao.PerfilDAO;
import mendo.dev.util.JPAUtil;

public class PerfilServicios {
    
    private static final Logger LOG = LoggerFactory.getLogger(PerfilServicios.class);

    public void crearNuevoPerfil(String userName, String password, Usuario usuario){
            
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();  
        try 
        {
            transaction.begin();
            
            PerfilDAO perfilDao = new PerfilDAO(entityManager);
            
            // Si el usuario viene de otra sesión (detached), hacemos merge para re-attacharlo
            Usuario usuarioManaged = entityManager.merge(usuario);
            
            Perfil perfil = new Perfil();
            perfil.setPassword(password);
            perfil.setUserName(userName);
            perfil.setUsuario(usuarioManaged);

            perfilDao.crearPerfil(perfil);

            transaction.commit();
            LOG.debug("Se ha guardado el perfil: " + perfil.toString());
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el nuevo perfil: " + e.getMessage());
            if (transaction != null && transaction.isActive()) 
            {
                transaction.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        }
        finally
        {   
            try 
            {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }  
            catch (Exception e) 
            {
                LOG.error("Error cerrando EntityManager:: "+ e.getMessage());
            }
            
        }
    }

    public void actualizarPerfil(String newPassword, String userName){
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = JPAUtil.getEntityTransaction(entityManager);

        try 
        {
            transaction.begin();

            PerfilDAO perfilDAO = new PerfilDAO(entityManager);
            Perfil perfil2 = perfilDAO.buscarPerfilPorUserId(userName);
            
            perfil2.setPassword(newPassword);

            perfilDAO.actualizarPerfil(perfil2);

            transaction.commit();
            LOG.debug("Se ha actualizado el perfil: " + perfil2.toString());
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la actualizacion de el nuevo perfil: " + e.getMessage());
            if (transaction != null && transaction.isActive()) 
            {
                transaction.rollback();
                LOG.debug("Rollback de la transaccion");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        }
        finally
        {   
            try 
            {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    LOG.debug("Se ha cerrado el entityManager");
                }
            }  
            catch (Exception e) 
            {
                LOG.error("Error cerrando EntityManager:: "+ e.getMessage());
            }
            
        }
    }

}
