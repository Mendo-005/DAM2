package es.ciudadescolar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Game;

public class TxtManager {

    private static final Logger LOG = LoggerFactory.getLogger(TxtManager.class);


    public static void generarTxt(List<Game> listaJuegos, File ficheroSalida)
    {
        try {
            FileWriter fw = new FileWriter(ficheroSalida);
            PrintWriter pt = new PrintWriter(fw);

            pt.println("nombreJuego | desarrollador | año");

            for (Game juego : listaJuegos) {
                pt.println(juego); 
                LOG.info(juego.toString() + " se incluyo en el fichero: " + ficheroSalida.getName());
            }

            pt.close();

            LOG.info("Se han añadido todos los juegos al fichero: " + ficheroSalida.getName());
        } catch (IOException e) {
            LOG.error("Error en la generacion del fichero: " + ficheroSalida.getName() + " | " + e);
        }
    }

    public static Object leerTxt(File ficheroTxt){

        List<Game> listaJuegosTxt = new ArrayList<>();

        try {

            FileReader fr = new FileReader(ficheroTxt);

            if (fr.ready()) {

                BufferedReader br = new BufferedReader(fr);
                String linea = br.readLine();
                linea = br.readLine(); // Skip header line

                while (linea != null) {

                    String[] partes = linea.split("\\|");
                    Game juego = new Game(partes[0].trim(), partes[1].trim(), partes[2].trim());
                    listaJuegosTxt.add(juego);
                    linea = br.readLine();
    
                }

                br.close();
                fr.close();
            }

        } catch (IOException e) {
            LOG.error("Error al leer el fichero: " + ficheroTxt.getName() + " | " + e);
        }

        return listaJuegosTxt;
    }  
}
