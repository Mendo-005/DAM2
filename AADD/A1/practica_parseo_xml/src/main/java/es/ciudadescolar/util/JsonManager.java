package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Medico;


public class JsonManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    // Clase ObjectMapper tiene el parseador de json
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    // Parseo simple de Json
    public static List<Medico> medicoSimple(File ficheroJson) 
    {
        List<Medico> medicoSimpleList = new ArrayList<>();

        if (!ficheroJson.exists()) 
            {
                LOG.warn("Fichero no encontrado: " + ficheroJson.getAbsolutePath());
                return medicoSimpleList;
            }
        if (!ficheroJson.canRead()) 
            {
                LOG.warn("Fichero no se puede leer: " + ficheroJson.getAbsolutePath());
                return medicoSimpleList;
            }

        try 
        {
            // Usamos el readTree para cargar todo el fichero en memoria y acceder a el (nodoRaiz)
            JsonNode nodoRaiz = mapper.readTree(ficheroJson);
            // El fichero simple_medico.json es un objeto simple, no un array
            Medico medico = new Medico();
            medico.setId(nodoRaiz.get("id").asText());
            //medico.setTurno(nodoRaiz.get("turno").asText());
            medico.setNombre(nodoRaiz.get("nombre").asText());
            medico.setApellido(nodoRaiz.get("apellido").asText());
            medico.setEspecialidad(nodoRaiz.get("especialidad").asText());
            //medico.setTelefono(nodoRaiz.get("telefono").asText());
            //medico.setEmail(nodoRaiz.get("email").asText());

            medicoSimpleList.add(medico);
            LOG.info("Se ha parseado el fichero JSON simple: " + ficheroJson.getName());

        } catch (IOException e) 
        {
            LOG.warn("Error en el parseo del json: " + ficheroJson.getAbsolutePath());
        }
        return medicoSimpleList;
    }
    
}