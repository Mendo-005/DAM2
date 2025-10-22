package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ciudadescolar.instituto.Alumno;

public class JsonManager {

    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Alumno> parsearJsonAlumnos(File ficheroJson){
        List<Alumno> listaAlumnos = new ArrayList<Alumno>();

        if (!ficheroJson.exists()) 
        {
            LOG.warn("El fichero Json no existe:" + ficheroJson.getAbsolutePath());
            
        }
        if (!ficheroJson.canRead()) 
        {
            LOG.warn("El fichero no se puede leer: " + ficheroJson.getAbsolutePath());
        }

        JsonNode nodoRaiz = null;

        try {
            nodoRaiz = mapper.readTree(ficheroJson);

            if (nodoRaiz.isObject())
            {
                LOG.info("centro: " + nodoRaiz.get("centro").asText());               
            }

        } catch (IOException e) 
        {
            LOG.error(e.getMessage());
        }
        
        
        
        return listaAlumnos;

    }
}