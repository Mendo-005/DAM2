package dev.mendo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Autor;
import dev.mendo.dominio.modelo.Autor.Sex;
import dev.mendo.dominio.modelo.Libro;
import dev.mendo.servicio.AutorServicio;
import dev.mendo.servicio.LibroService;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(AutorServicio.class);

    public static void main(String[] args) 
    {

        // a)
        Autor autor = new Autor("Jose1", Sex.H);
        AutorServicio autorServicio = new AutorServicio();
        autorServicio.crearAutor(autor);

        // b)
        Libro libro = new Libro("Jose Sala", LocalDate.of(2000, 8, 22));
        LibroService libroService = new LibroService();
        libroService.crearLibro(libro);

        // c)
        autorServicio.asociarAutorxLibro(autor, libro);

        // d)
        Autor autor_A = new Autor("Mario", Sex.H);
        Libro libro_A = new Libro("MendoXXX", LocalDate.of(2010, 12, 22));
        autorServicio.crearAutorYLibroCascade(autor_A, libro_A);

        // e)
        List<Libro> libros = autorServicio.reporteTodosLibrosDeAutor(autor_A);
        for (Libro entrada : libros) {
            LOG.info(entrada.toString());
        }

        // f)
        List<Autor> autores = libroService.reporteTodosAutoresDeLibro(libro_A);
        for (Autor entrada : autores) {
            LOG.info(entrada.toString());
        }

        // g)
        List<String> reporte = autorServicio.reporteGenereal();
        for (String entrada : reporte) {
            LOG.info(entrada);
        }
    }
}