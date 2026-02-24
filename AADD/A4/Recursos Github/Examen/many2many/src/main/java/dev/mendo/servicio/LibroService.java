package dev.mendo.servicio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Autor;
import dev.mendo.dominio.modelo.Libro;
import dev.mendo.persistencia.dao.LibroDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class LibroService {
    
     private static final Logger LOG = LoggerFactory.getLogger(LibroService.class);

    public void crearLibro(Libro libro)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        LibroDAO libroDAO = null;

        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            libroDAO = new LibroDAO(entityManager);

            transaction.begin();

            libroDAO.persistLibro(libro);;
            
            transaction.commit();
            LOG.trace("Se ha creado el libro correctamente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el libro: " + e.getMessage());
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

    public List<Autor> reporteTodosAutoresDeLibro(Libro libro)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        LibroDAO libroDAO = null;
        List<Autor> reporte = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            libroDAO = new LibroDAO(entityManager);

            transaction.begin();

            reporte = libroDAO.getAllAutorFromLibro(libro);

            transaction.commit();
            LOG.trace("Se ha creado el reporte de autores del libro: " + libro.getTitulo());
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error en la creacion de el reporte del libro: " + e.getMessage());
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
