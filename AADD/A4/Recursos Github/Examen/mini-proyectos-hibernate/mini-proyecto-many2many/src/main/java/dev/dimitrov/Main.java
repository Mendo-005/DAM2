package dev.dimitrov;

import java.time.LocalDate;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dimitrov.dominio.modelo.Autor;
import dev.dimitrov.dominio.modelo.Libro;
import dev.dimitrov.dominio.modelo.Autor.Sex;
import dev.dimitrov.servicios.AutorServicio;
import dev.dimitrov.servicios.LibroServicio;
import dev.dimitrov.util.JPAUtil;

public class Main {
    public static final Logger LOG = LoggerFactory.getLogger(LibroServicio.class);
    public static void main(String[] args) {
        try {
            LibroServicio ls = new LibroServicio();
            AutorServicio as = new AutorServicio();
            // ls.asociarLibroAutor(2L, 1L);

            //AutorServicio ass = new AutorServicio();
            // Set<Libro> ll = as.getLibrosByAutor(1l);
            // ystem.out.println(ll.toString());
            Libro l1 = new Libro("¿Sueñan los androides con avejas eléctricas?", LocalDate.now());
            Autor a1 = new Autor("Miguel de Cervantes", Sex.M);
            ls.asociarLibroAutorCascade(l1, a1);

/*             Set<Autor> autores = ls.getAutores(4l);
            LOG.info("AUTORES: {}",autores.toString());  */
        
            // System.out.println(ass.getInforme());
            LOG.info("Listo!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JPAUtil.close();
        }
    }
}