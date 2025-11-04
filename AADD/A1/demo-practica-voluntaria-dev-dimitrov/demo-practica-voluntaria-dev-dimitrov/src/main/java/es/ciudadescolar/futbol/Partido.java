package es.ciudadescolar.futbol;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Partido {
    private String adversario;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fecha;
    
    @JsonIgnore
    private boolean local;
    @JsonProperty("Local")
    private String localString;
    private String resultado;
    // Formateador que se usará para parsear al json de forma correcta las fechas
    @JsonIgnore
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public Partido(){
        
    }

    public Partido(String adversario, LocalDate fecha, boolean local, String resultado) {
        this.adversario = adversario;
        this.fecha = fecha;
        this.local = local;
        this.resultado = resultado;
        this.localString = "";
    }

    public String getLocalString(){
        return localString;
    }

    public String getAdversario() {
        return adversario;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public boolean isLocal() {
        return local;
    }
    public String getResultado() {
        return resultado;
    }
    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setLocal(boolean local) {
        this.local = local;
        this.localString = local ? "Si" : "No";
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Partido [adversario=" + adversario + ", fecha=" + fecha + ", local=" + local + ", resultado="
                + resultado + "]";
    }
}
