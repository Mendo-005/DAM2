package es.ciudadescolar;

import java.io.File;

/**
 * Programa de demostración para el manejo de archivos y directorios en Java
 * Utiliza la clase File para crear, mover y eliminar archivos y directorios
 * 
 * @author ciudadescolar
 * @version 1.0
 */
public class Programa {

    /**
     * Método principal que ejecuta las operaciones sobre archivos y directorios
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     * @throws Exception si ocurre algún error durante la ejecución
     */
    public static void main(String[] args) throws Exception 
    {

        try {
            if (args.length != 1) 
            {
                System.err.println("Se esperaba un parametro con el nombre del fichero");
                System.out.println("Ejemplo de uso: java Program fichero.txt");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error al validar argumentos: " + e.getMessage());
            System.exit(1);
        }

        String nombreFichero = null;
        nombreFichero = args[0];

        // ==== PASO 1: CREACIÓN DEL FICHERO ====
        // Crea un objeto File con la ruta del fichero en el directorio actual
        File fichero = new File(System.getProperty("user.dir") + 
                               System.getProperty("file.separator") + nombreFichero );
        
        System.out.println("=== INFORMACIÓN DEL FICHERO ===");
        System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());

        // Mostrar las propiedades de el fichero
        String permisosFichero = new String();
        if (fichero.canRead()) {
            permisosFichero = permisosFichero + "R";
            System.out.println("El fichero tiene permisos de lectura");
        } else {
            System.out.println("El fichero no tiene permisos de lectura");
        }
        
        if (fichero.canWrite()) {
            permisosFichero = permisosFichero + "W";
            System.out.println("El fichero tiene permisos de escritura");
        } else {
            System.out.println("El fichero no tiene permisos de escritura");
        }
        
        if (fichero.canExecute()) {
            permisosFichero = permisosFichero + "X";
            System.out.println("El fichero tiene permisos de ejecución");
        } else {
            System.out.println("El fichero no tiene permisos de ejecución");
        }
        
        System.out.println("Permisos del fichero: " + permisosFichero);

        // Verificar si el fichero existe y recrearlo
        if (fichero.exists()) {   
            fichero.delete();
            try {
                fichero.createNewFile();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }   
        } else {
            try {
                fichero.createNewFile();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el fichero " + fichero.getName());
                System.exit(1);
            }
        }
        
        // ==== PASO 2: CREACIÓN DEL DIRECTORIO ====
        // Crea un directorio
        File directorio = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "carpeta");

        if (directorio.exists()) {   
            directorio.delete();
            try {
                directorio.mkdir();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el directorio " + directorio.getName());
                System.exit(1);
            }   
        } else {
            try {
                directorio.mkdir();
            } catch (Exception e) {
                System.err.println("No se ha podido crear el directorio " + directorio.getName());
                System.exit(1);
            }
        }
        
        // ==== PASO 3: MOVER EL FICHERO AL DIRECTORIO ====
        // Mover el fichero de carpeta
        File ficheroMovido = new File(directorio, fichero.getName());
        if (fichero.renameTo(ficheroMovido)) {
            System.out.println("Se ha movido el fichero " + fichero.getName() + " a: " + ficheroMovido.getAbsolutePath());
        }

        // ==== PASO 4: LISTAR Y LIMPIAR CONTENIDO ====
        // Evalúa que sea un directorio y que tenga contenido
        if (directorio.isDirectory() && directorio.list() != null) {
            System.out.println("El contenido del directorio: " + directorio.getName() + " es: ");
            for (String item : directorio.list()) {
                System.out.println("  - " + item);
            }
            
            // Borrar todos los ficheros del directorio
            for (String item : directorio.list()) {
                System.out.println("Borrando fichero: " + item);
                File fileToDelete = new File(directorio, item);
                fileToDelete.delete();
            }
            
            // Borrar el directorio (ahora vacío)
            if (directorio.delete()) {
                System.out.println("Se ha borrado el directorio: " + directorio.getName());
            } else {
                System.out.println("No se ha podido borrar el directorio: " + directorio.getName());
            }
        } else {
            // Borrar el directorio (debe estar vacío)
            if (!directorio.delete()) {
                System.out.println("No se ha podido borrar el directorio: " + directorio.getName());
            } else {
                System.out.println("Se ha borrado el directorio: " + directorio.getName());
            }
        }

        
    }
}
