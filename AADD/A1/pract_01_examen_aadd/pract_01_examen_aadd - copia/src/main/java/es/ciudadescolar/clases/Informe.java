package es.ciudadescolar.clases;

import java.util.List;

public class Informe {
    
    private String id_producto;
    private String nombre_producto;
    private String categoria;
    
    private List<Estadistica> estadistica;
    private List<Pedidos> pedidos_completados;
    private List<Pedidos> pedidos_pendientes;
    

    public String getId_producto() {
        return id_producto;
    }


    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }


    public String getNombre_producto() {
        return nombre_producto;
    }


    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }


    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public List<Estadistica> getEstadistica() {
        return estadistica;
    }


    public void setEstadistica(List<Estadistica> estadistica) {
        this.estadistica = estadistica;
    }


    public List<Pedidos> getPedidos_completados() {
        return pedidos_completados;
    }


    public void setPedidos_completados(List<Pedidos> pedidos_completados) {
        this.pedidos_completados = pedidos_completados;
    }


    public List<Pedidos> getPedidos_pendientes() {
        return pedidos_pendientes;
    }


    public void setPedidos_pendientes(List<Pedidos> pedidos_pendientes) {
        this.pedidos_pendientes = pedidos_pendientes;
    }

    public Informe(String id_producto, String nombre_producto, String categoria, List<Estadistica> estadistica,
            List<Pedidos> pedidos_completados, List<Pedidos> pedidos_pendientes) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.categoria = categoria;
        this.estadistica = estadistica;
        this.pedidos_completados = pedidos_completados;
        this.pedidos_pendientes = pedidos_pendientes;
    }

    @Override
    public String toString() {
        return "id_producto:" + id_producto + ", nombre_producto:" + nombre_producto + ", categoria:"
                + categoria + ", estadistica:" + estadistica + ", pedidos_completados:" + pedidos_completados
                + ", pedidos_pendientes:" + pedidos_pendientes;
    }

/*
 * Clase Estadistica 
 */
    public class Estadistica {

        private Integer unidades_totales_vendidas;
        private Float ingresos_totales;
        private Float precio_promedio_venta;

        public Integer getUnidades_totales_vendidas() {
            return unidades_totales_vendidas;
        }

        public void setUnidades_totales_vendidas(Integer unidades_totales_vendidas) {
            this.unidades_totales_vendidas = unidades_totales_vendidas;
        }

        public Float getIngresos_totales() {
            return ingresos_totales;
        }

        public void setIngresos_totales(Float ingresos_totales) {
            this.ingresos_totales = ingresos_totales;
        }

        public Float getPrecio_promedio_venta() {
            return precio_promedio_venta;
        }

        public void setPrecio_promedio_venta(Float precio_promedio_venta) {
            this.precio_promedio_venta = precio_promedio_venta;
        }
        
        public Estadistica(Integer unidades_totales_vendidas, Float ingresos_totales, Float precio_promedio_venta) {
            this.unidades_totales_vendidas = unidades_totales_vendidas;
            this.ingresos_totales = ingresos_totales;
            this.precio_promedio_venta = precio_promedio_venta;
        }

        @Override
        public String toString() {
            return "unidades_totales_vendidas:" + unidades_totales_vendidas + ", ingresos_totales:"
                    + ingresos_totales + ", precio_promedio_venta:" + precio_promedio_venta;
        }
        
        
    }

    
}
