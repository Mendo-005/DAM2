package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Medico;
import es.ciudadescolar.util.XMLManager;

public class Main 
{

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File ficheroXmlSimple = new File("simple.xml");
    private static File ficheroXmlAtributo = new File("con_atributos.xml");
    private static File ficheroXmlCompleto = new File("hospital.xml");

    public static void main(String[] args) 
    {
        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlSimple + " no se pude leer: "+ ficheroXmlSimple.getAbsolutePath());    
        }
        
        XMLManager.parseXmlSimple(ficheroXmlSimple);

        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlAtributo + " no se pude leer: "+ ficheroXmlAtributo.getAbsolutePath());    
        }

        XMLManager.parseXmlConAtributos(ficheroXmlAtributo);

        if (!ficheroXmlSimple.canRead()) 
        {
            LOG.warn("El fichero "+ ficheroXmlCompleto + " no se pude leer: "+ ficheroXmlCompleto.getAbsolutePath());    
        }

        Object resultado = XMLManager.parseXmlCompleta(ficheroXmlCompleto);
        
        // Mostrar todos los médicos
        if (resultado instanceof List) {
            @SuppressWarnings("unchecked")
            List<Medico> listaMedicos = (List<Medico>) resultado;
            
            System.out.println("\n=== LISTA DE MÉDICOS ===");
            listaMedicos.forEach(medico -> {
                System.out.println(medico);
            });
        }
    }
}