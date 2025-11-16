package es.ciudadescolar.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Hacker;

public class JsonManager {

    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static ObjectMapper mapper = new ObjectMapper().registerModule( new JavaTimeModule());
     
    public static Map<String, Object> parseoJson(File ficheroJson) throws Exception
    {
        List<Hacker> listaHackers = new ArrayList<>();
        Map<String, String> idiomas = new HashMap<>();
        Set<String> idsVistos = new HashSet<>();

        JsonNode nodoRaiz = mapper.readTree(ficheroJson);

        for (JsonNode hacker : nodoRaiz) {

            String id =  hacker.get("id").asText();
            
            // Saltar si ya hemos visto este ID (eliminar duplicados)
            if (idsVistos.contains(id)) {
                LOG.info("Hacker duplicado ignorado: " + id);
                continue;
            }
            
            idsVistos.add(id);
            
            String nombreApellido = hacker.get("nombre y apellido").asText();
            String idioma = hacker.get("idioma").asText();
            double nota = hacker.get("nota").asDouble();

            // Separar nombre y apellido por espacio
            String[] palabras = nombreApellido.trim().split("\\s+");
            String nombre = (palabras.length > 0) ? palabras[0] : "";
            String apellido = (palabras.length > 1) ?palabras[1] : "";

            listaHackers.add(new Hacker(id, nombre, apellido, nota));
            idiomas.put(id, idioma);

        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("hackers", listaHackers);
        resultado.put("idiomas", idiomas);
        
        return resultado;
    }
}
