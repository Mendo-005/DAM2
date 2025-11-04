package es.ciudadescolar.clases;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pedidos {

    @JsonProperty("id_pedido")
    private String id_pedido;
    
    @JsonProperty("id_cliente")
    private String id_cliente;
    
    @JsonProperty("fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonProperty("estado")
    private String estado;
    
    @JsonProperty("articulos")
    private List<Map<String, Object>> articulos;

    public Pedidos() {}

    public String getId_pedido() { return id_pedido; }
    public void setId_pedido(String id_pedido) { this.id_pedido = id_pedido; }

    public String getId_cliente() { return id_cliente; }
    public void setId_cliente(String id_cliente) { this.id_cliente = id_cliente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Map<String, Object>> getArticulos() { return articulos; }
    public void setArticulos(List<Map<String, Object>> articulos) { this.articulos = articulos; }
}

