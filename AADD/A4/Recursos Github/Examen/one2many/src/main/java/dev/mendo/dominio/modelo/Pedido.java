package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    public static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id_pedido;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double importe;

    @ManyToOne()
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public Long getId_pedido() {
        return id_pedido;
    }


    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public Double getImporte() {
        return importe;
    }


    public void setImporte(Double importe) {
        this.importe = importe;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_pedido == null) ? 0 : id_pedido.hashCode());
        result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
        result = prime * result + ((importe == null) ? 0 : importe.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pedido other = (Pedido) obj;
        if (id_pedido == null) {
            if (other.id_pedido != null)
                return false;
        } else if (!id_pedido.equals(other.id_pedido))
            return false;
        if (fecha == null) {
            if (other.fecha != null)
                return false;
        } else if (!fecha.equals(other.fecha))
            return false;
        if (importe == null) {
            if (other.importe != null)
                return false;
        } else if (!importe.equals(other.importe))
            return false;
        return true;
    }


    public Pedido() {}


    public Pedido(LocalDate fecha, Double importe) {
        this.fecha = fecha;
        this.importe = importe;
    }


    @Override
    public String toString() {
        return "Pedido [fecha=" + fecha + ", importe=" + importe + "]";
    }
    
}
