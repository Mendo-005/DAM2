import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonTest extends TestBase
{

    @Test
    void testJsonEquivalente() throws Exception {
        
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".json"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero JSON de salida");

        Path generadoPath = candidatos[0].toPath();
        Path esperadoPath = Path.of("src/test/resources/hospital_Zaragoza_jose_sala.json");

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonGenerado = mapper.readTree(Files.readString(generadoPath));
        JsonNode jsonEsperado = mapper.readTree(Files.readString(esperadoPath));

        /* JsonNode implementa equals() comparando estructura y valores,
        no el orden textual → si cambian las claves de sitio pero el contenido es igual, estará bien todo.
        */
        assertEquals(jsonEsperado, jsonGenerado,
                "El JSON es distinto al esperado (aunque tenga el mismo contenido)");
    }

    /**
     * Valido que se añadan los médicos del fichero de texto
     * @throws Exception
     */
     @Test
    void testNumeroDeMedicos() throws Exception {
        
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".json"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero JSON de salida");

        Path jsonFile = candidatos[0].toPath(); // detecta dinámicamente si quieres
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile.toFile());

        ArrayNode medicos = (ArrayNode) root.get("medicos");

        assertEquals(5, medicos.size(), "El número de médicos no es correcto.");
    }

    /**
     * Valido que aparezca el nodo nombre y el valor sea el esperado "Hospital General Central"
     * @throws Exception
     */
     @Test
    void testNombreHospital() throws Exception {
        
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".json"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero JSON de salida");

        Path jsonFile = candidatos[0].toPath(); // detecta dinámicamente si quieres
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile.toFile());

        String nombreHospital = root.get("nombre").asText();

        assertEquals("Hospital General Central", nombreHospital, "El nombre del hospital no es correcto.");
    }
}