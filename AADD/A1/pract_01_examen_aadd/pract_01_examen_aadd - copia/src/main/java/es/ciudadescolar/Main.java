package es.ciudadescolar;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import es.ciudadescolar.clases.InformeSalida;
import es.ciudadescolar.clases.Pedidos;
import es.ciudadescolar.utils.JsonManager;
import es.ciudadescolar.utils.XMLManager;

public class Main {
    
    public static void main(String[] args) {
        
        // Cargar datos
        List<Pedidos> pedidos = JsonManager.parseJsonPedidos(new File("pedidos.json"));
        @SuppressWarnings("unchecked")
        Map<String, Map<String, String>> catalogoProductos = 
            (Map<String, Map<String, String>>) XMLManager.parseCatalogoProductos(new File("catalogo_productos.xml"));
        
        // Solicitar producto
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID del producto: ");
        String idProducto = scanner.nextLine().trim().toUpperCase();
        
        // Generar y guardar informe
        InformeSalida informe = JsonManager.generarInformeProducto(idProducto, pedidos, catalogoProductos);
        
        if (informe != null) {
            String nombreArchivo = "informe_" + idProducto + ".json";
            JsonManager.escribirInforme(informe, new File(nombreArchivo));
            System.out.println("Informe generado: " + nombreArchivo);
        } else {
            System.out.println("Error: Producto no encontrado.");
        }
        
        scanner.close();
    }
}