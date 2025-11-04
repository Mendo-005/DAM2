package es.ciudadescolar.utils;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Informe;

public class JsonManager {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);

    // Mapper
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    // Fechas
    private static String patronFecha = "yyyy-MM-dd";
    private static DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(patronFecha);

    // Procear Json
    public static List<Informe> parseJsonInforme(File ficheroJson)
    {
        List<Informe> listaInformes = new ArrayList<>();
        
        if (!ficheroJson.canRead()) 
        {
            LOG.warn("El fichero no se puede leer: " + ficheroJson.getAbsolutePath());    
        }

        try {
            JsonNode nodoRaiz = mapper.readTree(ficheroJson);

            if (nodoRaiz.isObject()) 
            {
                JsonNode nodoPedido = nodoRaiz.get("pedidos");

                if (nodoPedido != null && nodoPedido.isArray()) 
                {
                    Map<String, Informe> mapaInforme = new HashMap();
                    
                    // Procesar cada Pedido
                    for (JsonNode pedido : nodoPedido)
                    {
                        procesarPedidos(pedido, mapaInforme);
                    }

                    // Convertir mapa a lista
                    listaInformes.addAll(mapaInforme.values());
                }
            }
        } catch (IOException e) {
            LOG.warn("Error en el parseo: " + ficheroJson.getAbsolutePath(), e);
        }

        return listaInformes;
    }

    private static void procesarPedidos(JsonNode pedido, Map<String, Informe> mapaInforme) 
    {
        try 
        {
            String id_pedido = pedido.get("id").asText();       
        } catch (Exception e) 
        {
            LOG.error("Error al procesar resultado: " + e.getMessage());
        }    
    }
    
    
}
