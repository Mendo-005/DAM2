package es.ciudadescolar.clases;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InformeSalida {
    
    @JsonProperty("informe_producto")
    private DatosProducto informe_producto;
    
    public InformeSalida() {}
    
    public InformeSalida(DatosProducto informe_producto) {
        this.informe_producto = informe_producto;
    }
    
    public DatosProducto getInforme_producto() { return informe_producto; }
    
    public static class DatosProducto {
        @JsonProperty("id_producto")
        private String id_producto;
        
        @JsonProperty("nombre_producto")
        private String nombre_producto;
        
        @JsonProperty("categoria")
        private String categoria;
        
        @JsonProperty("estadisticas")
        private Estadisticas estadisticas;
        
        @JsonProperty("pedidos_completados")
        private List<Map<String, Object>> pedidos_completados;
        
        @JsonProperty("pedidos_pendientes")
        private List<Map<String, Object>> pedidos_pendientes;
        
        public DatosProducto() {}
        
        public DatosProducto(String id_producto, String nombre_producto, String categoria,
                            Estadisticas estadisticas, List<Map<String, Object>> pedidos_completados,
                            List<Map<String, Object>> pedidos_pendientes) {
            this.id_producto = id_producto;
            this.nombre_producto = nombre_producto;
            this.categoria = categoria;
            this.estadisticas = estadisticas;
            this.pedidos_completados = pedidos_completados;
            this.pedidos_pendientes = pedidos_pendientes;
        }
        
        public String getId_producto() { return id_producto; }
        public String getNombre_producto() { return nombre_producto; }
        public String getCategoria() { return categoria; }
        public Estadisticas getEstadisticas() { return estadisticas; }
        public List<Map<String, Object>> getPedidos_completados() { return pedidos_completados; }
        public List<Map<String, Object>> getPedidos_pendientes() { return pedidos_pendientes; }
    }
}