package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.futbol.Equipo;
import es.ciudadescolar.futbol.Partido;

public class JsonManager {

    public static ObjectMapper mapper;
    public static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);

    public static Equipo getTeamStats(String team, File json) {
        Set<String> equipos = new HashSet<>();
        mapper = new ObjectMapper();
        JsonNode root = null;
        JsonNode resultados = null;
        Partido p = new Partido();
        Equipo e = new Equipo(team, null, 0, 0, 0, 0);
        String local = null;
        String visitante = null;
        Integer golesVisitante = null;
        Integer golesLocal = null;
        boolean esLocal = false;
        boolean esVisitante = false;
        Integer tarjetasRojasLocal = null;
        Integer tarjetasRojasVisitante = null;
        Integer tarjetasAmarillasLocal = null;
        Integer tarjetasAmarillasVisitante = null;
        boolean equipoExiste = false;
        try {
            root = mapper.readTree(json);
            if (root.isObject()) {
                resultados = root.get("resultados");
                if (resultados.isArray()) {
                    for (JsonNode partido : resultados) {
                        
                        local = partido.get(XmlManager.get("equipo_local")).asText();
                        visitante = partido.get(XmlManager.get("equipo_visitante")).asText();
                        
                        equipos.add(local);
                        equipos.add(visitante);

                        if(equipos.size() == 20 && !equipoExiste){
                            return null;
                        }



                        esLocal = local.equalsIgnoreCase(team);
                        esVisitante = visitante.equalsIgnoreCase(team);
                        tarjetasRojasLocal = partido.get(XmlManager.get("tarjetas_rojas_local")).asInt();
                        tarjetasRojasVisitante = partido.get(XmlManager.get("tarjetas_rojas_visitante")).asInt();
                        tarjetasAmarillasLocal = partido.get(XmlManager.get("tarjetas_amarillas_local")).asInt();
                        tarjetasAmarillasVisitante = partido.get(XmlManager.get("tarjetas_amarillas_visitante"))
                                .asInt();

                        // Si el target es visitante o local, es que obviamente es un partido del equipo
                        // target
                        if (esLocal || esVisitante) {
                            golesVisitante = partido.get(XmlManager.get("goles_visitante")).asInt();
                            golesLocal = partido.get(XmlManager.get("goles_local")).asInt();

                            // Si el equipo target es el local, pongo como adversario al visitante y
                            // viceversa.
                            p.setAdversario(esLocal ? visitante : local);
                            p.setLocal(esLocal);
                            // El marcador siempre va así, primero los goles del local.
                            p.setResultado(golesLocal + "-" + golesVisitante);

                            // Parseo la fecha con el formatter estático que está público en la clase
                            // Partido
                            LocalDate fechaParseada = LocalDate.parse(partido.get(XmlManager.get("fecha")).asText(),
                                    Partido.formatter);
                            p.setFecha(fechaParseada); // La añado al partido

                            // Misma lógica en todo, si yo soy el local, me añado los datos del local, si
                            // no, los del visitante.
                            e.addGolesFavor(esLocal ? golesLocal : golesVisitante);
                            e.addGolesContra(esLocal ? golesVisitante : golesLocal);
                            e.addTarjetaAmarillas(esLocal ? tarjetasAmarillasLocal : tarjetasAmarillasVisitante);
                            e.addTarjetaRojas(esLocal ? tarjetasRojasLocal : tarjetasRojasVisitante);

                            // Añado el partido al equipo, ese método ya se encargará de categorizarlo como
                            // derrota, empate o victoria.
                            e.addPartido(p);

                            // Solamente si la divison esta vacia, agrega la división al objeto (Entrará
                            // solamente una vez a este if)
                            if (e.getDivision() == null) {
                                e.setDivision(partido.get(XmlManager.get("division")).asText());
                                equipoExiste = true;
                            }
                        }
                        p = new Partido();
                    }
                }
            }
        } catch (IOException ex) {
            LOG.error("Ha ocurrido un error mientras se parseaba el fichero JSON: " + ex.getMessage());
            return null;
        }
        LOG.info("Terminó la lectura del JSON de la temporada.");
        return equipoExiste ? e : null;
    }

    public static void makeJsonFile(File out, Equipo equipo) {
        // Creo nuevas instancias a partir del mapper de ObjectNodes y de un ArrayNode
        // que usaré para guardar cada tipo de partidos
        mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ObjectNode partido = mapper.createObjectNode();
        ArrayNode partidosJson = mapper.createArrayNode();

        try {
            // Saco toda la información del equipo y la pongo en formato clave-valor en el
            // objeto raiz
            root.put("Division", equipo.getDivision());
            root.put("Equipo", equipo.getEquipo());
            root.put("Goles a favor", equipo.getGolesFavor());
            root.put("Goles en contra", equipo.getGolesContra());
            root.put("Tarjetas amarillas", equipo.getTarjetaAmarillas());
            root.put("Tarjetas rojas", equipo.getTarjetaRojas());

            // Recupero el Mapa de partidos del equipo
            HashMap<String, List<Partido>> partidos = equipo.getPartidos();
            // Hago un array que usaré para recorrer el Mapa de partidos y para indicar el
            // nombre de cada array que vaya a poner en el JSON a volcar
            String[] estados = new String[] { "DERROTAS", "EMPATES", "VICTORIAS" };

            for (int i = 0; i < partidos.size(); i++) {
                List<Partido> tipoPartido = partidos.get(estados[i]);

                for (int j = 0; j < partidos.get(estados[i]).size(); j++) {
                    partido.put("Adversario", tipoPartido.get(j).getAdversario());
                    partido.put("Fecha", tipoPartido.get(j).getFecha().format(Partido.formatter));
                    partido.put("Local", (tipoPartido.get(j).isLocal() ? "Si" : "No"));
                    partido.put("Resultado", tipoPartido.get(j).getResultado());
                    // Añado el partido al arrayNode
                    partidosJson.add(partido);

                    // Cada vez que recupero toda la info de un partido, hago una nueva instancia de
                    // partido, para evitar conflictos
                    partido = mapper.createObjectNode();
                }

                // Añado el tipo de partidos a la raiz con su clave correspondiente (DERROTAS,
                // EMPATES o VICTORIAS)
                root.set(estados[i], partidosJson);
                
                // Cuando se termina la colección de un tipo de partidos también creo una nueva
                // instancia de ArrayNode
                partidosJson = mapper.createArrayNode();
            }
            // Finalmente pinto la raiz con el DefaultPrettyPrinter
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, root);
        } catch (IOException ex) {
            LOG.error("Ha ocurrido un error mientras se creaba el fichero JSON: " + ex.getMessage());
            return;
        }
        LOG.info("Terminó el volcado del equipo a JSON!");
    }


    // Método para volcar el Json de forma rápida
    public static void makeJsonFileFaster(File out, Equipo e) {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, e);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
