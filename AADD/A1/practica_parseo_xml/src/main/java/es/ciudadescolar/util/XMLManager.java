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

import es.ciudadescolar.clases.InformeSalida;
import es.ciudadescolar.clases.Medico;
import es.ciudadescolar.clases.Paciente;


public class XMLManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class);

    private static String getText(Element parent, String tagName)
    {
        Node node = parent.getElementsByTagName(tagName).item(0);

        return node != null ? node.getTextContent(): null;
    }
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
        List<Paciente> listaPacientes = new ArrayList<>();

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
            NodeList medicosNode = root.getElementsByTagName("medico");
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

                    // Elementos hijos
                    medico.setEspecialidad(getText(elementoMedicos, "especialidad"));
                    medico.setNombre(getText(elementoMedicos, "nombre"));
                    medico.setApellido(getText(elementoMedicos, "apellido"));
                    medico.setTelefono(getText(elementoMedicos, "telefono"));
                    medico.setEmail(getText(elementoMedicos, "email"));
                    
                    listaMedicos.add(medico);
                }                
            }

            // Pacientes
            NodeList pacienteNode = root.getElementsByTagName("paciente");
            for (int i = 0; i < pacienteNode.getLength(); i++) 
            {
                Node listaPacientesNode = pacienteNode.item(i);
                if (listaPacientesNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element elementoPaciente = (Element) listaPacientesNode;
                    
                    // Paciente
                    Paciente paciente = new Paciente();

                    // Atributos Paciente
                    paciente.setId(elementoPaciente.getAttribute("id"));
                    paciente.setEdad(elementoPaciente.getAttribute("edad"));

                    // Elementos hijos
                    paciente.setNombre(getText(elementoPaciente, "nombre"));
                    paciente.setApellido(getText(elementoPaciente, "apellido"));
                    paciente.setTelefono(getText(elementoPaciente, "telefono"));
                    paciente.setDiagnostico(getText(elementoPaciente, "diagnostico"));

                    listaPacientes.add(paciente);
                }
            }

            return new InformeSalida(nombreHospital, ciudad, listaMedicos, listaPacientes);

        } catch (ParserConfigurationException | SAXException | IOException e) 
        {
            LOG.warn("Error en el parseo del fichero: " + ficheroXml.getAbsolutePath());
        }

        return null;
    }

    public static void generarXML(InformeSalida informe, String ciudad, String nombreHospital, File archivoSalida)
    {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Constructores del fichero
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();

                // Elemento Hospital
                Element hospital = doc.createElement("hospital");
                hospital.setAttribute("Ciudad",ciudad);
                doc.appendChild(hospital);

                Element nombreHospitalElm = doc.createElement("nombreHospital");
                nombreHospitalElm.setTextContent(nombreHospital);
                hospital.appendChild(nombreHospitalElm);

                // Medicos
                // Itineramos la lista de medicos del fichero
                for (Medico medico : informe.getListaMedicos()) 
                {
                    Element medicoElm = doc.createElement("Medico");
                    medicoElm.setAttribute("id", medico.getId());
                    medicoElm.setAttribute("Nombre", medico.getNombre());
                    medicoElm.setAttribute("Apellido", medico.getApellido());
                    medicoElm.setAttribute("Especialidad", medico.getEspecialidad());
                    hospital.appendChild(medicoElm);
                }
                
                // Constructor
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(archivoSalida);
                
                transformer.transform(source, result);
                

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
    }
}
