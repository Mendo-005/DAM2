package es.ciudadescolar;

import es.ciudadescolar.util.Alumno;
import es.ciudadescolar.util.XmlManager;
import java.io.File;
import java.util.List;

public class Programa {
    public static void main(String[] args) {
        //XmlManager.procesarXmlAlumnos(new File("alumnos.xml"));
        List<Alumno> alumnoRecuperados = XmlManager.procesarXmlAlumnos(new File("alumnos.xml"));

        if (alumnoRecuperados.isEmpty())
        {
            System.out.println("No se han recuperado alumnos del xml");
            System.exit(1);
        }
        for (Alumno al:alumnoRecuperados)
        {
            System.out.println(al);
        }
    }
}
