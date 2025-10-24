package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
        JsonNode nodoAlumnos = null; 
        try {
            nodoRaiz = mapper.readTree(ficheroJson);

            if (nodoRaiz.isObject())
            {
                LOG.info("centro: " + nodoRaiz.get("centro").asText());               
                LOG.info("codigo" + nodoRaiz.get("codigo").asInt());
                LOG.info("curso" + nodoRaiz.get("curso").asText());
                
                nodoAlumnos = nodoRaiz.get("alumnos");
                if (nodoAlumnos.isArray())
                {
                    Alumno alumno = null;
                    for(JsonNode al: nodoAlumnos)
                    {
                        alumno = new Alumno();
                        alumno.setExpediente(al.get("expediente").asText());
                        alumno.setEdad(Integer.valueOf(al.get("edad").asInt()));
                        alumno.setNombre(al.get("nombre").asText());
                    
                        listaAlumnos.add(alumno);
                    }
                }
            }

        } catch (IOException e) 
        {
            LOG.error(e.getMessage());
        }
        
        return listaAlumnos;

    }

    public static void createJsonAlumnos(List<Alumno> alumnos, File ficheroSalidaJson)
    {/*
        // Opcion 1: Creacion dinamica del fichero
        ArrayNode arrayAlumnos = mapper.createArrayNode();
        
        ObjectNode nodoAlumno = null;
        
        if((alumnos != null) && (!alumnos.isEmpty()))
        {
            for(Alumno al : alumnos)
            {
                nodoAlumno = mapper.createObjectNode();

                nodoAlumno.put("nombre",al.getNombre());
                nodoAlumno.put("expediente",al.getExpediente());
                nodoAlumno.put("edad",al.getEdad().intValue()); // int porque es Integer

                arrayAlumnos.add(nodoAlumno);
            }

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, arrayAlumnos);
            } catch (StreamWriteException e) {
                LOG.error("Error durante la escritura "+ e.getMessage());
            } catch (DatabindException e) {
                LOG.error("Error durante la escritura "+ e.getMessage());
            } catch (IOException e) {
                LOG.error("Error durante la escritura "+ e.getMessage());
            }
        }*/


        // Opcion 2: Serializacioón Jackson
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, alumnos);
        } catch (StreamWriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}