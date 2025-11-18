package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.ciudadescolar.clases.Cine;

public class XmlManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(XmlManager.class);

    public static Object parseoXml(File ficheroXml)
    {
        List<Cine> listaPeliculas = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ficheroXml);

            Element root = doc.getDocumentElement();

            NodeList peliculas = root.getElementsByTagName("pelicula");
            for (int i = 0; i < peliculas.getLength(); i++) {

                Node peliculaNode = peliculas.item(i);
                if (peliculaNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element peliculaElm = (Element) peliculaNode;

                    Cine pelicula = new Cine();
                    pelicula.setPais(peliculaElm.getElementsByTagName("pais").item(0).getTextContent());
                    pelicula.setTitulo(peliculaElm.getElementsByTagName("titulo").item(0).getTextContent());
                    pelicula.setCine(root.getElementsByTagName("cine").item(0).getTextContent());

                    listaPeliculas.add(pelicula);
                }
            } 

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Error en el parseo del fichero: " + ficheroXml.getName() + " | " + e);
        }
        
        return listaPeliculas; 
    }

    public static void generarXml(List<Cine> listaPeliculas, File ficheroSalida, File validador)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element cartelera = doc.createElement("cartelera");
            doc.appendChild(cartelera);

            for (Cine pelicula : listaPeliculas) {

                Element peliculaElm = doc.createElement("pelicula");
                peliculaElm.setAttribute("titulo", pelicula.getTitulo());
                peliculaElm.setAttribute("pais", pelicula.getPais());
                
                cartelera.setAttribute("cine", pelicula.getCine());
                cartelera.appendChild(peliculaElm);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            try {

                Transformer transformer = tf.newTransformer();

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(ficheroSalida);

                DOMImplementation domImp = doc.getImplementation();
                DocumentType docType = domImp.createDocumentType("doctype", null, validador.getName());
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());

                transformer.setOutputProperty(OutputKeys.METHOD, "xml");                 
                transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");                 
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                transformer.transform(source, result);


            } catch (TransformerConfigurationException e) {
                LOG.error("Error ejecucion del transform: " + ficheroSalida.getName() + " | " + e);

            } catch (TransformerException e) {
                LOG.error("Error ejecucion del transform: " + ficheroSalida.getName() + " | " + e);
            }

        } catch (ParserConfigurationException e) {
            LOG.error("Error en la generacion del fichero: " + ficheroSalida.getName() + " | " + e);
        }
    }
    
    //public static void validarFichero(File ficheroSalidaXml, File validador)
    //{
    //    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
    //        try {
    //            DocumentBuilder db = dbf.newDocumentBuilder();
    //            Document doc = db.parse(ficheroSalidaXml);
//
    //            dbf.setValidating(true);
    //            dbf.setNamespaceAware(false);
    //            dbf.setIgnoringElementContentWhitespace(false);
//
    //            DOMImplementation domImp = documento.getImplementation();
    //            DocumentType docType = domImp.createDocumentType("doctype", null, ficheroSalidaXml.getName());
    //            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId())
//
    //            LOG.info("Se ha validado el fichero con exito: " + ficheroSalidaXml.getName());
//
    //        } catch (ParserConfigurationException e) {
    //            LOG.error("Error en la validacion del fichero: " + ficheroSalidaXml.getName() + " | " + e);
    //        } catch (SAXException e) {
    //            LOG.error("Error en la validacion del fichero: " + ficheroSalidaXml.getName() + " | " + e);
    //        } catch (IOException e) {
    //            LOG.error("Error en la validacion del fichero: " + ficheroSalidaXml.getName() + " | " + e);
    //        }
//
    //}
}