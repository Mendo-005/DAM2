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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class); 
    private static Map<String,String> mapaAtributos;


    /**
     * @param ficheroXml
     * @return
     */
    public static void parseoAtributosXml(File ficheroXml)
    {
        mapaAtributos = new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(ficheroXml);

            Element nodoRaiz = document.getDocumentElement();

            NodeList nodeAtributo = nodoRaiz.getElementsByTagName("atributo");
            for (int i = 0; i < nodeAtributo.getLength(); i++) 
            {
                Node atributoNodo = nodeAtributo.item(i);
                if (atributoNodo.getNodeType() == Node.ELEMENT_NODE) 
                {
                   Element atributoElement = (Element) atributoNodo;
                   
                   String k = atributoElement.getAttribute("name");
                   String v = atributoElement.getAttribute("value");
                   
                    mapaAtributos.put(k, v);
                }
            }
            
            } catch (SAXException e) {
            LOG.error("El parseo no se pudo ejecutar correctamente: ", e);
            } catch (IOException e) {
            LOG.error("El parseo no se pudo ejecutar correctamente: ", e);
            } catch (ParserConfigurationException e) 
            {
            LOG.error("El parseo no se pudo ejecutar correctamente: ", e);
            }     
    }

    public static String get(String k)
    {
        return mapaAtributos == null ? "Error" : mapaAtributos.get(k);
    }
    
}
