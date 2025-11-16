package es.ciudadescolar.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import es.ciudadescolar.clases.Hacker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlManager {

    private static final Logger LOG = LoggerFactory.getLogger(XmlManager.class);

    public static void generarXml(List<Hacker> listaHackers, Map<String, String> idiomas, File ficheroSalida)
    {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Elemento Raiz
            Element hackers = doc.createElement("hackers");
            doc.appendChild(hackers);

            for (Hacker h : listaHackers) {
                Element hacker = doc.createElement("hacker");
                hacker.setAttribute("id", h.getId());
                
                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(h.getNombre());
                hacker.appendChild(nombre);
                
                Element apellido = doc.createElement("apellido");
                apellido.setTextContent(h.getApellido());
                hacker.appendChild(apellido);
                
                hackers.appendChild(hacker);
            }

            Element resumen = doc.createElement("resumen");
            
            Element notaMedia = doc.createElement("media_notas");
            double nota_media = 0;

            for (Hacker h : listaHackers) {
                nota_media += h.getNota();
            }
            
            if (listaHackers.size() > 0) {
                nota_media = nota_media / listaHackers.size();
            }

            notaMedia.setTextContent(String.valueOf(nota_media));
            
            resumen.appendChild(notaMedia);

            // Contar por idioma
            Map<String, Integer> conteoIdiomas = new HashMap<>();
            for (Hacker h : listaHackers) {
                String idioma = idiomas.get(h.getId());
                
                // Si el idioma ya existe, suma 1. Si no existe, empieza en 0 y suma 1
                if (conteoIdiomas.containsKey(idioma)) {
                    int cantidad = conteoIdiomas.get(idioma);
                    conteoIdiomas.put(idioma, cantidad + 1);
                } else {
                    conteoIdiomas.put(idioma, 1);
                }
            }
            
            // Agregar elementos para cada idioma
            for (String idioma : conteoIdiomas.keySet()) {
                Element idimaElement = doc.createElement("idioma");
                Integer cantidad = conteoIdiomas.get(idioma);
                idimaElement.setAttribute("num_hackers", String.valueOf(cantidad));
                idimaElement.setTextContent(idioma);
                resumen.appendChild(idimaElement);
            }
            
            hackers.appendChild(resumen);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(ficheroSalida);

            transformer.transform(source, result);

            LOG.info("XML generado con exito: " + ficheroSalida.getAbsolutePath());

        } catch (ParserConfigurationException e) {
            LOG.error("Error en la creacion del fichero: " + ficheroSalida);
        } catch (Exception e) {
            LOG.error("Error al generar el XML: " + e.getMessage());
        }

    }

    public static void validarXml(File xmlFileSalida, File validador)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringElementContentWhitespace(true);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try 
        {
            Schema schema = sf.newSchema(validador);
            dbf.setSchema(schema);

            LOG.info("XML validado");
        } catch (SAXException e) 
        {
            LOG.error("Error, no se ha podido validar el XML: " + e);
        }
    }
}
