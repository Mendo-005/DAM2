package es.ciudadescolar.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.clases.Estadisticas;
import es.ciudadescolar.clases.InformeSalida;
import es.ciudadescolar.clases.Pedidos;

public class JsonManager {

    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static List<Pedidos> parseJsonPedidos(File ficheroJson) {
        List<Pedidos> listaPedidos = new ArrayList<>();
        
        if (!ficheroJson.canRead()) {
            LOG.warn("El fichero no se puede leer: " + ficheroJson.getAbsolutePath());
            return listaPedidos;
        }

        try {
            var jsonData = mapper.readValue(ficheroJson, Map.class);
            var pedidosArray = (List<Map<String, Object>>) jsonData.get("pedidos");
            
            for (Map<String, Object> pedidoData : pedidosArray) {
                Pedidos pedido = mapper.convertValue(pedidoData, Pedidos.class);
                listaPedidos.add(pedido);
            }
        } catch (IOException e) {
            LOG.error("Error en el parseo: " + ficheroJson.getAbsolutePath(), e);
        }

        LOG.info("Se han cargado {} pedidos desde {}", listaPedidos.size(), ficheroJson.getName());
        return listaPedidos;
    }

    public static InformeSalida generarInformeProducto(String idProducto, List<Pedidos> pedidos, 
                                                       Map<String, Map<String, String>> catalogoProductos) {
        
        LOG.info("Generando informe para el producto: {}", idProducto);
        
        Map<String, String> infoProducto = catalogoProductos.get(idProducto);
        if (infoProducto == null) {
            LOG.warn("Producto {} no encontrado en el catálogo", idProducto);
            return null;
        }
        
        String nombreProducto = infoProducto.get("nombre");
        String categoria = infoProducto.get("categoria");
        
        List<Map<String, Object>> pedidosCompletados = new ArrayList<>();
        List<Map<String, Object>> pedidosPendientes = new ArrayList<>();
        
        int unidadesTotales = 0;
        double ingresosTotales = 0.0;
        double sumaPreciosVenta = 0.0;
        int numeroVentas = 0;
        
        for (Pedidos pedido : pedidos) {
            boolean contieneProduco = false;
            int cantidadProductoEnPedido = 0;
            
            for (Map<String, Object> articulo : pedido.getArticulos()) {
                String id = (String) articulo.get("id_producto");
                if (idProducto.equals(id)) {
                    contieneProduco = true;
                    int cantidad = ((Number) articulo.get("cantidad")).intValue();
                    double precio = ((Number) articulo.get("precio_unitario")).doubleValue();
                    
                    cantidadProductoEnPedido += cantidad;
                    unidadesTotales += cantidad;
                    ingresosTotales += cantidad * precio;
                    sumaPreciosVenta += precio;
                    numeroVentas++;
                }
            }
            
            if (contieneProduco) {
                Map<String, Object> pedidoSimple = Map.of(
                    "id_pedido", pedido.getId_pedido(),
                    "id_cliente", pedido.getId_cliente(),
                    "fecha", pedido.getFecha().toString(),
                    "cantidad_comprada", cantidadProductoEnPedido
                );
                
                if ("ENTREGADO".equals(pedido.getEstado())) {
                    pedidosCompletados.add(pedidoSimple);
                } else {
                    pedidosPendientes.add(pedidoSimple);
                }
            }
        }
        
        double precioPromedioVenta = numeroVentas > 0 ? (sumaPreciosVenta / numeroVentas) : 0.0;
        
        Estadisticas estadisticas = new Estadisticas(unidadesTotales, ingresosTotales, precioPromedioVenta);
        InformeSalida.DatosProducto datosProducto = new InformeSalida.DatosProducto(
            idProducto, nombreProducto, categoria, estadisticas, pedidosCompletados, pedidosPendientes);
        
        LOG.info("Informe generado: {} unidades vendidas, {} euros de ingresos", unidadesTotales, ingresosTotales);
        
        return new InformeSalida(datosProducto);
    }
    
    public static void escribirInforme(InformeSalida informe, File archivoSalida) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoSalida, informe);
            LOG.info("Informe guardado en: {}", archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            LOG.error("Error al escribir el informe: {}", archivoSalida.getAbsolutePath(), e);
        }
    }
}