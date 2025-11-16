package es.ciudadescolar;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Hacker;
import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.util.XmlManager;



public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File ficheroJson = new File("hackers.json");
    private static File ficheroXsd = new File("hackers.xsd");
    private static File ficheroSalidaXml = new File("hackers_resumen.xml");

    public static void main(String[] args) {
        
        try {
            Map<String, Object> datos = JsonManager.parseoJson(ficheroJson);
            List<Hacker> informe = (List<Hacker>) datos.get("hackers");
            Map<String, String> idiomas = (Map<String, String>) datos.get("idiomas");
            
            XmlManager.generarXml(informe, idiomas, ficheroSalidaXml);

            LOG.info("Programa ejecutado correctamente");
            
        } catch (Exception e) {

            LOG.error("Programa no se pudo ejecutar: " + e);
        }

        try {
            XmlManager.validarXml(ficheroSalidaXml, ficheroXsd);

        } catch (Exception e) {
            
            LOG.error("Programa no pudo ejecutar la validacion: " + e);

        }


    }
}
