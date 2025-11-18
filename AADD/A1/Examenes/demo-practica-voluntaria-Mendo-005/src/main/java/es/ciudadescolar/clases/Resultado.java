package es.ciudadescolar.clases;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

// Clase que representa el resultado de un partido para un equipo específico
public class Resultado {

    private String adversario;   // Nombre del equipo rival
    private String resultado;    // Resultado del partido (ej: "Victoria 2-1")

    @JsonProperty("fecha")
    @JsonFormat(pattern = "dd-MM-yyyy")  // Formato de fecha para JSON
    private LocalDate fecha_partido;     // Fecha del partido

    private Boolean local;       // true = partido en casa, false = partido fuera

    public String getAdversario() {
        return adversario;
    }

    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getFecha_partido() {
        return fecha_partido;
    }

    public void setFecha_partido(LocalDate fecha_partido) {
        this.fecha_partido = fecha_partido;
    }

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public Resultado(String adversario, String resultado, LocalDate fecha_partido, Boolean local) {
        this.adversario = adversario;
        this.resultado = resultado;
        this.fecha_partido = fecha_partido;
        this.local = local;
    }

    @Override
    public String toString() {
        return "Adversario:" + adversario + ", fecha:" + fecha_partido + ", Local:" + local +", Resultado:" + resultado ;
    }
    
}
