package es.ciudadescolar.utils;

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

public class XMLManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class);

    public static Object parseCatalogoProductos(File ficheroXML) {
        
        Map<String, Map<String, String>> catalogoProductos = new HashMap<>();

        // Creamos parseo del XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(ficheroXML);
            documento.getDocumentElement().normalize();

            // Obtener todos los productos
            NodeList listaProductos = documento.getElementsByTagName("producto");

            for (int i = 0; i < listaProductos.getLength(); i++) {
                Node nodoProducto = listaProductos.item(i);

                if (nodoProducto.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoProducto = (Element) nodoProducto;

                    // Atributo id
                    String id = elementoProducto.getAttribute("id");

                    // Subelementos
                    String nombre = elementoProducto.getElementsByTagName("nombre")
                            .item(0).getTextContent().trim();
                    String categoria = elementoProducto.getElementsByTagName("categoria")
                            .item(0).getTextContent().trim();

                    // Crear un mapa interno con la información del producto
                    Map<String, String> infoProducto = new HashMap<>();
                    infoProducto.put("nombre", nombre);
                    infoProducto.put("categoria", categoria);

                    // Guardar en el catálogo general
                    catalogoProductos.put(id, infoProducto);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);
        }

        return catalogoProductos;
    }

}
