package es.ciudadescolar;

import java.io.File;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 8c957d4029d7871f434f38bc0844e01c3a694ed7

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
=======
import es.ciudadescolar.instituto.Alumno;
>>>>>>> 8c957d4029d7871f434f38bc0844e01c3a694ed7
import es.ciudadescolar.util.JsonManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
<<<<<<< HEAD

        JsonManager.parsearJsonAlumnos(new File("alumnos.json"));
=======
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
>>>>>>> 8c957d4029d7871f434f38bc0844e01c3a694ed7
    }
}