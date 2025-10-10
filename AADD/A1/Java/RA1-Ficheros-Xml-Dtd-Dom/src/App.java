import es.ciudadescolar.util.Alumno;
import es.ciudadescolar.util.XmlManager;
import java.io.File;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        
        List<Alumno> alumnosRecuperados = XmlManager.procesarXMLAlumnos(new File("alumnos3.xml"), new File("alumnos3.xsd"));

        if (alumnosRecuperados.isEmpty())
        {
            System.out.println("No se han recuperado alumnos del xml");
            System.exit(1);
        }

        for (Alumno al:alumnosRecuperados)
        {
            System.out.println(al);
        }

        // Obtener la lista de edades leyendo el XML mediante getElementsByTagName("edad")
        List<Integer> listaEdades = XmlManager.getListaEdadesAlumnos(new File("alumnos3.xml"), new File("alumnos3.xsd"));

        // Calcular la media a partir de esa lista de edades
        double mediaPorLista = listaEdades.stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0);

        System.out.printf("Media de edad (listaEdades por tag): %.2f%n", mediaPorLista);

        XmlManager.generarXmlAlumnos(alumnosRecuperados, new File("alumnos4.xml"), new File("alumnos4.xsd"));
    }
}
