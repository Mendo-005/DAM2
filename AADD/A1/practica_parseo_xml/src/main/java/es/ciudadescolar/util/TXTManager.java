package es.ciudadescolar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Medico;

public class TXTManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(TXTManager.class);
    
    public static List<Medico> leerNuevMedicos(File ficheroTxT)
    {
        List<Medico> nuevosMedicos = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(ficheroTxT)))
        {
            String linea; // Linea en la que te encuentras
            boolean primeraLinea = true; // Marcamos si hay cabezera o no 

            while ((linea = br.readLine()) != null) 
            {
                // Saltamos la cabecera (si existe)
                if (primeraLinea) 
                {
                    primeraLinea = false;
                    continue;    
                }

                String[] campos = linea.split("\\|"); // Dividimos la línea en los campos ( | )
                if (campos.length == 6) // Total de campos en la lista
                {
                    // Creamos instancia de Medico
                    Medico medico = new Medico();
                    medico.setId(campos[0].trim()); // Añadimos en orden de la Array
                    medico.setEspecialidad(campos[1].trim()); // Eliminamos los espacios con trim()
                    medico.setNombre(campos[2].trim());
                    medico.setApellido(campos[3].trim());
                    medico.setTelefono(campos[4].trim());
                    medico.setEmail(campos[5].trim());

                    nuevosMedicos.add(medico);
                }
            }

            LOG.info("Se ha parseado el fichero: " + ficheroTxT.getName());

        } catch (IOException e) 
        {
            LOG.warn("No se ha podido parsear el fichero: " + ficheroTxT.getAbsolutePath());
        }
        return nuevosMedicos;
    }
}
