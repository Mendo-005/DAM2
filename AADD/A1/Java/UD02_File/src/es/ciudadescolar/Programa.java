package es.ciudadescolar;

import java.io.File;

public class Programa 
{

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception 
    {   
        // Crea un fichero
        File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "fichero.txt");

        if (fichero.exists()) 
        {   
            fichero.delete();
             try 
            {
                fichero.createNewFile();
            } catch (Exception e) 
            {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }   
        } else
        {
            try 
            {
                fichero.createNewFile();
            } catch (Exception e) 
            {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }
        }
        
        // Crea un directorio
        File directorio = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "carpeta");

        if (directorio.exists()) 
        {   
            directorio.delete();
             try 
            {
                directorio.mkdir();
            } catch (Exception e) 
            {
                System.err.println("No se ha podido crear el directorio " + directorio.getName());
                System.exit(1);
            }   
        } else
        {
            try 
            {
                directorio.mkdir();
            } catch (Exception e) 
            {
                System.err.println("No se ha podido crear el directorio " + directorio.getName());
                System.exit(1);
            }
        }
        
        // Evalua que sea un dir y que tenga contenido
        if (directorio.isDirectory() && directorio.list() != null)
        {
            System.out.println("El contenido del directorio: " + directorio.getName() + " es: ");
            for (String item:directorio.list()) {
                System.out.println(item);
                
            }
        }
        
        // Evalua que sea un dir y que tenga contenido
        if (directorio.isDirectory() && directorio.list() != null)
        {
            System.out.println("El contenido del directorio: " + directorio.getName() + " es: ");
            for (String item:directorio.list()) {
                System.out.println("Borrando ficheros");
                File fileToDelete = new File(directorio, item);
                fileToDelete.delete();
                
            }
        } else 
        {
            // Borrar el directorio (Debe estar vacío)
            if (!directorio.delete()) 
            {
                System.out.println("No se ha podido borrar el directorio" + directorio.getName());
            } else
            {
                System.out.println("Se ha borado el directorio");
            }
        }

        
    }
}
