package dev.dimitrov.servicios;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dimitrov.dominio.modelo.Autor;
import dev.dimitrov.dominio.modelo.Libro;
import dev.dimitrov.persistencia.dao.AutorDAO;
import dev.dimitrov.persistencia.dao.LibroDAO;
import dev.dimitrov.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class LibroServicio {
    public static final Logger LOG = LoggerFactory.getLogger(LibroServicio.class);
    public Long addLibro(Libro l){
        Long id = -1l;
        EntityManager em = null;
        LibroDAO ldao = null;
        EntityTransaction et = null;

        try {
            em = JPAUtil.getEntityManager();
            ldao = new LibroDAO(em);
            et = em.getTransaction();
            et.begin();
            ldao.saveLibro(l);
            et.commit();
            id = l.getId();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("An error ocurred while adding a book: "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }

        return id;
    }

    // Si no existen alguno de los 2 sale
    public void asociarLibroAutor(Long libroId, Long autorId){
        EntityManager em = null;
        LibroDAO ldao = null;
        AutorDAO adao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            ldao = new LibroDAO(em);
            adao = new AutorDAO(em);
            et = em.getTransaction();
            
            et.begin();
            Autor autor = adao.getAutorById(autorId);
            Libro libro = ldao.getLibroById(libroId);
            

            if(autor == null || libro == null){
                throw new RuntimeException("El libro o el autor especificado no existe");
            }
            
            libro.addObra(autor);


            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            
            LOG.error("Ocurrió un error: "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
    }

    public void asociarLibroAutorCascade(Libro libro, Autor autor){
        EntityManager em = null;
        LibroDAO ldao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            // adao = new AutorDAO(em);
            ldao = new LibroDAO(em);

            et.begin();

            
            ldao.saveLibro(libro);
            libro.addObra(autor);
            
            et.commit();
            LOG.info("Finalizada la asociación de libro con autor en cascada [{},{}]",libro.getId(),autor.getId());
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error durante la asociacion de un libro con su actor en cascada: "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
    }

    public Set<Autor> getAutores(Long libroId){
        Set<Autor> autores = null;
        EntityManager em = null;
        LibroDAO ldao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            ldao = new LibroDAO(em);

            et.begin();

            Libro libro = ldao.getLibroById(libroId);

            if(libro != null){
                autores = new HashSet<>(libro.getAutores());
            }
            else{
                throw new RuntimeException("Alumno no encontrado!!");
            }
            
            et.commit();
            LOG.info("Se acabó con la busqueda de actores del libro con id {}",libroId);
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error durante la asociacion de un libro con su actor en cascada: "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
        return autores;
    }
}
