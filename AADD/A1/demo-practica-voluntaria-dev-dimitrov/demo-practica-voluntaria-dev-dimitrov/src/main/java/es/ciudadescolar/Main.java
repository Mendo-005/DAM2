package es.ciudadescolar;
import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;

import es.ciudadescolar.futbol.Equipo;
import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.util.XmlManager;
import org.slf4j.LoggerFactory;

public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File ficheroXml = new File("atrib_definition.xml");
    private static File ficheroJson = new File("season-1819.json");
    private static String nameFile = "demo-practica-voluntaria-dev-dimitrov.json";
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) 
    {
        System.out.print("Introduce el nombre del equipo que quieras consultar sus estadísticas: ");
        String userInput = sc.nextLine().trim();

        XmlManager.parseXml(ficheroXml);
        Equipo e = JsonManager.getTeamStats(userInput, ficheroJson);
        if(e != null){
            JsonManager.makeJsonFile(new File(e.getEquipo()+"-"+nameFile), e);
        }
        else{
            LOG.error("El equipo especificado '"+userInput+"' no se encontró en el fichero JSON");
        }
        sc.close();
    }
}
