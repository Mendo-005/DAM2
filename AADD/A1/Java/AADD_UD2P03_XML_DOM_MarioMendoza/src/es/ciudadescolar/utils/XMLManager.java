package es.ciudadescolar.utils;

import es.ciudadescolar.clases.Noticia;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLManager {

/*
 * 1º Descargamos el XML de internet y lo mandamos a un fichero para parsear 
 */
    public static boolean downloadFileFromInternet(String urlString, String fichName) throws MalformedURLException, URISyntaxException 
        { 
          URL url = new URI(urlString).toURL(); 
          BufferedInputStream in =null; 
          FileOutputStream out= null; 
          try  
          { 
           in = new BufferedInputStream(url.openStream()); 
           out = new FileOutputStream(fichName); 

           byte dataBuffer[] = new byte[1024]; 
           int bytesRead; 
           while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1)  
           { 
            out.write(dataBuffer, 0, bytesRead); 
           } 
          } catch (IOException e) {  return false; } 
          finally 
          { 
           try 
           { 
            if(in != null) 
             in.close(); 
            if(out != null) 
             out.close(); 
           } 
           catch(IOException e)  
        {System.err.println("error de E/S durante el cierre de los flujos"); return false;} 
          } 
          return true; 
    }

    /*
     *  2º Parseamos el XML y lo convertimos en objetos Noticia
     */
    public static List<Noticia> parseXMLFile(String fichName) 
    {
      List<Noticia> listaNoticias = new ArrayList<Noticia> ();

      // Variable para ir creando cada noticia
      Noticia noticia = null;

      // Parseamos el XML
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      try 
      {
        // Creamos el parser
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Parseamos el fichero y obtenemos el documento
        Document documento = db.parse(fichName);
        // Obtenemos el elemento raíz
        Element elementoRaiz = documento.getDocumentElement(); 
        // Obtenemos la lista de nodos "item" (las noticias están dentro de channel/item)
        NodeList listaNodosNoticias = elementoRaiz.getElementsByTagName("item");
        // Recorremos la lista de nodos "item"
        for (int i = 0; i < listaNodosNoticias.getLength(); i++)
        {
          Node nodoNoticia = listaNodosNoticias.item(i); // obtenemos el nodo "item" i-ésimo

          if (nodoNoticia.getNodeType() == Node.ELEMENT_NODE) // si es un nodo elemento
          {

            Element elementoNoticia = (Element) nodoNoticia; // casteamos el nodo a elemento

            // Obtenemos los elementos hijos del nodo noticia con verificaciones de seguridad
            String title = "";
            String creator = "";
            String description = "";
            String date = "";
            
            NodeList titleNodes = elementoNoticia.getElementsByTagName("title");
            if (titleNodes.getLength() > 0) {
                title = titleNodes.item(0).getTextContent();
            }
            
            NodeList creatorNodes = elementoNoticia.getElementsByTagName("dc:creator");
            if (creatorNodes.getLength() > 0) {
                creator = creatorNodes.item(0).getTextContent();
            }
            
            NodeList descriptionNodes = elementoNoticia.getElementsByTagName("description");
            if (descriptionNodes.getLength() > 0) {
                description = descriptionNodes.item(0).getTextContent();
            }

            NodeList dateNodes = elementoNoticia.getElementsByTagName("pubDate");
            if (dateNodes.getLength() > 0) {
                date = dateNodes.item(0).getTextContent();
              
            }


            // Creamos la noticia y establecemos sus propiedades
            noticia = new Noticia();
            noticia.setSecuencial(i + 1); // Asignamos un secuencial basado en el índice
            noticia.setTitle(title);
            noticia.setCreator(creator);
            noticia.setDescription(description);
            listaNoticias.add(noticia);
          }
        }
          
      } catch (Exception e) 
      {
        e.printStackTrace();
        System.err.println("Error: No se ha podido parsear el fichero XML");
        return listaNoticias;
      }

      return listaNoticias;   
    }

}
