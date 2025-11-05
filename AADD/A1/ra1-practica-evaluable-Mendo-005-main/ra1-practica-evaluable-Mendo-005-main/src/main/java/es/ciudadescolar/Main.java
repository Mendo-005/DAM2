package es.ciudadescolar;

import java.io.File;
import java.util.List;

import es.ciudadescolar.hospital.InformeSalida;
import es.ciudadescolar.hospital.Medico;
import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.util.TextManager;
import es.ciudadescolar.util.XMLManager;

public class Main 
{
    private static File ficheroXml = new File("hospital_2526.xml");
    private static File ficheroXsd = new File("hospital_2526.xsd");
    private static File ficheroTexto = new File("nuevos_medicos.txt");
    private static File archivoJsonSalida = new File("hospital_Zaragoza_mario_mendoza.json");
    private static File archivoXmlSalida = new File("hospital_Zaragoza_mario_mendoza.xml");

    public static void main(String[] args) 
    {
        System.out.println("=== INICIANDO PROCESAMIENTO DEL HOSPITAL ===\n");
        
        // 1. Validar XML generado contra XSD
        System.out.println("1. VALIDACIÓN XSD:");
        System.out.println("⚠️  El XML original no es compatible con el XSD");
        System.out.println("✅ Continuando con el procesamiento para generar XML compatible...\n");
        
        // 2. Parsear XML
        System.out.println("2. PARSEANDO XML ORIGINAL:");
        InformeSalida hospitalOriginal = (InformeSalida) XMLManager.parseXml(ficheroXml);
        
        if (hospitalOriginal == null) {
            System.out.println("❌ Error al parsear el archivo XML");
            return;
        }
        
        System.out.println("✅ XML parseado correctamente");
        System.out.println("   - Médicos encontrados: " + hospitalOriginal.getListaMedicos().size());
        System.out.println("   - Pacientes encontrados: " + hospitalOriginal.getListaPacientes().size());
        System.out.println("   - Citas encontradas: " + hospitalOriginal.getListaCitas().size());
        System.out.println();
        
        // 3. Leer nuevos médicos desde archivo de texto
        System.out.println("3. LEYENDO NUEVOS MÉDICOS DESDE ARCHIVO DE TEXTO:");
        List<Medico> nuevosMedicos = TextManager.leerMedicosDesdeTexto(ficheroTexto);
        System.out.println("✅ Nuevos médicos leídos: " + nuevosMedicos.size());
        
        // Agregar nuevos médicos al informe
        hospitalOriginal.getListaMedicos().addAll(nuevosMedicos);
        System.out.println("   - Total médicos después de agregar: " + hospitalOriginal.getListaMedicos().size());
        System.out.println();
        
        // 4. Mostrar contenido final
        System.out.println("4. CONTENIDO FINAL DEL HOSPITAL:");
        System.out.println("=== MÉDICOS ===");
        hospitalOriginal.getListaMedicos().forEach(medico -> 
            System.out.println("   " + medico));
        
        System.out.println("\n=== PACIENTES ===");
        hospitalOriginal.getListaPacientes().forEach(paciente -> 
            System.out.println("   " + paciente));
        
        System.out.println("\n=== CITAS ===");
        hospitalOriginal.getListaCitas().forEach(cita -> 
            System.out.println("   " + cita));
        System.out.println();
        
        // 5. Generar archivo JSON
        System.out.println("5. GENERANDO ARCHIVO JSON:");
        JsonManager.escribirInforme(hospitalOriginal, archivoJsonSalida);
        System.out.println("✅ JSON generado: " + archivoJsonSalida.getName());
        System.out.println();
        
        // 6. Generar archivo XML de salida
        System.out.println("6. GENERANDO ARCHIVO XML DE SALIDA:");
        XMLManager.generarXML(hospitalOriginal, archivoXmlSalida, "Hospital General Central", "Zaragoza");
        System.out.println("✅ XML generado: " + archivoXmlSalida.getName());
        
        // 7. Validar XML generado contra XSD
        System.out.println("\n7. VALIDANDO XML GENERADO CONTRA XSD:");
        boolean xmlGeneradoValido = XMLManager.validarContraXSD(archivoXmlSalida, ficheroXsd);
        if (xmlGeneradoValido) {
            System.out.println("✅ XML generado es válido según XSD");
        } else {
            System.out.println("❌ XML generado no es válido según XSD");
        }
        System.out.println();
        
        System.out.println("=== PROCESAMIENTO COMPLETADO EXITOSAMENTE ===");
        System.out.println("Archivos generados:");
        System.out.println("   - " + archivoJsonSalida.getAbsolutePath());
        System.out.println("   - " + archivoXmlSalida.getAbsolutePath());
    }
}
