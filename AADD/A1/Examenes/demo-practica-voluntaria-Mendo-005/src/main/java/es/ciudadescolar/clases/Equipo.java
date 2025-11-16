package es.ciudadescolar.clases;

import java.util.List;

// Clase que representa un equipo de fútbol con sus estadísticas
public class Equipo {
    
    // Datos básicos del equipo
    private String division;    // División en la que juega (ej: SP1)
    private String equipo;      // Nombre del equipo
    
    // Estadísticas de goles y disciplina
    private Integer golesFavor;         // Goles marcados
    private Integer golesContra;        // Goles recibidos
    private Integer tarjetasAmarillas;  // Total tarjetas amarillas
    private Integer tarjetasRojas;      // Total tarjetas rojas
    
    // Listas de resultados por tipo
    private List<Resultado> derrotas;   // Partidos perdidos
    private List<Resultado> empates;    // Partidos empatados
    private List<Resultado> victorias;  // Partidos ganados

    // Getters y SettersS
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public String getEquipo() {
        return equipo;
    }
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    public Integer getGolesFavor() {
        return golesFavor;
    }
    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }
    public Integer getGolesContra() {
        return golesContra;
    }
    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }
    public Integer getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }
    public void setTarjetasAmarillas(Integer tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }
    public Integer getTarjetasRojas() {
        return tarjetasRojas;
    }
    public void setTarjetasRojas(Integer tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }
    public List<Resultado> getDerrotas() {
        return derrotas;
    }
    public void setDerrotas(List<Resultado> derrotas) {
        this.derrotas = derrotas;
    }
    public List<Resultado> getEmpates() {
        return empates;
    }
    public void setEmpates(List<Resultado> empates) {
        this.empates = empates;
    }
    public List<Resultado> getVictorias() {
        return victorias;
    }
    public void setVictorias(List<Resultado> victorias) {
        this.victorias = victorias;
    }
    
    public Equipo(String division, String equipo, Integer golesFavor, Integer golesContra, Integer tarjetasAmarillas,
            Integer tarjetasRojas, List<Resultado> derrotas, List<Resultado> empates, List<Resultado> victorias) {
        this.division = division;
        this.equipo = equipo;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
        this.derrotas = derrotas;
        this.empates = empates;
        this.victorias = victorias;
    }
    
    

}
