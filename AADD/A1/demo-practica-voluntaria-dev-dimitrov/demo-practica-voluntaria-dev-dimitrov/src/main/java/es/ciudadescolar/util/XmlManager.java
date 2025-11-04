package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager {
    private static Logger LOG = LoggerFactory.getLogger(XmlManager.class);
    private static Map<String, String> ids;

    // método que parsea el xml a un hashmap en el que el que la clave será la descripción en español 
    // y el valor será el identificador que se utiliza en el json.
    // Dicho hashmap se usará para posteriormente acceder más facilmente a la información del json
    public static void parseXml(File f){
        ids = new HashMap<>();
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document d = null;
        Element at = null;
        NodeList lista = null;
        try{
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            d = db.parse(f);
            lista = d.getElementsByTagName("atributo");

            for(int i = 0;i<lista.getLength(); i++){
                at = (Element)lista.item(i);
                // Le quito espacios para evitar confusiones y aplico trim para que no haya espacios en los extremos
                String k = at.getAttribute("value").replace(" ", "_").trim();
                String v = at.getAttribute("name");
                ids.put(k, v);
            }
        }
        catch(ParserConfigurationException | IOException | SAXException e){
            LOG.error("Hubo un problema mientras se parseaba el XML: "+e.getMessage());
            return;
        }
        LOG.info("Se completó el parseo del XML");
    }
    
    // Para sacar los valores del mapa, devuelve null en caso 
    // hago una comprobación de si el mapa es nulo, ya que si intento sacar algo de un mapa nulo, saltará exception.
    public static String get(String k){
        return ids == null ? "EL HASHMAP ES NULL" : ids.get(k); 
    }
}
