 
package es.ciudadescolar.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import es.ciudadescolar.clases.Fichas;

public class XMLManager {
    
        
    public static List<Fichas> parseXMLFile(String fichName) 
    {
    // Creamos la lista que se va devolver
    List<Fichas> listaDeFichas = new ArrayList<Fichas>();

    // 
    Fichas ficha = null;

    // Creamos un factory para los bulder document
    try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(fichName);
        
        Element elementoRaiz = document.getDocumentElement();

        
    } catch (ParserConfigurationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (SAXException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 

    return listaDeFichas;
    }
    
}