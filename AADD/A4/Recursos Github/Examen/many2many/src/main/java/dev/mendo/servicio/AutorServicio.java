package dev.mendo.servicio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Autor;
import dev.mendo.dominio.modelo.Libro;
import dev.mendo.persistencia.dao.AutorDAO;
import dev.mendo.persistencia.dao.LibroDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AutorServicio {
    
    private static final Logger LOG = LoggerFactory.getLogger(AutorServicio.class);

    public void crearAutor(Autor autor)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        AutorDAO autorDAO = null;

        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            autorDAO = new AutorDAO(entityManager);

            transaction.begin();

            autorDAO.persistAutor(autor);
            
            transaction.commit();
            LOG.trace("Se ha creado el autor correctamente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el autor: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Rollback realizado con exito ");
            } catch (Exception e1) {
                LOG.error("Error al realizar el rollback");
            }
        } 
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    
    public void asociarAutorxLibro(Autor autor, Libro libro)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        AutorDAO autorDAO = null;
        LibroDAO libroDAO = null;

        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            autorDAO = new AutorDAO(entityManager);
            libroDAO = new LibroDAO(entityManager);

            transaction.begin();

            if (autorDAO.findAutorById(autor.getId_autor()) == null) {
                autorDAO.persistAutor(autor);
                LOG.warn("Este autor no estaba en la base de datos");
            }
            if (libroDAO.findLibroById(libro.getId_libro()) == null) {
                libroDAO.persistLibro(libro);
                LOG.warn("Este libro no estaba en la base de datos");

            }

            autor.addLibro(libro);
            libro.addAutor(autor);
            autorDAO.mergeAutor(autor);
            libroDAO.mergeLibro(libro);

            transaction.commit();
            LOG.trace("Se ha asociado el autor y el libro correctamente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la asociacion de el autor y el libro: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Rollback realizado con exito ");
            } catch (Exception e1) {
                LOG.error("Error al realizar el rollback");
            }
        } 
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public void crearAutorYLibroCascade(Autor autor, Libro libro)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        AutorDAO autorDAO = null;
        LibroDAO libroDAO = null;

        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            autorDAO = new AutorDAO(entityManager);
            libroDAO = new LibroDAO(entityManager);

            transaction.begin();

            autor.addLibro(libro);
            libro.addAutor(autor);
            autorDAO.persistAutor(autor);
            libroDAO.persistLibro(libro);

            transaction.commit();
            LOG.trace("Se ha creado el autor y el libro correctamente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creado de el autor y el libro: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Rollback realizado con exito ");
            } catch (Exception e1) {
                LOG.error("Error al realizar el rollback");
            }
        } 
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public List<Libro> reporteTodosLibrosDeAutor(Autor autor)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        AutorDAO autorDAO = null;
        List<Libro> reporte = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            autorDAO = new AutorDAO(entityManager);

            transaction.begin();

            reporte = autorDAO.getAllLibrosFromAutor(autor);

            transaction.commit();
            LOG.trace("Se ha creado el reporte de libros del autor: " + autor.getNombre());
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el reporte del autor: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Rollback realizado con exito ");
            } catch (Exception e1) {
                LOG.error("Error al realizar el rollback");
            }
        } 
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return reporte;
    }

    public List<String> reporteGenereal() 
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        AutorDAO autorDAO = null;
        List<String> reporte = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            autorDAO = new AutorDAO(entityManager);

            transaction.begin();

            reporte = autorDAO.getReporte();

            transaction.commit();
            LOG.trace("Se ha creado el reporte general");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el reporte general: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Rollback realizado con exito ");
            } catch (Exception e1) {
                LOG.error("Error al realizar el rollback");
            }
        } 
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return reporte;     
    }    
}
