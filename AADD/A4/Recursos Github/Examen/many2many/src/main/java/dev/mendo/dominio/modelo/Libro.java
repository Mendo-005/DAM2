package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libro")
public class Libro implements Serializable
{
    public static final long serialVersionUID  = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_libro;

    @Column(nullable = false)
    private String titulo;
    
    private LocalDate fechaPublicacion;  

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "autor_libro", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores;

    public void addAutor(Autor autor)
    {
        autores.add(autor);
    }

    public Libro() {
        autores = new HashSet<>();
    }

    public Libro(String titulo, LocalDate fechaPublicacion) {
        this();
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_libro() {
        return id_libro;
    }

    public void setId_libro(Long id_libro) {
        this.id_libro = id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_libro == null) ? 0 : id_libro.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((fechaPublicacion == null) ? 0 : fechaPublicacion.hashCode());
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
        Libro other = (Libro) obj;
        if (id_libro == null) {
            if (other.id_libro != null)
                return false;
        } else if (!id_libro.equals(other.id_libro))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (fechaPublicacion == null) {
            if (other.fechaPublicacion != null)
                return false;
        } else if (!fechaPublicacion.equals(other.fechaPublicacion))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Libro [id_libro=" + id_libro + ", titulo=" + titulo + ", fechaPublicacion=" + fechaPublicacion + "]";
    }
    
}
