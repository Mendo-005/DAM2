package es.ciudadescolar;

import java.io.File;

/**
 * Clase que contiene métodos estáticos para el manejo de archivos y directorios
 * Proporciona funcionalidades para recorrer y borrar directorios de forma recursiva
 * 
 * @author MarioMendoza
 * @version 1.0
 */
public class FicherosClase {

    /**
     * Recorre recursivamente un directorio y muestra información de todos los archivos y subdirectorios
     * Formato de salida: 
     * - Para directorios: D|ruta_absoluta|R|W|H
     * - Para archivos: F|ruta_absoluta|R|W|H 
     * Donde R=readable, W=writable, H=hidden
     * 
     * @param ruta la ruta del directorio a recorrer
     */
    public static void recorrerDirectorio(String ruta) {
        try {
            File directorio = new File(ruta);
            
            // Verificar que el directorio existe y es válido
            if (!directorio.exists()) {
                System.err.println("Error: La ruta no existe: " + ruta);
                return;
            }
            
            if (!directorio.isDirectory()) {
                System.err.println("Error: La ruta no es un directorio: " + ruta);
                return;
            }
            
            // Obtener la lista de archivos y subdirectorios
            File[] contenido = directorio.listFiles();
            
            if (contenido == null) {
                System.err.println("Error: No se pueden leer los contenidos del directorio (permisos insuficientes)");
                return;
            }
            
            // Recorrer cada elemento del directorio
            for (File elemento : contenido) {
                mostrarInformacionElemento(elemento);
                
                // Si es un directorio, llamada recursiva
                if (elemento.isDirectory()) {
                    recorrerDirectorio(elemento.getAbsolutePath());
                }
            }
            
        } catch (SecurityException e) {
            System.err.println("Error de permisos al acceder a: " + ruta + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar el directorio: " + ruta + " - " + e.getMessage());
        }
    }
    
    /**
     * Muestra la información de un elemento (archivo o directorio) en el formato especificado
     * 
     * @param elemento el archivo o directorio a procesar
     */
    private static void mostrarInformacionElemento(File elemento) {
        StringBuilder info = new StringBuilder();
        
        // Determinar si es directorio o archivo
        if (elemento.isDirectory()) {
            info.append("D");
        } else {
            info.append("F");
        }
        
        // Añadir la ruta absoluta
        info.append("|").append(elemento.getAbsolutePath());
        
        // Verificar permisos de lectura
        if (elemento.canRead()) {
            info.append("|R");
        } else {
            info.append("|");
        }
        
        // Verificar permisos de escritura
        if (elemento.canWrite()) {
            info.append("|W");
        } else {
            info.append("|");
        }
        
        // Verificar si está oculto
        if (elemento.isHidden()) {
            info.append("|H");
        } else {
            info.append("|");
        }
        
        System.out.println(info.toString());
    }
    
    /**
     * Borra recursivamente un archivo o directorio
     * Si es un directorio, borra primero todo su contenido antes de borrarlo
     * 
     * @param ruta la ruta del archivo o directorio a borrar
     * @return true si se borró exitosamente, false en caso contrario
     */
    public static boolean borrar(String ruta) {
        try {
            File elemento = new File(ruta);
            
            // Verificar que el elemento existe
            if (!elemento.exists()) {
                System.err.println("Error: El elemento no existe: " + ruta);
                return false;
            }
            
            return borrarRecursivo(elemento);
            
        } catch (SecurityException e) {
            System.err.println("Error de permisos al borrar: " + ruta + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al borrar: " + ruta + " - " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Método auxiliar que realiza el borrado recursivo
     * 
     * @param elemento el elemento a borrar
     * @return true si se borró exitosamente, false en caso contrario
     */
    private static boolean borrarRecursivo(File elemento) {
        try {
            // Si es un directorio, borrar primero todo su contenido
            if (elemento.isDirectory()) {
                File[] contenido = elemento.listFiles();
                
                // Si no se pueden leer los contenidos, intentar borrar directamente
                if (contenido == null) {
                    System.err.println("Advertencia: No se puede acceder al contenido de: " + elemento.getAbsolutePath());
                    return elemento.delete();
                }
                
                // Borrar recursivamente cada elemento del directorio
                for (File hijo : contenido) {
                    if (!borrarRecursivo(hijo)) {
                        System.err.println("Error: No se pudo borrar: " + hijo.getAbsolutePath());
                        return false;
                    }
                }
            }
            
            // Borrar el elemento (archivo o directorio ya vacío)
            boolean resultado = elemento.delete();
            
            if (resultado) {
                System.out.println("Borrado: " + elemento.getAbsolutePath());
            } else {
                System.err.println("Error: No se pudo borrar: " + elemento.getAbsolutePath());
            }
            
            return resultado;
            
        } catch (SecurityException e) {
            System.err.println("Error de permisos al borrar: " + elemento.getAbsolutePath() + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al borrar: " + elemento.getAbsolutePath() + " - " + e.getMessage());
            return false;
        }
    }
}