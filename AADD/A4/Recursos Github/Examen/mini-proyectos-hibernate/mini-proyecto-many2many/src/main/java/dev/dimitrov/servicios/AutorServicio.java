package dev.dimitrov.servicios;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dimitrov.dominio.modelo.Autor;
import dev.dimitrov.dominio.modelo.Libro;
import dev.dimitrov.persistencia.dao.AutorDAO;
import dev.dimitrov.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AutorServicio {
    public static final Logger LOG = LoggerFactory.getLogger(AutorServicio.class);

    public Long addAutor(Autor l) {
        Long id = -1l;
        EntityManager em = null;
        AutorDAO ldao = null;
        EntityTransaction et = null;

        try {
            em = JPAUtil.getEntityManager();
            ldao = new AutorDAO(em);
            et = em.getTransaction();
            et.begin();
            ldao.saveAutor(l);
            et.commit();
            id = l.getId();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("An error ocurred while adding a book: " + e.getMessage());
        } finally {
            JPAUtil.secureCloseEm(em);
        }

        return id;
    }

    public Set<Libro> getLibrosByAutor(Long autorId) {
        Set<Libro> libros = null;
        EntityManager em = null;
        AutorDAO adao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            adao = new AutorDAO(em);
            et.begin();
            Autor autor = adao.getAutorById(autorId);

            if (autor != null) {
                libros = autor.getLibros();
            }
            else{
                LOG.warn("No existe el autor");
            }

            et.commit();
        } catch (Exception e) {
            et.rollback();
            LOG.error("Ocurrió un error mientras se sacaban libros: " + e.getMessage());
        }

        return libros;
    }

    public String getInforme(){
        String report= "nombre | libros"+System.getProperty("line.separator");
        EntityManager em = null;
        AutorDAO adao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            et = em.getTransaction();
            adao = new AutorDAO(em);
            et.begin();
            Set<Autor> autores = adao.getAll();

            for(Autor a: autores){
                report+= a.getNombre()+" | ";
                for(Libro l: a.getLibros()){
                    report += l.getTitulo()+" ";
                }
                report += System.getProperty("line.separator");
            }

            et.commit();
        } catch (Exception e) {
            et.rollback();
            LOG.error("Ocurrió un error mientras se sacaban libros: " + e.getMessage());
        }
        return report;
    }
}
