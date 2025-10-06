package es.ciudadescolar;

import es.ciudadescolar.clases.Modulos;
import es.ciudadescolar.util.FlujoBinario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Programa 
{    
    public static void main(String[] args) 
    {
        /*
         * Ejercicio 1:
         */
        Modulos[] modulos = {new Modulos("Bases de datos", 6, "José", 1, "DAM25fn3-a$"),
                            new Modulos("Entornos de desarrollo", 3, "Salvador", 1, "DAM25fn3-a$"),
                            new Modulos("Acceso a datos", 6, "José", 1, "DAM25fn3-a$"),
                            new Modulos("Programación de servicios y procesos", 4, "Agustín", 1, "DAM25fn3-a$"),
                            new Modulos("Programación", 8, "Salvador", 1, "DAM25fn3-a$")};
        String fichaModulos = "modulos.dat";

        FlujoBinario.escrituraModulos(modulos, fichaModulos);
        FlujoBinario.lecturaModulos(fichaModulos);

        /*
         * Ejercicio 2:
         */
        String nombreFichero = "modulos2.txt";
        File fichero = new File("modulos2.txt");

        if (fichero.exists()) {
            fichero.delete();
            try {
                fichero.createNewFile();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }   
        } else {
            try {
                fichero.createNewFile();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }
        }

        try (FileWriter fw = new FileWriter(nombreFichero, true);
            BufferedWriter bw = new BufferedWriter(fw))            {
            
            for(Modulos modulo : modulos) {
                bw.write(modulo.toString());
                bw.newLine();
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
