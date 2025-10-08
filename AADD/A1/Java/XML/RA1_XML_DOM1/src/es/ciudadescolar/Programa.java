package es.ciudadescolar;

import es.ciudadescolar.util.Alumno;
import es.ciudadescolar.util.XmlManager;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws IOException {
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
        XmlManager.generarXmlAlumnos((alumnoRecuperados), new File("alumnos2.xml"), new File("alumnos2.xml"));
    
        //XmlManager.devolverXmlExpediente(new File("alumnos.xml"));
        List<Alumno> expedientesRecuperados = XmlManager.devolverXmlExpediente(new File("alumnos.xml"));

        if (alumnoRecuperados.isEmpty())
        {
            System.out.println("No se han recuperado alumnos del xml");
            System.exit(1);
        }
        for (Alumno exp:expedientesRecuperados)
        {
            System.out.println(exp);
        }
        XmlManager.generarXmlAlumnos((expedientesRecuperados), new File("alumnos2.xml"), new File("alumnos2.xml"));
    

    }
}
