package es.ciudadescolar.clases;

public class Game {

    private String nombreJuego;
    private String desarrollador;
    private String año;

    public String getNombreJuego() {
        return nombreJuego;
    }
    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }
    public String getDesarrollador() {
        return desarrollador;
    }
    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }
    public String getAño() {
        return año;
    }
    public void setAño(String año) {
        this.año = año;
    }

    public Game(String nombreJuego, String desarrollador, String año) {
        this.nombreJuego = nombreJuego;
        this.desarrollador = desarrollador;
        this.año = año;
    }
    @Override
    public String toString() {
        return nombreJuego + " | " + desarrollador + " | " + año ;
    }   
}
