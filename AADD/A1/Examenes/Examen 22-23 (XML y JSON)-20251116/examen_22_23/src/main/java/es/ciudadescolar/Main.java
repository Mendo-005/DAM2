package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Cine;
import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.util.XmlManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    private static File ficheroSalidaXml = new File("cine_MarioMendoza.xml");
    private static File ficheroSalidaJson = new File("cine_MarioMendoza.json");
    private static File ficheroXml = new File("cartelera.xml");
    private static File ficheroDtd = new File("cartelera.dtd");

    public static void main(String[] args) {

        if (!ficheroXml.canRead()) {
            LOG.error("El fichero no se puede leer: " + ficheroXml.getName());
        }
        else {
            
            @SuppressWarnings("unchecked")
            List<Cine> listaPeliculas = (List<Cine>) XmlManager.parseoXml(ficheroXml);
            for (Cine cine : listaPeliculas) {
                LOG.info("Contenido: " + cine);
            }
            
            XmlManager.generarXml(listaPeliculas, ficheroSalidaXml, ficheroDtd);
            if (ficheroSalidaXml.exists()) {
                LOG.info("El fichero se genero con exito: " + ficheroSalidaXml.getAbsolutePath()); 
                //XmlManager.validarFichero(ficheroSalidaXml, ficheroDtd);
            }

            JsonManager.generarJson(listaPeliculas, ficheroSalidaJson);
            if (ficheroSalidaJson.exists()) {
                LOG.info("El fichero se genero con exito: " + ficheroSalidaJson.getAbsolutePath()); 
            }

        }

        
    }
}