package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.ciudadescolar.hospital.Cita;
import es.ciudadescolar.hospital.InformeSalida;
import es.ciudadescolar.hospital.Medico;
import es.ciudadescolar.hospital.Paciente;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLManager {

    private static final Logger LOG = LoggerFactory.getLogger(XMLManager.class);

    public static Object parseXml(File ficheroXML) {
    List<InformeSalida> listaHospital = new ArrayList<>();
        


        List<Medico> listaMedico = new ArrayList<> ();
        List<Paciente> listaPaciente = new ArrayList<> ();
        List<Cita> listaCita = new ArrayList<> ();
        
        Medico medico = null;
        Paciente paciente = null;
        Cita cita = null;

        // Creamos parseo del XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try 
            {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document documento = db.parse(ficheroXML);
                Element elementoRaiz = documento.getDocumentElement();

                // Obtenemos todos los elementos del XML
                NodeList listaNodosHospital = elementoRaiz.getElementsByTagName("Hospital");
                
                for (int i = 0; i < listaNodosHospital.getLength(); i++) 
                {
                    Node nodoH = listaNodosHospital.item(i);
                    
                    if (nodoH.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        Element elementoH = (Element) nodoH;

                        String idM = "";
                        String especialidad = "";
                        String nombreM = "";
                        String apellidoM = "";

                        String idP = "";
                        String nombreP = "";
                        String seguro= "";
                        String apellidoP = "";
                        
                        String idC = "";
                        String fecha = "";
                        String estado = "";
                        String pacienteRef = "";
                        String medicoRef = "";
                        String motivo = "";

                        

                        // Atributo ciudad
                        String nombre = elementoH.getAttribute("nombre");
                        String ciudad = elementoH.getAttribute("ciudad");
                        
                        NodeList medicosNodes = elementoH.getElementsByTagName("Medico");
                        if (medicosNodes.getLength() > 0) {
                            idM = ((Element) medicosNodes).getAttribute("id");
                            especialidad = ((Element) medicosNodes).getAttribute("especialidad");
                            
                            nombreM = medicosNodes.item(0).getTextContent();
                            apellidoM = medicosNodes.item(1).getTextContent();
                            
                        }

                        NodeList pacientesNodes = elementoH.getElementsByTagName("Paciente");
                        if (medicosNodes.getLength() > 0) {
                            idP = ((Element) medicosNodes).getAttribute("id");
                            seguro = ((Element) medicosNodes).getAttribute("seguro");
                            
                            nombreP = medicosNodes.item(0).getTextContent();
                            apellidoP = medicosNodes.item(1).getTextContent();
                        }

                        NodeList citaNodes = elementoH.getElementsByTagName("Cita");
                        if (medicosNodes.getLength() > 0) {
                            idC = ((Element) medicosNodes).getAttribute("id");
                            fecha = ((Element) medicosNodes).getAttribute("fecha");
                            estado = ((Element) medicosNodes).getAttribute("estado");

                            pacienteRef = medicosNodes.item(0).getTextContent();
                            medicoRef = medicosNodes.item(1).getTextContent();
                            motivo = medicosNodes.item(2).getTextContent();
                        }

                       //NodeList habitacionNodes = elementoH.getElementsByTagName("Habitacion");
                       //if (medicosNodes.getLength() > 0) {
                       //    estado = medicosNodes.item(0).getTextContent();
                       //}

                        // Medico
                        medico = new Medico();
                        medico.setId(idM);
                        medico.setNombre(nombreM);
                        medico.setApellido(apellidoM);
                        medico.setEspecialidad(especialidad);
                        listaMedico.add(medico);

                        // Paciente
                        paciente = new Paciente();
                        paciente.setId(idP);
                        paciente.setNombre(nombreP);
                        paciente.setApellido(apellidoP);
                        paciente.setSeguro(seguro);
                        listaPaciente.add(paciente);

                        // Cita
                        cita = new Cita();
                        cita.setId(idC);
                        cita.setEstado(estado);
                        cita.setPaciente(pacienteRef);
                        cita.setMedico(medicoRef);
                        cita.setMotivo(motivo);

                        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
                        LocalDate fechaLD = LocalDate.parse(fecha, dateformatter );
                        cita.setFecha(fechaLD);

                        listaCita.add(cita);

                        listaHospital = new InformeSalida(listaMedico, listaMedico, listaMedico);
                        listaHospital.add(listaMedico, listaMedico, listaMedico);

                    }
                }

            } catch (ParserConfigurationException e) 
            {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);
            } catch (SAXException e) {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);
            } catch (IOException e) {
                LOG.warn("Error al parsear el archivo XML: " + ficheroXML.getAbsolutePath(), e);

            }

            return listaHospital;
           
            
    }
}
