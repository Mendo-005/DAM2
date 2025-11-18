package es.ciudadescolar;

import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Equipo;
import es.ciudadescolar.util.JsonManager;

public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File ficheroJson = new File("season-1819.json");
    
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Escriba el equipo: ");
        String equipo = scanner.nextLine();

        try 
        {
            JsonManager.parseoJson(ficheroJson, equipo);
            LOG.info("Se ha completado el parseo del fichero: " + ficheroJson.getName() + " | " + equipo);

        } catch (Exception e) 
        
        {
            LOG.error("No se ha podido leer el fichero: " + ficheroJson.getName());
        }

        File ficheroSalida = new File("temporada_" + equipo.toUpperCase() + ".json");

        if (!equipo.isEmpty()) 
        {
            Equipo listaEquipo = JsonManager.parseoJson(ficheroJson, equipo);
            JsonManager.generarJson(listaEquipo, ficheroSalida);
        }
        scanner.close();
    }
    
}