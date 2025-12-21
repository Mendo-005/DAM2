package mendo.dev.clases;

import java.time.LocalDate;

public class Comic 
{
    private String titulo;
    private Integer numero;
    private Double precio;
    private Integer cod_editorial;
    private LocalDate fecha_publi;
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getCod_editorial() {
        return cod_editorial;
    }
    public void setCod_editorial(Integer cod_editorial) {
        this.cod_editorial = cod_editorial;
    }
    public LocalDate getFecha_publi() {
        return fecha_publi;
    }
    public void setFecha_publi(LocalDate fecha_publi) {
        this.fecha_publi = fecha_publi;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((precio == null) ? 0 : precio.hashCode());
        result = prime * result + ((cod_editorial == null) ? 0 : cod_editorial.hashCode());
        result = prime * result + ((fecha_publi == null) ? 0 : fecha_publi.hashCode());
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
        Comic other = (Comic) obj;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (precio == null) {
            if (other.precio != null)
                return false;
        } else if (!precio.equals(other.precio))
            return false;
        if (cod_editorial == null) {
            if (other.cod_editorial != null)
                return false;
        } else if (!cod_editorial.equals(other.cod_editorial))
            return false;
        if (fecha_publi == null) {
            if (other.fecha_publi != null)
                return false;
        } else if (!fecha_publi.equals(other.fecha_publi))
            return false;
        return true;
    }
    
    public Comic() {
    }
    public Comic(String titulo, Integer numero, Double precio, Integer cod_editorial, LocalDate fecha_publi) {
        this.titulo = titulo;
        this.numero = numero;
        this.precio = precio;
        this.cod_editorial = cod_editorial;
        this.fecha_publi = fecha_publi;
    }
    
    @Override
    public String toString() {
        return  "Comic: " + titulo + " | " + numero + " | " + precio + " | "
                + cod_editorial + " | " + fecha_publi ;
    }
}