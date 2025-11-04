package es.ciudadescolar.clases;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Estadisticas {
    
    @JsonProperty("unidades_totales_vendidas")
    private Integer unidades_totales_vendidas;
    
    @JsonProperty("ingresos_totales")
    private Double ingresos_totales;
    
    @JsonProperty("precio_promedio_venta")
    private Double precio_promedio_venta;
    
    public Estadisticas() {}
    
    public Estadisticas(Integer unidades_totales_vendidas, Double ingresos_totales, Double precio_promedio_venta) {
        this.unidades_totales_vendidas = unidades_totales_vendidas;
        this.ingresos_totales = ingresos_totales;
        this.precio_promedio_venta = precio_promedio_venta;
    }
    
    public Integer getUnidades_totales_vendidas() { return unidades_totales_vendidas; }
    public Double getIngresos_totales() { return ingresos_totales; }
    public Double getPrecio_promedio_venta() { return precio_promedio_venta; }
}