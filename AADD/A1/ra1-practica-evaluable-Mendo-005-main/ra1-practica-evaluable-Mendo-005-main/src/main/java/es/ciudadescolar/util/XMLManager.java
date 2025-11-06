package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.ciudadescolar.hospital.Cita;
import es.ciudadescolar.hospital.InformeSalida;
import es.ciudadescolar.hospital.Medico;
import es.ciudadescolar.hospital.Paciente;

public class XMLManager {

    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class);

    public static Object parseXml(File ficheroXML) {
        List<Medico> listaMedico = new ArrayList<>();
        List<Paciente> listaPaciente = new ArrayList<>();
        List<Cita> listaCita = new ArrayList<>();
        
        Map<String, Medico> medicosMap = new HashMap<>();
        Map<String, Paciente> pacientesMap = new HashMap<>();

        // Creamos parseo del XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try 
            {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document documento = db.parse(ficheroXML);
                Element elementoRaiz = documento.getDocumentElement();

                // Atributos del hospital
                String nombreHospital = elementoRaiz.getAttribute("nombre");
                String ciudad = elementoRaiz.getAttribute("ciudad");
                LOG.info("Parseando hospital: {} en {}", nombreHospital, ciudad);

                // Parsear médicos
                NodeList medicosNodes = elementoRaiz.getElementsByTagName("Medico");
                for (int i = 0; i < medicosNodes.getLength(); i++) {
                    Node nodoMedico = medicosNodes.item(i);
                    if (nodoMedico.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoMedico = (Element) nodoMedico;
                        
                        Medico medico = new Medico();
                        medico.setId(elementoMedico.getAttribute("id"));
                        medico.setEspecialidad(elementoMedico.getAttribute("especialidad"));
                        
                        // Obtener elementos hijo
                        NodeList hijos = elementoMedico.getChildNodes();
                        for (int j = 0; j < hijos.getLength(); j++) {
                            Node hijo = hijos.item(j);
                            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoHijo = (Element) hijo;
                                switch (elementoHijo.getTagName()) {
                                    case "Nombre":
                                        medico.setNombre(elementoHijo.getTextContent());
                                        break;
                                    case "Apellido":
                                        medico.setApellido(elementoHijo.getTextContent());
                                        break;
                                    case "Telefono":
                                        // Se lee pero no se guarda porque la clase no tiene este campo
                                        LOG.debug("Teléfono del médico {}: {}", medico.getId(), elementoHijo.getTextContent());
                                        break;
                                    case "Email":
                                        // Se lee pero no se guarda porque la clase no tiene este campo
                                        LOG.debug("Email del médico {}: {}", medico.getId(), elementoHijo.getTextContent());
                                        break;
                                }
                            }
                        }
                        
                        listaMedico.add(medico);
                        medicosMap.put(medico.getId(), medico);
                    }
                }

                // Parsear pacientes
                NodeList pacientesNodes = elementoRaiz.getElementsByTagName("Paciente");
                for (int i = 0; i < pacientesNodes.getLength(); i++) {
                    Node nodoPaciente = pacientesNodes.item(i);
                    if (nodoPaciente.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoPaciente = (Element) nodoPaciente;
                        
                        Paciente paciente = new Paciente();
                        paciente.setId(elementoPaciente.getAttribute("id"));
                        paciente.setHabitacion(elementoPaciente.getAttribute("habitacion"));
                        paciente.setSeguro(elementoPaciente.getAttribute("seguro"));
                        
                        // Obtener elementos hijo
                        NodeList hijos = elementoPaciente.getChildNodes();
                        for (int j = 0; j < hijos.getLength(); j++) {
                            Node hijo = hijos.item(j);
                            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoHijo = (Element) hijo;
                                switch (elementoHijo.getTagName()) {
                                    case "Nombre":
                                        paciente.setNombre(elementoHijo.getTextContent());
                                        break;
                                    case "Apellido":
                                        paciente.setApellido(elementoHijo.getTextContent());
                                        break;
                                    case "FechaNacimiento":
                                        // Se lee pero no se guarda porque la clase no tiene este campo
                                        LOG.debug("Fecha nacimiento del paciente {}: {}", paciente.getId(), elementoHijo.getTextContent());
                                        break;
                                    case "Telefono":
                                        // Se lee pero no se guarda porque la clase no tiene este campo
                                        LOG.debug("Teléfono del paciente {}: {}", paciente.getId(), elementoHijo.getTextContent());
                                        break;
                                    case "Email":
                                        // Se lee pero no se guarda porque la clase no tiene este campo
                                        LOG.debug("Email del paciente {}: {}", paciente.getId(), elementoHijo.getTextContent());
                                        break;
                                }
                            }
                        }
                        
                        listaPaciente.add(paciente);
                        pacientesMap.put(paciente.getId(), paciente);
                    }
                }

                // Parsear citas
                NodeList citasNodes = elementoRaiz.getElementsByTagName("Cita");
                for (int i = 0; i < citasNodes.getLength(); i++) {
                    Node nodoCita = citasNodes.item(i);
                    if (nodoCita.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoCita = (Element) nodoCita;
                        
                        Cita cita = new Cita();
                        cita.setId(elementoCita.getAttribute("id"));
                        cita.setEstado(elementoCita.getAttribute("estado"));
                        
                        String fechaStr = elementoCita.getAttribute("fecha");
                        if (!fechaStr.isEmpty()) {
                            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate fechaLD = LocalDate.parse(fechaStr, dateformatter);
                            cita.setFecha(fechaLD);
                        }
                        
                        // Obtener elementos hijo
                        NodeList hijos = elementoCita.getChildNodes();
                        for (int j = 0; j < hijos.getLength(); j++) {
                            Node hijo = hijos.item(j);
                            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoHijo = (Element) hijo;
                                switch (elementoHijo.getTagName()) {
                                    case "PacienteRef":
                                        String pacienteId = elementoHijo.getAttribute("id");
                                        Paciente pacienteRef = pacientesMap.get(pacienteId);
                                        cita.setPaciente(pacienteRef);
                                        break;
                                    case "MedicoRef":
                                        String medicoId = elementoHijo.getAttribute("id");
                                        Medico medicoRef = medicosMap.get(medicoId);
                                        cita.setMedico(medicoRef);
                                        break;
                                    case "Motivo":
                                        cita.setMotivo(elementoHijo.getTextContent());
                                        break;
                                }
                            }
                        }
                        
                        listaCita.add(cita);
                    }
                }

                LOG.info("Parseado completado: {} médicos, {} pacientes, {} citas", 
                        listaMedico.size(), listaPaciente.size(), listaCita.size());

                return new InformeSalida(listaMedico, listaPaciente, listaCita);

            } catch (ParserConfigurationException e) 
            {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);
            } catch (SAXException e) {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);
            } catch (IOException e) {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);

            }

            return null;  
            
    }

    public static boolean validarContraXSD(File ficheroXML, File ficheroXSD) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(ficheroXSD);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(ficheroXML));
            LOG.info("El archivo XML es válido según el XSD");
            return true;
        } catch (Exception e) {
            LOG.error("El archivo XML no es válido: {}", e.getMessage());
            return false;
        }
    }

    public static void generarXML(InformeSalida informe, File archivoSalida, String nombreHospital, String ciudad) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Elemento raíz
            Element hospital = doc.createElement("Hospital");
            doc.appendChild(hospital);
            
            // Elementos nombre y ciudad (según XSD)
            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(nombreHospital);
            hospital.appendChild(nombre);
            
            Element ciudadElem = doc.createElement("ciudad");
            ciudadElem.setTextContent(ciudad);
            hospital.appendChild(ciudadElem);

            // Médicos directamente bajo Hospital (según XSD)
            for (Medico medico : informe.getListaMedicos()) {
                Element medicoElem = doc.createElement("Medico");
                medicoElem.setAttribute("id", medico.getId());
                medicoElem.setAttribute("especialidad", medico.getEspecialidad());
                medicoElem.setAttribute("Nombre", medico.getNombre()); // Atributo según XSD
                medicoElem.setAttribute("Apellido", medico.getApellido()); // Atributo según XSD
                hospital.appendChild(medicoElem);
            }

            // Pacientes directamente bajo Hospital (según XSD)
            for (Paciente paciente : informe.getListaPacientes()) {
                Element pacienteElem = doc.createElement("Paciente");
                pacienteElem.setAttribute("id", paciente.getId());
                pacienteElem.setAttribute("seguro", paciente.getSeguro());
                pacienteElem.setAttribute("Nombre", paciente.getNombre()); // Atributo según XSD
                pacienteElem.setAttribute("Apellido", paciente.getApellido()); // Atributo según XSD
                hospital.appendChild(pacienteElem);
            }

            // Citas
            Element citas = doc.createElement("Citas");
            hospital.appendChild(citas);
            for (Cita cita : informe.getListaCitas()) {
                Element citaElem = doc.createElement("Cita");
                citaElem.setAttribute("id", cita.getId());
                citaElem.setAttribute("fecha", cita.getFecha().toString());
                citaElem.setAttribute("estado", cita.getEstado());
                
                Element pacienteRef = doc.createElement("PacienteRef");
                pacienteRef.setAttribute("id", cita.getPaciente().getId());
                citaElem.appendChild(pacienteRef);
                
                Element medicoRef = doc.createElement("MedicoRef");
                medicoRef.setAttribute("id", cita.getMedico().getId());
                citaElem.appendChild(medicoRef);
                
                Element motivo = doc.createElement("Motivo");
                motivo.setTextContent(cita.getMotivo());
                citaElem.appendChild(motivo);
                
                citas.appendChild(citaElem);
            }

            // Escribir al archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivoSalida);
            transformer.transform(source, result);
            
            LOG.info("XML generado correctamente en: {}", archivoSalida.getAbsolutePath());
            
        } catch (Exception e) {
            LOG.error("Error al generar XML: {}", e.getMessage(), e);
        }
    }
}
