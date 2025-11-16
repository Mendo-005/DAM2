package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Game;

public class JsonManager {

    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static Object parsearJson(File ficheroJson)
    {
        List<Game> listaJuegosJson = new ArrayList<>();

        try {

            JsonNode root = mapper.readTree(ficheroJson);

            JsonNode juegos = root.get("juegos");
            for (JsonNode juegNode : juegos) {
                
                Game juego = new Game(null, null, null);

                juego.setNombreJuego(juegNode.get("nombre").toString());
                juego.setDesarrollador(juegNode.get("desarrollador").toString());
                juego.setAño(juegNode.get("anyoo").toString());
                
                listaJuegosJson.add(juego);
            }

        } catch (IOException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroJson.getName() + " | " + e);
        }

        return listaJuegosJson;
    }

    public static void generarJson(File ficheroSalidaJson, List<Game> listaJuegos, Map<String,String> valoracion)
    {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode juegos = mapper.createArrayNode();


        for (Game juego : listaJuegos) {
            ObjectNode juegNode = mapper.createObjectNode();

            juegNode.put("nombre", juego.getNombreJuego());
            juegNode.put("año", juego.getAño());
            juegNode.put("desarrollador", juego.getDesarrollador());

            String nombreJuego = juego.getNombreJuego();
            String valoracionJuego = valoracion.get(nombreJuego);
            juegNode.put("valoracion", valoracionJuego);

            juegos.add(juegNode);
        }
        root.set("juegos", juegos);
        
        try {

            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, root);

        } catch (IOException e) {
            LOG.error("Error en la creacion del fichero: " + ficheroSalidaJson.getName() + " | " + e);
        }
    }
    
}
