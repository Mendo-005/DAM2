package es.ciudadescolar;

import java.io.File;
import java.util.Map;

import es.ciudadescolar.util.XMLManager;

public class Main 
{
    private static File ficheroXml = new File("hospital_2526.xml");
    private static String nameFileJSON = ".json";
    private static String nameFileXML = ".xml";

    public static void main(String[] args) 
    {
        XMLManager.parseXml(ficheroXml);  
    }
}
