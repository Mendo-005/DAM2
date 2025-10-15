import es.ciudadescolar.utils.XMLManager;


public class Programa {
    public static void main(String[] args) throws Exception 
    {
        Boolean XMLNoticias = XMLManager.downloadFileFromInternet("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada","XMLNoticias");
        System.out.println("XMLNoticias downloaded: " + XMLNoticias);
        

    }
}
