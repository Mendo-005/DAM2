package es.ciudadescolar.utils;

import es.ciudadescolar.clases.Noticia;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TextManager {

    /*
     * Filtra las noticias por categoría y las guarda en un fichero de texto
     */
    public static boolean guardarNoticiasPorCategoria(List<Noticia> todasLasNoticias, String categoria) {
        try {
            // Filtrar noticias por categoría
            List<Noticia> noticiasFiltradas = new ArrayList<>();
            for (Noticia noticia : todasLasNoticias) {
                if (noticia.getCategorias() != null) {
                    for (String cat : noticia.getCategorias()) {
                        if (cat.equalsIgnoreCase(categoria)) {
                            noticiasFiltradas.add(noticia);
                            break; // Evitar duplicados si una noticia tiene la misma categoría varias veces
                        }
                    }
                }
            }

            // Generar nombre del fichero con fecha actual
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nombreFichero = "noticias_" + categoria + "_" + fechaActual.format(formatter) + ".txt";

            // Escribir las noticias filtradas al fichero
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero));
            
            writer.write("=== NOTICIAS DE " + categoria.toUpperCase() + " ===\n");
            writer.write("Fecha de generación: " + fechaActual.format(formatter) + "\n");
            writer.write("Total de noticias encontradas: " + noticiasFiltradas.size() + "\n");
            writer.write("========================================\n\n");

            for (Noticia noticia : noticiasFiltradas) {
                writer.write("Noticia #" + noticia.getSecuencial() + "\n");
                writer.write("Título: " + noticia.getTitle() + "\n");
                writer.write("Autor: " + noticia.getCreator() + "\n");
                writer.write("Descripción: " + noticia.getDescription() + "\n");
                writer.write("Categorías: " + String.join(", ", noticia.getCategorias()) + "\n");
                writer.write("----------------------------------------\n\n");
            }

            writer.close();
            
            System.out.println("Se han guardado " + noticiasFiltradas.size() + 
                             " noticias de la categoría '" + categoria + "' en el fichero: " + nombreFichero);
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
            return false;
        }
    }

    /*
     * Guarda todas las noticias en un fichero de texto
     */
    public static boolean guardarTodasLasNoticias(List<Noticia> noticias, String nombreFichero) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero));
            
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            writer.write("=== TODAS LAS NOTICIAS ===\n");
            writer.write("Fecha de generación: " + fechaActual.format(formatter) + "\n");
            writer.write("Total de noticias: " + noticias.size() + "\n");
            writer.write("==========================\n\n");

            for (Noticia noticia : noticias) {
                writer.write("Noticia #" + noticia.getSecuencial() + "\n");
                writer.write("Título: " + noticia.getTitle() + "\n");
                writer.write("Autor: " + noticia.getCreator() + "\n");
                writer.write("Descripción: " + noticia.getDescription() + "\n");
                if (noticia.getCategorias() != null && !noticia.getCategorias().isEmpty()) {
                    writer.write("Categorías: " + String.join(", ", noticia.getCategorias()) + "\n");
                }
                writer.write("----------------------------------------\n\n");
            }

            writer.close();
            
            System.out.println("Se han guardado " + noticias.size() + " noticias en el fichero: " + nombreFichero);
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
            return false;
        }
    }
}