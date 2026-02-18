package dev.dimitrov.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dimitrov.dominio.modelo.Perfil;
import dev.dimitrov.dominio.modelo.Usuario;
import dev.dimitrov.persistencia.dao.PerfilDAO;
import dev.dimitrov.persistencia.dao.UsuarioDAO;
import dev.dimitrov.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UsuarioServicio {
    public static final Logger LOG = LoggerFactory.getLogger(UsuarioServicio.class);

    public void addUsuarioConPerfil(Usuario u, Perfil p) {
        EntityManager em = null;
        EntityTransaction et = null;
        UsuarioDAO ud = null;
        PerfilDAO pd = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            et.begin();
            ud = new UsuarioDAO(em);
            pd = new PerfilDAO(em);

            ud.addUsuario(u); // persisto el usuario

            pd.addPerfil(p); // persisto el perfil

            p.setUsuario(u); // Luego hago un set del usuario persistente a perfil persistente

            et.commit();
            LOG.info("Terminada la transaccion");
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Ocurrió un problema mientras se añadía un usuario con perfil: " + e.getMessage());
        } finally {
            JPAUtil.secureCloseEm(em);
        }
    }

    public void removeUsuario(Usuario u) {
        EntityManager em = null;
        EntityTransaction et = null;
        UsuarioDAO ud = null;
        PerfilDAO pd = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            et.begin();
            ud = new UsuarioDAO(em);
            pd = new PerfilDAO(em);
            u = ud.getUsuarioByEmail(u.getEmail());
            if (u == null) {
                throw new RuntimeException("No se ha borrado nada");
            } else {
                Perfil p = pd.getPerfilByUser(u);
                pd.removePerfil(p);
                ud.removeUsuario(u);
            }

            et.commit();
            LOG.info("Terminado el borrado del usuario: " + u.toString());
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Ocurrió un problema mientras se borraba un usuario: " + e.getMessage());
        } finally {
            JPAUtil.secureCloseEm(em);
        }
    }

    public void actualizarPerfilUsuario(Usuario u, Perfil nuevoPerfil) {
        EntityManager em = null;
        EntityTransaction et = null;
        UsuarioDAO ud = null;
        PerfilDAO pd = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            et.begin();
            ud = new UsuarioDAO(em);
            pd = new PerfilDAO(em);

            u = ud.getUsuarioByEmail(u.getEmail()); // recupero el usuario persistente
            if (u == null) {
                throw new RuntimeException("El usuario no existe en la db");
            }

            Perfil antiguoPerfil = pd.getPerfilByUser(u); // recupero el perfil antiguo

            pd.removePerfil(antiguoPerfil); // lo borro

            pd.addPerfil(nuevoPerfil); // persisto el nuevo perfil

            nuevoPerfil.setUsuario(u);

            et.commit();
            LOG.info("Terminada la actualizacion de perfil del usuario: " + u.toString());
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Ocurrió un problema mientras se actualizaba el perfil un usuario: " + e.getMessage());
        } finally {
            JPAUtil.secureCloseEm(em);
        }
    }

    public void generarInforme() {
        UsuarioDAO ud = null;
        PerfilDAO pd = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            ud = new UsuarioDAO(em);
            pd = new PerfilDAO(em);
            et.begin();
            List<Usuario> users = ud.getAll();

            System.out.println("============================================");
            System.out.println("nombre | apellidos | email | username");
            if (!users.isEmpty()) {
                for (Usuario u : users) {
                    String username = pd.getPerfilByUser(u).getUsername();
                    System.out.println(u.getNombre() + " " + u.getApellidos() + " " + u.getEmail() + " " + username);
                }
            }
            else{
                System.out.println("No se encontraron usuarios...");
            }

            System.out.println("============================================");

            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error durante el mostrado de usuario con sus perfiles");
        } finally {
            JPAUtil.secureCloseEm(em);
        }
    }
}
