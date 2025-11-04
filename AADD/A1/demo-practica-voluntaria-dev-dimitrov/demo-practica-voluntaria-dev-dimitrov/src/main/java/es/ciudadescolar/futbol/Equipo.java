package es.ciudadescolar.futbol;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Equipo {
    
    private String equipo;
    
    @JsonProperty("Division")
    private String division;

    @JsonProperty("Goles a favor")
    private Integer golesFavor;

    @JsonProperty("Goles en favor")
    private Integer golesContra;
     
    @JsonProperty("Tarjetas amarillas")
    private Integer tarjetaAmarillas;

    @JsonProperty("Tarjetas rojas")
    private Integer tarjetaRojas;

    private HashMap<String, List<Partido>> partidos;
    public Equipo(){
        
    }

    public Equipo(String equipo, String division, Integer golesFavor, Integer golesContra, Integer tarjetaAmarillas,
        Integer tarjetaRojas) {
        
        // Importante inicializar bien el mapa de partidos
        partidos = new HashMap<>();
        partidos.put("DERROTAS", new ArrayList<>());
        partidos.put("EMPATES", new ArrayList<>());
        partidos.put("VICTORIAS", new ArrayList<>());

        this.equipo = equipo;
        this.division = division;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.tarjetaAmarillas = tarjetaAmarillas;
        this.tarjetaRojas = tarjetaRojas;
    }


    public String getEquipo() {
        return equipo;
    }


    public String getDivision() {
        return division;
    }


    public Integer getGolesFavor() {
        return golesFavor;
    }


    public Integer getGolesContra() {
        return golesContra;
    }


    public Integer getTarjetaAmarillas() {
        return tarjetaAmarillas;
    }


    public Integer getTarjetaRojas() {
        return tarjetaRojas;
    }


    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }


    public void setDivision(String division) {
        this.division = division;
    }

    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }

    public void addGolesFavor(Integer golesFavor) {
        this.golesFavor += golesFavor;
    }

    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }

    public void addGolesContra(Integer golesContra) {
        this.golesContra += golesContra;
    }

    public void setTarjetaAmarillas(Integer tarjetaAmarillas) {
        this.tarjetaAmarillas = tarjetaAmarillas;
    }

    public void addTarjetaAmarillas(Integer tarjetaAmarillas) {
        this.tarjetaAmarillas += tarjetaAmarillas;
    }

    public void setTarjetaRojas(Integer tarjetaRojas) {
        this.tarjetaRojas = tarjetaRojas;
    }

    public void addTarjetaRojas(Integer tarjetaRojas) {
        this.tarjetaRojas += tarjetaRojas;
    }

    public void addPartido(Partido p){
        String[] resultado = p.getResultado().split("-");
        int resultadoLocal = Integer.parseInt(resultado[0]);
        int resultadoVisitante = Integer.parseInt(resultado[1]);
        
        if(resultadoLocal>resultadoVisitante){
            partidos.get(p.isLocal() ? "VICTORIAS" : "DERROTAS").add(p);
        }
        else if(resultadoLocal<resultadoVisitante){
            partidos.get(!p.isLocal() ? "VICTORIAS" : "DERROTAS").add(p);

        }
        else{ // Si es emapte si o si se va ahi, sin importar quien es el visitante
            partidos.get("EMPATES").add(p);
        }
    }

    public HashMap<String, List<Partido>> getPartidos(){
        return partidos;
    }


    @Override
    public String toString() {
        return "Equipo [equipo=" + equipo + ", division=" + division + ", golesFavor=" + golesFavor + ", golesContra="
                + golesContra + ", tarjetaAmarillas=" + tarjetaAmarillas + ", tarjetaRojas=" + tarjetaRojas
                + ", partidos=" + partidos + "]";
    }
}
