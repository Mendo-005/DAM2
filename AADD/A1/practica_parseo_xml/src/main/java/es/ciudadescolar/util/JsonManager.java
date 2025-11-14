package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Medico;
import es.ciudadescolar.clases.Paciente;


public class JsonManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    // Clase ObjectMapper tiene el parseador de json
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    /*
     * Parseo simple de Json
     */
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
    

    /*
     * Parseo Completo Json
     */
    public static List<Paciente> medicoCompleto(File ficheroJson)
    {
        List<Paciente> pacienteCompleto = new ArrayList<>();

        if (!ficheroJson.exists()) 
            {
                LOG.warn("Fichero no encontrado: " + ficheroJson.getAbsolutePath());
                return pacienteCompleto;
            }
        if (!ficheroJson.canRead()) 
            {
                LOG.warn("Fichero no se puede leer: " + ficheroJson.getAbsolutePath());
                return pacienteCompleto;
            }
        
        try {

            JsonNode nodoRaiz = mapper.readTree(ficheroJson);

            // Navegar a hospital -> departamentos
            JsonNode hospital = nodoRaiz.get("hospital");
            if (hospital == null) {
                LOG.warn("No se encontró el nodo 'hospital' en el JSON");
                return pacienteCompleto;
            }

            JsonNode departamentos = hospital.get("departamentos");
            if (departamentos == null) {
                LOG.warn("No se encontró el nodo 'departamentos' en el JSON");
                return pacienteCompleto;
            }

            // Iterar sobre cada departamento
            for (JsonNode departamento : departamentos) 
            {
                JsonNode pacientesJsonNode = departamento.get("pacientes");
                
                if (pacientesJsonNode != null && pacientesJsonNode.isArray()) 
                {
                    // Iterar sobre cada paciente del departamento
                    for (JsonNode pa : pacientesJsonNode) 
                    {
                        Paciente paciente = new Paciente();
                        paciente.setId(pa.get("id").asText());
                        paciente.setNombre(pa.get("nombre").asText());
                        paciente.setApellido(pa.get("apellido").asText());
                        paciente.setEdad(String.valueOf(pa.get("edad").asInt()));
                        paciente.setTelefono(pa.get("telefono").asText());
                        paciente.setDiagnostico(pa.get("diagnostico").asText());
                        
                        pacienteCompleto.add(paciente);
                    }
                }
            }

            LOG.info("Se han parseado " + pacienteCompleto.size() + " pacientes del fichero: " + ficheroJson.getName());

        } catch (IOException e) {
            LOG.warn("Error en el parseo del json: " + ficheroJson.getAbsolutePath());
            e.printStackTrace();
        } catch (Exception e) {
            LOG.warn("Error inesperado en el parseo del json: " + ficheroJson.getAbsolutePath());
            e.printStackTrace();
        }

        return pacienteCompleto;
        
    }

    public static void crearJsonPacientes(List<Paciente> pacienteCompleto, File ficheroSalida)
    {
        try {

            // Generamos el nuevo json de manera rapida con el mapper
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalida, pacienteCompleto);

        } catch (StreamWriteException e) {
            LOG.warn("Error en la generación del json: " + ficheroSalida.getAbsolutePath());
            e.printStackTrace();
        } catch (DatabindException e) {
            LOG.warn("Error en la generación del json: " + ficheroSalida.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            LOG.warn("Error en la generación del json: " + ficheroSalida.getAbsolutePath());
        }
    }
}