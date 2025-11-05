package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.hospital.InformeSalida;

public class JsonManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void escribirInforme(InformeSalida informe, File archivoSalida) {
        try 
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoSalida, informe);
            LOG.info("Informe guardado en: {}", archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            LOG.error("Error al escribir el informe: {}", archivoSalida.getAbsolutePath(), e);
        }
    }

    public static InformeSalida leerInforme(File archivoJson) {
        try {
            InformeSalida informe = mapper.readValue(archivoJson, InformeSalida.class);
            LOG.info("Informe leído desde: {}", archivoJson.getAbsolutePath());
            return informe;
        } catch (IOException e) {
            LOG.error("Error al leer el informe: {}", archivoJson.getAbsolutePath(), e);
            return null;
        }
    }
}
