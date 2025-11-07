package es.ciudadescolar.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import es.ciudadescolar.clases.Medico;


public class XMLManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class);


    /*
     * XML simple
     */
    public static Object parseXmlSimple(File ficheroXml)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ficheroXml);
            Element root = document.getDocumentElement();
            
            String nombre = "";
            String ubicacion = "";
            Integer capacidad = null;
            
            NodeList bibliotecaNode = root.getChildNodes();
            for (int i = 0; i < bibliotecaNode.getLength(); i++) 
            {
                Node nodeBiblioteca = bibliotecaNode.item(i);
                if (nodeBiblioteca.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element elementoBiblioteca = (Element) nodeBiblioteca;
                    switch (elementoBiblioteca.getTagName()) {
                        case "nombre":
                            nombre = elementoBiblioteca.getTextContent();
                            break;
                        case "ubicacion":
                            ubicacion = elementoBiblioteca.getTextContent();
                            break;
                        case "capacidad":
                            String capacidadStr = elementoBiblioteca.getTextContent();
                            capacidad = Integer.parseInt(capacidadStr);
                            break;
                    }

                }
            }

            LOG.info("\n Nombre: "+ nombre + "\n Ubicacion: "+ ubicacion+ "\n Capacidad: "+ capacidad);

        } catch (ParserConfigurationException | SAXException | IOException e) 
        {
            LOG.warn("Error en el parseo del fichero: " + ficheroXml.getAbsolutePath());
        }

        return null;
        
    }

    /*
     * XML con atributos
     */
    public static Object parseXmlConAtributos(File ficheroXml)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ficheroXml);
            Element root = document.getDocumentElement();
            
            String id = "";
            Boolean activo = false;

            String nombre = "";
            Integer edad = null;

            String codigo = "";
            String carrera = "";
            
            NodeList estudianteNode = root.getChildNodes();
            for (int i = 0; i < estudianteNode.getLength(); i++) 
            {
                id = root.getAttribute("id");

                String activoStr = root.getAttribute("activo");
                activo = Boolean.parseBoolean(activoStr);

                Node nodeEstudiante = estudianteNode.item(i);
                if (nodeEstudiante.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element elementoEstudiante = (Element) nodeEstudiante;

                    switch (elementoEstudiante.getTagName()) {
                        case "nombre":
                            nombre = elementoEstudiante.getTextContent();
                            break;
                        case "carrera":
                            carrera = elementoEstudiante.getTextContent();
                            codigo = elementoEstudiante.getAttribute("codigo");
                            break;
                        case "edad":
                            String edadStr = elementoEstudiante.getTextContent();
                            edad = Integer.parseInt(edadStr);
                            break;
                    }

                }
            }

            LOG.info(
            "\n Atributos: " + id + " | " + activo +
            "\n Nombre: "+ nombre + 
            "\n Carrera: "+ carrera+" ("+ codigo +")"+ 
            "\n Edad: "+ edad);

        } catch (ParserConfigurationException | SAXException | IOException e) 
        {
            LOG.warn("Error en el parseo del fichero: " + ficheroXml.getAbsolutePath());
        }

        return null;
    }

    /*
     * XML Completo
     */
    public static Object parseXmlCompleta(File ficheroXml)
    {
        List<Medico> listaMedicos = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try 
        {  
            // Constructores del Parseo
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ficheroXml);
            Element root = document.getDocumentElement();
            
            // Atributos Hospital
            String nombreHospital = root.getAttribute("nombre");
            String ciudad = root.getAttribute("ciudad");
            
            // Medicos
            NodeList medicosNode = root.getElementsByTagName("Medico");
            for (int i = 0; i < medicosNode.getLength(); i++) 
            {
                Node nodeMedicos = medicosNode.item(i);
                if (nodeMedicos.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element elementoMedicos = (Element) nodeMedicos;

                    // Atributos Medico
                    Medico medico = new Medico();
                    medico.setId(elementoMedicos.getAttribute("id"));
                    medico.setTurno(elementoMedicos.getAttribute("turno"));

                    // Sacamos a los hijos
                    NodeList hijos = elementoMedicos.getChildNodes();
                    for (int j = 0; j < hijos.getLength(); j++) 
                    {
                        Node nodeHijos = hijos.item(j);
                        if (nodeHijos.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            Element elementoHijos = (Element) hijos;
                            switch (elementoHijos.getTagName()) {
                                case "email":
                                    medico.setEmail(elementoHijos.getTextContent());
                                    break;
                                case "telefono":
                                    medico.setTelefono(elementoHijos.getTextContent());
                                    break;
                                case "apellido":
                                    medico.setApellido(elementoHijos.getTextContent());
                                    break;
                                case "nombre":
                                    medico.setNombre(elementoHijos.getTextContent());
                                    break;
                                case "especialidad":
                                    medico.setEspecialidad(elementoHijos.getTextContent());
                                    break;
                            }
                        }
                    }

                    listaMedicos.add(medico);
                }                
            }

        } catch (ParserConfigurationException | SAXException | IOException e) 
        {
            LOG.warn("Error en el parseo del fichero: " + ficheroXml.getAbsolutePath());
        }

        return listaMedicos;
    }
}
