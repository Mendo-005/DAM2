package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Equipo;
import es.ciudadescolar.clases.Resultado;

public class JsonManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static String patronFecha = "dd/MM/yyyy";
    private static DateTimeFormatter formato = DateTimeFormatter.ofPattern(patronFecha);


    public static Equipo parseoJson(File ficheroJson, String team)
    {
        List<Resultado> listaVictorias = new ArrayList<>();
        List<Resultado> listaEmpates = new ArrayList<>();
        List<Resultado> listaDerrotas = new ArrayList<>();
        
        Equipo equipo = new Equipo(null, team, 0, 0, 0, 0, listaDerrotas, listaEmpates, listaVictorias);
        
        try 
        {
            JsonNode nodoRaiz = mapper.readTree(ficheroJson);
            JsonNode arrayResultados = nodoRaiz.get("resultados");
            
            int golesFavor = 0;
            int golesContra = 0;
            int tarjetasAmarillasTotal = 0;
            int tarjetasRojasTotal = 0;
            
            if (arrayResultados != null && arrayResultados.isArray())
            {
                for (JsonNode result : arrayResultados) 
                {
                    String division = result.get("Div").asText();
                    
                    // Goles
                    int goles_local = result.get("FTHG").intValue();
                    int goles_visitante = result.get("FTAG").intValue();

                    // Tarjetas amarillas
                    int tarjetas_amarillas_local = result.get("HY").intValue();
                    int tarjetas_amarillas_visitante = result.get("AY").intValue();
                    
                    // Tarjetas rojas
                    int tarjetas_rojas_local = result.get("HR").intValue();
                    int tarjetas_rojas_visitante = result.get("AR").intValue();

                    // Equipos Por Partido
                    String local = result.get("HomeTeam").asText();
                    String visitante = result.get("AwayTeam").asText();
                    String fecha = result.get("Date").asText();

                    equipo.setDivision(division);
                    
                    // Determinar si el equipo juega como local o visitante
                    Boolean esLocal = local.equalsIgnoreCase(team);
                    Boolean esVisitante = visitante.equalsIgnoreCase(team);

                    if (esLocal || esVisitante)
                    {
                        String adversario;
                        int miGol, golesAdversario;
                        int misTarjetasAmarillas, misTarjetasRojas;
                        Boolean esPartidoLocal;
                        
                        if (esLocal)
                        {
                            adversario = visitante;
                            miGol = goles_local;
                            golesAdversario = goles_visitante;
                            misTarjetasAmarillas = tarjetas_amarillas_local;
                            misTarjetasRojas = tarjetas_rojas_local;
                            esPartidoLocal = true;
                            
                            golesFavor += goles_local;
                            golesContra += goles_visitante;
                        }
                        else
                        {
                            adversario = local;
                            miGol = goles_visitante;
                            golesAdversario = goles_local;
                            misTarjetasAmarillas = tarjetas_amarillas_visitante;
                            misTarjetasRojas = tarjetas_rojas_visitante;
                            esPartidoLocal = false;
                            
                            golesFavor += goles_visitante;
                            golesContra += goles_local;
                        }
                        
                        tarjetasAmarillasTotal += misTarjetasAmarillas;
                        tarjetasRojasTotal += misTarjetasRojas;
                        
                        // Crear resultado del partido
                        String resultadoTexto = miGol + "-" + golesAdversario;
                        LocalDate fechaPartido = LocalDate.parse(fecha, formato);
                        Resultado resultado = new Resultado(adversario, resultadoTexto, fechaPartido, esPartidoLocal);
                        
                        // Clasificar resultado
                        if (miGol > golesAdversario)
                        {
                            listaVictorias.add(resultado);
                        }
                        else if (miGol == golesAdversario)
                        {
                            listaEmpates.add(resultado);
                        }
                        else
                        {
                            listaDerrotas.add(resultado);
                        }
                    }
                }
                
                // Establecer estadísticas finales
                equipo.setGolesFavor(golesFavor);
                equipo.setGolesContra(golesContra);
                equipo.setTarjetasAmarillas(tarjetasAmarillasTotal);
                equipo.setTarjetasRojas(tarjetasRojasTotal);
            }
        } 
        catch (IOException e) 
        {
            LOG.error("El parseo no se pudo ejecutar correctamente: ", e);
        }

        return equipo;
    }

    public static void generarJson(Equipo equipo, File ficheroSalida)
    {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode listaVictorias = mapper.createArrayNode();
        ArrayNode listaEmpates = mapper.createArrayNode();
        ArrayNode listaDerrotas = mapper.createArrayNode();
        
        try 
        {
            root.put("División", equipo.getDivision());
            root.put("Equipo", equipo.getEquipo());
            root.put("Goles a favor", equipo.getGolesFavor());
            root.put("Goles en contra", equipo.getGolesContra());
            root.put("Tarjetas Amarillas", equipo.getTarjetasAmarillas());
            root.put("Tarjetas Rojas", equipo.getTarjetasRojas());

            for (Resultado resultado : equipo.getVictorias()) {
                ObjectNode partido = mapper.createObjectNode();
                partido.put("Adversario", resultado.getAdversario());
                partido.put("Resultado", resultado.getResultado());
                partido.put("Fecha", resultado.getFecha_partido().format(formato));
                partido.put("Local", resultado.getLocal() ? "Si" : "No");
                listaVictorias.add(partido);
            }
            
            for (Resultado resultado : equipo.getEmpates()) {
                ObjectNode partido = mapper.createObjectNode();
                partido.put("Adversario", resultado.getAdversario());
                partido.put("Resultado", resultado.getResultado());
                partido.put("Fecha", resultado.getFecha_partido().format(formato));
                partido.put("Local", resultado.getLocal() ? "Si" : "No");
                listaEmpates.add(partido);
            }
            
            for (Resultado resultado : equipo.getDerrotas()) {
                ObjectNode partido = mapper.createObjectNode();
                partido.put("Adversario", resultado.getAdversario());
                partido.put("Resultado", resultado.getResultado());
                partido.put("Fecha", resultado.getFecha_partido().format(formato));
                partido.put("Local", resultado.getLocal() ? "Si" : "No");
                listaDerrotas.add(partido);
            }
            
            root.set("Victorias", listaVictorias);
            root.set("Empates", listaEmpates);
            root.set("Derrotas", listaDerrotas);

            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalida, root);
        } catch (IOException e) 
        {
            LOG.error("El Json no se pudo generar");
        }
        
    }


}