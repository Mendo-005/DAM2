package es.ciudadescolar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.hospital.Medico;

public class TextManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(TextManager.class);

    public static List<Medico> leerMedicosDesdeTexto(File archivoTexto) {
        List<Medico> nuevosMedicos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivoTexto))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                // Saltar la cabecera
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                
                String[] campos = linea.split("\\|");
                if (campos.length == 6) {
                    Medico medico = new Medico();
                    medico.setId(campos[0].trim());
                    medico.setEspecialidad(campos[1].trim());
                    medico.setNombre(campos[2].trim());
                    medico.setApellido(campos[3].trim());
                    // Campos 4 y 5 (teléfono y email) se ignoran ya que la clase Medico no los tiene
                    
                    nuevosMedicos.add(medico);
                    LOG.debug("Médico leído: {}", medico);
                } else {
                    LOG.warn("Línea con formato incorrecto: {}", linea);
                }
            }
            
            LOG.info("Se leyeron {} médicos del archivo de texto", nuevosMedicos.size());
            
        } catch (IOException e) {
            LOG.error("Error al leer el archivo de texto: {}", archivoTexto.getAbsolutePath(), e);
        }
        
        return nuevosMedicos;
    }
}
