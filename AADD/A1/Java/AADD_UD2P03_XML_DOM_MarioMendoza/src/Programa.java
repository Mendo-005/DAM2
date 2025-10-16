import es.ciudadescolar.utils.XMLManager;
import es.ciudadescolar.clases.Noticia;
import java.util.List;


public class Programa {
    public static void main(String[] args) throws Exception 
    {
        Boolean XMLNoticias = XMLManager.downloadFileFromInternet("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada","XMLNoticias");
        System.out.println("XMLNoticias downloaded: " + XMLNoticias);
        
        if (!XMLNoticias) {
            System.out.println("Failed to download XMLNoticias.");
            System.exit(1);
        }
        
        // Procesamos el XML descargado y obtenemos las noticias
        List<Noticia> noticiasRecuperadas = XMLManager.parseXMLFile("XMLNoticias");

        if (noticiasRecuperadas.isEmpty())
        {
            System.out.println("No se han recuperado noticias del xml");
            System.exit(1);
        }

        for (Noticia noticia : noticiasRecuperadas)
        {
            System.out.println(noticia);
        }

    }
}
