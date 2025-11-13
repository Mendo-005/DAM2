package es.ciudadescolar.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.ciudadescolar.clases.Medico;

class XmlManagerTest {

    @Test
    void testParseXmlCompleta() {
        // Arrange
        File ficheroXmlCompleto = new File("hospital.xml");
        
        System.out.println("Ruta del archivo: " + ficheroXmlCompleto.getAbsolutePath());
        System.out.println("¿Existe el archivo? " + ficheroXmlCompleto.exists());
        System.out.println("¿Se puede leer? " + ficheroXmlCompleto.canRead());
        
        // Act
        @SuppressWarnings("unchecked")
        List<Medico> medicos = (List<Medico>) XmlManager.parseXmlCompleta(ficheroXmlCompleto);
        
        // Assert
        assertNotNull(medicos, "La lista de médicos no debería ser null");
        System.out.println("Médicos parseados: " + medicos.size());
        assertEquals(2, medicos.size(), "Deberían haberse parseado 2 médicos");
        
        // Verificar primer médico
        Medico medico1 = medicos.get(0);
        assertEquals("M01", medico1.getId());
        assertEquals("mañana", medico1.getTurno());
        assertEquals("Cardiología", medico1.getEspecialidad());
        assertEquals("Carlos", medico1.getNombre());
        assertEquals("Fernández García", medico1.getApellido());
        assertEquals("911234567", medico1.getTelefono());
        assertEquals("carlos.fernandez@hospital.org", medico1.getEmail());
        
        // Verificar segundo médico
        Medico medico2 = medicos.get(1);
        assertEquals("M02", medico2.getId());
        assertEquals("tarde", medico2.getTurno());
        assertEquals("Pediatría", medico2.getEspecialidad());
        assertEquals("Ana", medico2.getNombre());
        assertEquals("Martínez Ruiz", medico2.getApellido());
        assertEquals("922345678", medico2.getTelefono());
        assertEquals("ana.martinez@hospital.org", medico2.getEmail());
        
        // Imprimir resultados para inspección visual
        System.out.println("\n=== MÉDICOS PARSEADOS ===");
        medicos.forEach(medico -> System.out.println(medico));
    }
}
