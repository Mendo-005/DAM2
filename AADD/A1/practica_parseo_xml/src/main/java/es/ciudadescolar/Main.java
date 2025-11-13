package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.InformeSalida;
import es.ciudadescolar.clases.Medico;
import es.ciudadescolar.util.TxtManager;
import es.ciudadescolar.util.XmlManager;

public class Main 
{

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File ficheroXmlSimple = new File("simple.xml");
    private static File ficheroXmlAtributo = new File("con_atributos.xml");
    private static File ficheroXmlCompleto = new File("hospital.xml");
    private static File ficheroXmlNuevo = new File("hospital_2.0.xml");
    private static File ficheroTXT = new File("medicos.txt");

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
        listaMedicos.addAll(TxtManager.leerNuevMedicos(ficheroTXT));
        XmlManager.generarXML(informe, ficheroXmlNuevo);

    }
}