package es.ciudadescolar.clases;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pedidos {

    private String id_pedido;
    private String id_cliente;
    
    @JsonProperty("fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Integer cantidad_comprada;

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad_comprada() {
        return cantidad_comprada;
    }

    public void setCantidad_comprada(Integer cantidad_comprada) {
        this.cantidad_comprada = cantidad_comprada;
    }

    public Pedidos(String id_pedido, String id_cliente, LocalDate fecha, Integer cantidad_comprada) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.cantidad_comprada = cantidad_comprada;
    }

    @Override
    public String toString() {
        return "id_pedido:" + id_pedido + ", id_cliente:" + id_cliente + ", fecha:" + fecha
                + ", cantidad_comprada:" + cantidad_comprada;
    }    
}
