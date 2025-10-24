package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.JsonManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        File ficheroSalidaAlumnod = new File(("nuevosAlumnos.json"));
        File ficheroJsonAlumnos = new File("alumnos.json");
        
        List<Alumno> alumnosJson = JsonManager.parsearJsonAlumnos(ficheroJsonAlumnos);
        
        if (!alumnosJson.isEmpty())
        {
            // Nuevo Json
            JsonManager.createJsonAlumnos((alumnosJson), ficheroSalidaAlumnod);
            
        }
        else
        {
            LOG.warn("No se han recuperado ficheros json: " + ficheroJsonAlumnos.getName());
        }
    }
}