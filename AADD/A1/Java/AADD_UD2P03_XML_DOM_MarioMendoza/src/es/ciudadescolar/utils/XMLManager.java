package es.ciudadescolar.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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



}
