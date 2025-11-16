package es.ciudadescolar.clases;

public class Cine {

    private String cine;
    private String pais;
    private String titulo;

    public String getCine() {
        return cine;
    }
    public void setCine(String cine) {
        this.cine = cine;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Cine() {
    }
    public Cine(String cine, String pais, String titulo) {
        this.cine = cine;
        this.pais = pais;
        this.titulo = titulo;
    }
    
    @Override
    public String toString() {
        return "Cine [cine=" + cine + ", pais=" + pais + ", titulo=" + titulo + "]";
    }

}