package mendo.dev;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mendo.dev.util.DbManager;

public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        // manager (Constructor)
        DbManager manager = new DbManager();

        // a)
        String titulo = "Watchmen";
        List<String> listaAutores = manager.getAutorComic(titulo);
        for (String autor : listaAutores) 
        {
            LOG.info(autor);
        }

        // b)
        Integer id_comic = 1;
        Double precio = manager.getPrecioComic(id_comic);
        LOG.info("Precio del comic: " + precio);

        // c)
        String nombre_creador = "Stan Lee";
        String titulo_comic = "Watchmen";
        String rol_creador = "Escritor";
        manager.addAsignarCreadorComic(nombre_creador, titulo_comic, rol_creador);

        // d)
        String nombre_c  = "Todd McFarlane";
        String rol_c = "Escritor";
        String titulo_co = "Spawn";
        Integer numero_comic  = 1;
        Double precio_comic  = 3.50;
        Integer cod_editorial  = 3;
        Date fecha_publicacion   = Date.valueOf(LocalDate.of(1993, 11, 15));
        manager.addCreadoresYComic(nombre_c, rol_c, titulo_co, numero_comic, 
                                   precio_comic, cod_editorial, fecha_publicacion);
    }
}