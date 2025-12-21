package mendo.dev.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.github.cdimascio.dotenv.Dotenv;
import mendo.dev.clases.Comic;
import mendo.dev.repository.rowmapper.ComicRowMapper;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) 
    {
        LOG.info("Abriendo Spring main");

        Dotenv dotenv = Dotenv.load();

        /*
            -Creamos la conexión con la base de datos mediante Spring
            -No mantiene la conexión, es un Builder
            -No hay pool de conexiones, no hay recursos que cerrar. 
            -Por eso no implementa Closeable, ni tiene método close().
        */
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dotenv.get("URL"));
        dataSource.setUsername(dotenv.get("USER"));
        dataSource.setPassword(dotenv.get("PASSWORD"));
        dataSource.setDriverClassName(dotenv.get("DRIVER"));

        /**
            1. JdbcTemplate pide una conexión al DataSource
            2. Usa esa conexión para ejecutar la consulta/actualización.
            3. Cierran la conexión automáticamente, internamente, después de cada operación.
            
            Es decir, con JdbcTemplate, cada operación abre y cierra una conexión nueva.
            
            JdbcTemplate.query()	abre conexión → ejecuta → la cierra
            JdbcTemplate.update()	abre conexión → ejecuta → la cierra
        */
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        /*
            Consulta simple a la base de datos
            @return Comic
        */
        String sql = "SELECT * FROM comic";
        List<Comic> comicReturn = jdbc.query(sql, new ComicRowMapper());
        for (Comic comic : comicReturn) 
        {
            LOG.info(comic.toString());    
        }

        /*
            Consulta con parametro a la base de datos
            @param titulo   
            @return Comic
        */
        String sql1 = "SELECT * FROM comic WHERE titulo = ?";
        List<Comic> comicPotTitulo = jdbc.query(sql1, new ComicRowMapper(), "Amazing Fantasy");
        for (Comic comic : comicPotTitulo) 
        {
            LOG.info("Comic por titulo: " + comic);    
        }
        
            
    }
}