package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.InformeSalida;
import es.ciudadescolar.clases.Medico;
import es.ciudadescolar.clases.Paciente;
import es.ciudadescolar.util.TxtManager;
import es.ciudadescolar.util.XmlManager;
import es.ciudadescolar.util.JsonManager;

public class Main 
{

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    // XML
    private static File ficheroXmlSimple = new File("simple.xml");
    private static File ficheroXmlAtributo = new File("con_atributos.xml");
    private static File ficheroXmlCompleto = new File("hospital.xml");
    private static File ficheroXmlNuevo = new File("hospital_2.0.xml");
    
    // TXT
    private static File ficheroTXT = new File("medicos.txt");
    
    // Validadores XSD & DTD
    private static File ficheroXsdSalida = new File("hospital_salida.xsd");
    private static File ficheroDtdSalida = new File("hospital_salida.dtd");
    
    // Json
    private static File ficheroSimpleJson = new File("simple_medico.json");
    private static File ficheroSalidaJsonCompleto = new File("pacientes.json");
    private static File ficheroEntradaJsonCompleto = new File("hospital_completo.json");


    public static void main(String[] args) 
    {
        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlSimple + " no se pude leer: "+ ficheroXmlSimple.getAbsolutePath());    
        }
        
        XmlManager.parseXmlSimple(ficheroXmlSimple);

        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlAtributo + " no se pude leer: "+ ficheroXmlAtributo.getAbsolutePath());    
        }

        XmlManager.parseXmlConAtributos(ficheroXmlAtributo);

        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlCompleto + " no se pude leer: "+ ficheroXmlCompleto.getAbsolutePath());    
        }

        //Object resultadoMedicos = XMLManager.parseXmlCompleta(ficheroXmlCompleto);
        //
        //// Mostrar todos los médicos
        //if (resultadoMedicos instanceof List<?>) {
        //    List<Medico> medicos = (List<Medico>) resultadoMedicos;
        //    LOG.info("Lista de médicos ({}):", medicos.size());
        //    medicos.forEach(medico -> LOG.info(medico.toString()));
        //}
        //
        //Object resultadoPacientes = XMLManager.parseXmlCompleta(ficheroXmlCompleto);
        //
        //// Mostrar todos los médicos
        //if (resultadoPacientes instanceof List<?>) {
        //    List<Paciente> pacientes = (List<Paciente>) resultadoPacientes;
        //    LOG.info("Lista de médicos ({}):", pacientes.size());
        //    pacientes.forEach(medico -> LOG.info(medico.toString()));
        //}

        InformeSalida informe = (InformeSalida) XmlManager.parseXmlCompleta(ficheroXmlCompleto);
        List<Medico> listaMedicos = informe.getListaMedicos();
        
        // Añadimos los Medicos del Txt
        listaMedicos.addAll(TxtManager.leerNuevMedicos(ficheroTXT));
        // Añadimos los Medicos del Json Simple
        listaMedicos.addAll(JsonManager.medicoSimple(ficheroSimpleJson));
        
        XmlManager.generarXML(informe, ficheroXmlNuevo);
        System.out.println("informe generado");

        // Validar el XML de salida generado
        LOG.info("=== Validando XML de salida ===");
        XmlManager.validarXML(ficheroXmlNuevo, ficheroXsdSalida);


        /*
         *  Parsear y generar fichero Pacientes Json
         */
        try {
            List<Paciente> listaPacientes = JsonManager.medicoCompleto(ficheroEntradaJsonCompleto);
            JsonManager.crearJsonPacientes(listaPacientes, ficheroSalidaJsonCompleto);
            LOG.info("JSON generado: " + ficheroSalidaJsonCompleto.getName());
        } catch (Exception e) 
        {
            LOG.info("Error en la generacion del JSON");
        }
    }
}