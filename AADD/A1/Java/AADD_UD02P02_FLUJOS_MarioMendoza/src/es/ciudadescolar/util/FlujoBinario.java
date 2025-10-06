package es.ciudadescolar.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import es.ciudadescolar.clases.Modulos;

public class FlujoBinario 
{
    public static void escrituraModulos (Modulos[] modulos, String fichaModulos)
	{
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		
		try 
		{
			fos = new FileOutputStream(new File(fichaModulos));
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			
			for (Modulos mod:modulos)
			{
				oos.writeObject(mod);
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Error durante la escritura de objetos en fichero: "+ e.getMessage());
		} catch (IOException e) 
		{
			System.out.println("Error durante la escritura de objetos en fichero: "+ e.getMessage());
		}
		finally
		{
			try 
			{
				oos.flush();
				oos.close();
			} catch (IOException e) 
			{
				System.out.println("Error en el cierre de fichero binario con objetos: "+ e.getMessage());

			}
			
		}
		
	}

     /**
     * Lee los objetos serializados en un fichero binario mostrandolos por pantalla (Salida estandar)
     * @param rutaFichero
     */
     public static void lecturaModulos(String rutaFichero)
     {
        FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try 
		{
			fis = new FileInputStream(new File(rutaFichero));
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			while(true)
			{
			
				Modulos mod = (Modulos) ois.readObject();
				System.out.println(mod);
			}
		} 
		catch (EOFException e)
		{
			// excepción controlada. Fin de fichero.
            System.out.println("Lectura de fichero finalizada");

		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("Error durante la lectura de objetos: "+ e.getMessage());
		} 
		catch (IOException e) 
		{
			System.out.println("Error durante la lectura de objetos: "+ e.getMessage());

		}
		finally
		{
			try 
            {
				ois.close();
			} 
            catch (IOException e) 
			{
				System.out.println("Error en el cierre de fichero binario con objetos"+ e.getMessage());
			}
		}
		
	}

}
