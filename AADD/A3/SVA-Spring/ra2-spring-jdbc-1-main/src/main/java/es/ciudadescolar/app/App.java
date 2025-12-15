package es.ciudadescolar.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

import es.ciudadescolar.model.Actor;
import es.ciudadescolar.repository.rowmapper.ActorRowMapper;
import es.ciudadescolar.transaction.BorrarPeliculasCallBack;
import io.github.cdimascio.dotenv.Dotenv;

public class App   
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        LOG.trace("lanzando aplicacion de Spring ");

        Dotenv dotenv = Dotenv.load();
        
        /**
         * DriverManagerDataSource no mantiene conexiones abiertas.
         * Solo sirve como fábrica de conexiones.
         * No hay pool de conexiones, no hay recursos que cerrar. 
         * Por eso no implementa Closeable, ni tiene método close().
         */

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dotenv.get("DB_URL"));
        dataSource.setUsername(dotenv.get("DB_USER"));
        dataSource.setPassword(dotenv.get("DB_PASS"));
        dataSource.setDriverClassName(dotenv.get("DB_DRIVER"));


        /**
         * 1. JdbcTemplate pide una conexión al DataSource
         * 2. Usa esa conexión para ejecutar la consulta/actualización.
         * 3. Cierran la conexión automáticamente, internamente, después de cada operación.
         * 
         * Es decir, con JdbcTemplate, cada operación abre y cierra una conexión nueva.
         * 
         * JdbcTemplate.query()	abre conexión → ejecuta → la cierra
         * JdbcTemplate.update()	abre conexión → ejecuta → la cierra
        */
         
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        /*
         * Cuando quieres ejecutar una consulta SQL y convertir automáticamente cada fila en un objeto Java,
         *  por ejemplo:
         *      Mapear filas de la tabla alumno a objetos Alumno.
         *      Convertir resultados de una vista o join a DTOs.
         */
        List<Actor> actores = jdbc.query("SELECT actor_id, first_name, last_name FROM actor WHERE actor_id < 20",new ActorRowMapper());
        for (Actor a:actores)
        {
            LOG.info("query - Actor" + a.toString());
        }

       
        // DriverManagerDataSource no necesita cierre porque no mantiene un pool de conexiones. Cada instrucción en JdbcTemplate crea una nueva conexión y la cierra automáticamente

        // IMPORTANTE:  queryForList ⇒ Devuelve filas como mapas de columnas. (no soporta rowmapper)
        //              query(...) ⇒ Usa RowMapper para construir objetos.

        // Consulta parametrizada simple
        String sql = "SELECT film_id, title FROM film limit 5";
        List<Map<String, Object>> filas = jdbc.queryForList(sql);
        for (Map<String, Object> fila : filas) {
           LOG.info("ID: " + fila.get("film_id") + ", Nombre: " + fila.get("title"));
        }

        String sql2 = "SELECT title FROM film limit 5";
        //IMPORTANTE: SOLO FUNCIONA EL QUERYFORLIST SI SOLO SE DEVUELVE UNA COLUMNA Y ESA COLUMNO SE CONVIERTE DIRECTAMENTE AL TIPO ESPECIFICADO.
        //como es String el título puedo hacerlo.
        List<String> titulos = jdbc.queryForList(sql2, String.class);
        for (String tit:titulos)
        {
            LOG.info("titulo: "+tit);
        }
        
        String sql3 = "SELECT title FROM film where film_id = ?";
        List<String> titulosPorFilm_id = jdbc.queryForList(sql3, String.class, new Object[]{4});
        for (String tit:titulosPorFilm_id)
        {
            LOG.info("titulo por film_id 4: "+tit);
        }

        /*jdbc.update devuelve el número de filas afectadas.
        Funciona para INSERT, UPDATE y DELETE.
        seguro, porque los parámetros se pasan al PreparedStatement.*/

        String sql4 = "UPDATE actor SET first_name = ? WHERE actor_id = ?";
        int filasActualizadas = jdbc.update(sql4, "PAQUITO", 10);
        System.out.println("Filas actualizadas: " + filasActualizadas);

        
        /** 
         * En las transacciones, mientras se ejecute dentro del mismo TransactionTemplate, 
         * la conexión será la misma
         * El DataSourceTransactionManager desactiva la autocommit de esa conexión (setAutoCommit(false)).
        */
        DataSourceTransactionManager tm = new DataSourceTransactionManager(dataSource);
        TransactionTemplate txTemplate = new TransactionTemplate(tm);
        
        txTemplate.execute(new BorrarPeliculasCallBack(jdbc));
        
              
        // -----------------------------
        // Llamada a procedimiento almacenado
        // CREATE DEFINER=`root`@`%` PROCEDURE `film_in_stock`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
        // -----------------------------
        SimpleJdbcCall call = new SimpleJdbcCall(jdbc);
        call.withProcedureName("film_in_stock");
        Map<String, Object> parametros = new HashMap<>();
        //Spring usa los nombres REALES de los parámetros del SP
        parametros.put("p_film_id", 1); // IN
        parametros.put("p_store_id", 2); // IN
        // Spring solo espera en el mapa los parámetros de entrada (IN) o entrada y salida (INOUT)
        // el mapa de salida usa como clave EXACTAMENTE el nombre del parámetro OUT definido en el stored procedure de MySQL.
        Map<String, Object> resultado = call.execute(parametros);
        Integer stockPelicula = (Integer) resultado.get("p_film_count");
        System.out.println("Resultado del procedimiento: Stock Film["+parametros.get("p_film_id")+"] Store["+parametros.get("p_store_id")+"]=" + stockPelicula.toString());

        
        /** 
         * DriverManagerDataSource no mantiene conexiones abiertas.
         * Solo sirve como fábrica de conexiones. 
         * 
         * Por tanto no se cierra: dataSource.close();
         * 
         */

        LOG.trace("finalizando aplicacion de Spring ");
    }

}
