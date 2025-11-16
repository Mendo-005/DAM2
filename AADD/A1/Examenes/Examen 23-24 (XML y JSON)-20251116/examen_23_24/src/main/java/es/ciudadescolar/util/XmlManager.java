package es.ciudadescolar.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.ciudadescolar.clases.Game;

public class XmlManager {

    private static final Logger LOG = LoggerFactory.getLogger(XmlManager.class);

    public static Object parseoXml(File ficheroXml)
    {
        List<Game> listaJuegos = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ficheroXml);

            Element root = document.getDocumentElement();

            NodeList juegos = root.getElementsByTagName("game");
            for (int i = 0; i < juegos.getLength(); i++) {
                Node juegosNode = juegos.item(i);

                if (juegosNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element juegoElement = (Element) juegosNode;

                    Game juego = new Game(null, null, null);

                    juego.setNombreJuego(juegoElement.getAttribute("name"));
                    juego.setAño(juegoElement.getElementsByTagName("year").item(0).getTextContent());
                    juego.setDesarrollador(juegoElement.getElementsByTagName("dev").item(0).getTextContent());
                    
                    listaJuegos.add(juego);
                }
            }

        } catch (ParserConfigurationException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        } catch (SAXException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        } catch (IOException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        }

        return listaJuegos;
        
    }

    public static Object parseoXmlToJson(File ficheroXml)
    {
        List<Game> listaJuegos = new ArrayList<>();
        Map<String, String> valoracion = new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ficheroXml);

            Element root = document.getDocumentElement();

            NodeList juegos = root.getElementsByTagName("game");
            for (int i = 0; i < juegos.getLength(); i++) {
                Node juegosNode = juegos.item(i);

                if (juegosNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element juegoElement = (Element) juegosNode;

                    Game juego = new Game(null, null, null);

                    String nombre = juegoElement.getAttribute("name");
                    juego.setNombreJuego(juegoElement.getAttribute("name"));
                    juego.setAño(juegoElement.getElementsByTagName("year").item(0).getTextContent());
                    juego.setDesarrollador(juegoElement.getElementsByTagName("dev").item(0).getTextContent());

                    valoracion.put(nombre , juegoElement.getElementsByTagName("rating").item(0).getTextContent());
                    listaJuegos.add(juego);
                }
            }


        } catch (ParserConfigurationException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        } catch (SAXException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        } catch (IOException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("valoracion", valoracion);
        resultado.put("juegos", listaJuegos);

        return resultado;
        
    }

    public static void generarXml(File ficheroSalida, List<Game> listaJuegosJson )
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element juegos = doc.createElement("juegos");
            doc.appendChild(juegos);

            juegos.setAttribute("Creador", "Mario Mendoza");

            for (Game game : listaJuegosJson) {

                Element juego = doc.createElement("juego");

                Element nombreJuego = doc.createElement("nombre");
                nombreJuego.setTextContent(game.getNombreJuego());
                juego.appendChild(nombreJuego);

                Element año = doc.createElement("año");
                año.setTextContent(game.getAño());
                juego.appendChild(año);

                Element desarrollador = doc.createElement("desarrollador");
                desarrollador.setTextContent(game.getDesarrollador());
                juego.appendChild(desarrollador);

                juegos.appendChild(juego);
                
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            try {

                Transformer transformer = tf.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(ficheroSalida);

                transformer.transform(source, result);

            } catch (TransformerException e) {
                LOG.error("No se pudo generar el fichero: "  + ficheroSalida.getName() + " | " +  e);
            }

        } catch (ParserConfigurationException e) {
            LOG.error("Error en la creacion del fichero: " + ficheroSalida.getName() + " | " + e);
        }
    }

    
}
