package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Cine;

public class JsonManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void generarJson(List<Cine> listaPeliculas, File ficheroSalidaJson)
    {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode peliculas = mapper.createArrayNode();

        //ObjectNode totalPeliculas = mapper.createObjectNode();
        int numPeliculas = 0;

        // totalPeliculas.put("", numPeliculas);
        for (Cine peliculaElm : listaPeliculas) {

            ObjectNode pelicula = mapper.createObjectNode();
            pelicula.put("Pais", peliculaElm.getPais());
            pelicula.put("Titulo", peliculaElm.getTitulo());

            peliculas.add(pelicula);
            numPeliculas++;

            root.put("Cine", peliculaElm.getCine());
        }
        root.put("Numero de Peliculas", numPeliculas);
        root.set("Peliculas", peliculas);

        try {

            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, root);

        } catch (IOException e) {
            LOG.error("Error en la generacion del fichero: " + ficheroSalidaJson.getName() + " | " + e);
        }
    }
}
