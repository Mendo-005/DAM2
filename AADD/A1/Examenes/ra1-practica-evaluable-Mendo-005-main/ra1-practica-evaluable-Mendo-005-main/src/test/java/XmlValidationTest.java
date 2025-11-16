import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.xpath.JAXPXPathEngine;

public class XmlValidationTest extends TestBase 
{
     
    @Test
    void testXmlValidoYCorrecto() throws Exception {
        
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".xml"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero XML de salida");

        File xml = candidatos[0];
                
        File xsd = new File("src/test/resources/hospital_2526.xsd");

        // ✅ Primera parte: validar contra el XSD
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        assertDoesNotThrow(() ->
                schemaFactory.newSchema(xsd).newValidator().validate(new StreamSource(xml)),
                "El XML generado NO es válido según el XSD"
        );

        // ✅ Segunda parte: comparar el contenido real vs esperado
        Path esperado = Path.of("src/test/resources/hospital_Zaragoza_jose_sala.xml");
        String xmlEsperado = Files.readString(esperado);
        String xmlGenerado = Files.readString(xml.toPath());

        Diff diferencias = DiffBuilder.compare(xmlEsperado)
                .withTest(xmlGenerado)
                .ignoreWhitespace()
                .normalizeWhitespace()
                .checkForSimilar() // ✅ Permite equivalencia si el contenido es igual, aunque el orden varíe
                .build();

        assertFalse(diferencias.hasDifferences(),
                "El XML es válido, pero su contenido NO es el esperado: " + diferencias.toString());
    }

    /**
     * Test ciudad del XML de salida Zaragoza
     * @throws Exception
     */
    @Test
    void testCiudadZaragoza() throws Exception {
        
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".xml"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero XML de salida");

        String xml = Files.readString(candidatos[0].toPath());
        JAXPXPathEngine xpath = new JAXPXPathEngine();

        String valor = xpath.evaluate("/Hospital/ciudad", Input.fromString(xml).build());

        assertEquals("Zaragoza", valor, "El elemento <ciudad> no contiene el valor esperado");
    }

    /**
     * Test XML de salida se valida contra el XSD proporcionado
     * @throws Exception
     */
    @Test
    void testAtributoXsd() throws Exception {
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".xml"));

        assertNotNull(candidatos, "Error al leer la carpeta");
        assertTrue(candidatos.length > 0, "No se encontró ningún fichero XML de salida");

        File xmlFile = candidatos[0];
        String xml = Files.readString(xmlFile.toPath());

        // Registrar el namespace xsi en XPath
        Map<String, String> ns = new HashMap<>();
        ns.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");

        JAXPXPathEngine xpath = new JAXPXPathEngine();
        xpath.setNamespaceContext(ns);

        // Consultar el atributo xsi:noNamespaceSchemaLocation del nodo raíz
        String valor = xpath.evaluate("/*/@xsi:noNamespaceSchemaLocation",
                Input.fromString(xml).build()
        );

        assertEquals("hospital_2526.xsd", valor,
                "El atributo xsi:noNamespaceSchemaLocation del elemento raíz no es el esperado");
    }

    /**
     * Test XML de salida notación
     * @throws Exception
     */
    @Test
    void testExisteXml() {
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_") && name.endsWith(".xml"));


        assertNotNull(candidatos);
        assertNotNull(Arrays.stream(candidatos).findFirst().orElse(null),
                "No se encontró ningún fichero XML en el directorio con la notación solicitada");
    }

}
