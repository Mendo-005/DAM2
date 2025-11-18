package es.ciudadescolar;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Game;
import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.util.TxtManager;
import es.ciudadescolar.util.XmlManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    // Ej 1) 
    private static File ficheroXml = new File("Sega_Dreamcast.xml");
    private static File ficheroSalidaTxt = new File("juegos_MarioMendoza.txt");

    // Ej 2)
    private static File ficheroJson = new File("Sega_Dreamcast.json");
    private static File ficheroSalidaXml = new File("juegos_MarioMendoza.xml");

    // Ej Extra)
    private static File ficheroSalidaJson = new File("juegos_MarioMendoza.json");



    public static void main(String[] args) {

        // Ej 1) 
        if (!ficheroXml.canRead()) {
            LOG.error("Error en la lectura del fichero: " + ficheroXml.getName());
        }
        else {
            @SuppressWarnings("unchecked")
            List<Game> listaJuegosXml = (List<Game>) XmlManager.parseoXml(ficheroXml);
            
            TxtManager.generarTxt(listaJuegosXml, ficheroSalidaTxt);
            if (ficheroSalidaTxt.exists()) {

                LOG.info("Se genero el fichero: " + ficheroSalidaTxt);                
            } 
            else {
                
                LOG.error("Error en la creacion del fichero: " +  ficheroSalidaTxt);
            }
        }

        // Ej 2)

        if (!ficheroJson.canRead()) {
            LOG.error("Error en la lectura del fichero: " + ficheroJson.getName());
        }
        else {

            @SuppressWarnings("unchecked")
            List<Game> listaJuegosJson = (List<Game>) JsonManager.parsearJson(ficheroJson);
            
            @SuppressWarnings("unchecked")
            List<Game> listaJuegosTxt = (List<Game>) TxtManager.leerTxt(ficheroSalidaTxt);
            listaJuegosJson.addAll(listaJuegosTxt);

            try {

                XmlManager.generarXml(ficheroSalidaXml, listaJuegosJson);
                if (ficheroSalidaXml.exists()) {

                LOG.info("Se genero el fichero: " + ficheroSalidaXml);                
                }

            } catch (Exception e) {
                LOG.error("Error en la creacion del fichero: " +  ficheroSalidaXml + " | " + e);
            }
            
        }

        // Ej Extra) 
        @SuppressWarnings("unchecked")
        Map<String, Object> datos = (Map<String, Object>) XmlManager.parseoXmlToJson(ficheroXml);
        @SuppressWarnings("unchecked")
        List<Game> listaJuegos = (List<Game>) datos.get("juegos");
        @SuppressWarnings("unchecked")
        Map<String,String> valoracion = (Map<String, String>) datos.get("valoracion");

        try {
            
            JsonManager.generarJson(ficheroSalidaJson, listaJuegos, valoracion);
            if (ficheroSalidaJson.exists()) {

                LOG.info("Se genero el fichero: " + ficheroSalidaJson);                
            }

        } catch (Exception e) {
            LOG.error("Error en la creacion del fichero: " +  ficheroSalidaJson + " | " + e);
        }

    }
}