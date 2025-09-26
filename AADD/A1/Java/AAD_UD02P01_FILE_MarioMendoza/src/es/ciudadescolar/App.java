package es.ciudadescolar;

import java.io.File;

/**
 * Clase principal del programa AAD_UD02P01_FILE
 * Permite recorrer recursivamente un directorio y borrar su contenido
 * 
 * @author MarioMendoza
 * @version 1.0
 */
public class App {
    
    /**
     * Método principal que recibe la ruta como parámetro
     * Valida los argumentos y ejecuta las operaciones solicitadas
     * 
     * @param args argumentos de línea de comandos (debe contener una ruta)
     */
    public static void main(String[] args) {
        try {
            // Validar número de parámetros
            if (args.length != 1) {
                System.err.println("Error: Se esperaba exactamente un parámetro con la ruta del directorio");
                System.out.println("Uso: java App <ruta_directorio>");
                System.out.println("Ejemplo: java App C:\\Users\\alumno\\Desktop\\MiCarpeta");
                return;
            }

            String rutaDirectorio = args[0];
            
            // Verificar que la ruta proporcionada existe
            File directorio = new File(rutaDirectorio);
            if (!directorio.exists()) {
                System.err.println("Error: La ruta especificada no existe: " + rutaDirectorio);
                return;
            }
            
            // Verificar que sea un directorio
            if (!directorio.isDirectory()) {
                System.err.println("Error: La ruta especificada no es un directorio: " + rutaDirectorio);
                return;
            }
            
            System.out.println("=== RECORRIDO DEL DIRECTORIO ===");
            System.out.println("Directorio a procesar: " + directorio.getAbsolutePath());
            System.out.println();
            
            // Recorrer el directorio
            FicherosClase.recorrerDirectorio(rutaDirectorio);
            
            System.out.println();
            System.out.println("=== OPERACIÓN DE BORRADO ===");
            System.out.print("¿Desea borrar el directorio y todo su contenido? (s/N): ");
            
            // Para efectos de demostración, comentamos la parte interactiva
            // Scanner scanner = new Scanner(System.in);
            // String respuesta = scanner.nextLine();
            // if (respuesta.toLowerCase().equals("s") || respuesta.toLowerCase().equals("si")) {
            //     FicherosClase.borrar(rutaDirectorio);
            //     System.out.println("Directorio borrado exitosamente.");
            // } else {
            //     System.out.println("Operación de borrado cancelada.");
            // }
            // scanner.close();
            
            System.out.println("Operación completada exitosamente.");
            
        } catch (SecurityException e) {
            System.err.println("Error de permisos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            // e.printStackTrace(); // Comentado para evitar mostrar información sensible en producción
        }
    }
}