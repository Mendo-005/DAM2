package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autor")
public class Autor implements Serializable{
    public static final long serialVersionUID  = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_autor;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Sex sex;
    public enum Sex{H,M}
    
    @ManyToMany(mappedBy = "autores")
    private Set<Libro> libros;
    
    public Autor() {
        libros = new HashSet<>();
        sex = Sex.M;
    }

    public Autor(String nombre, Sex sex) {
        this();
        this.nombre = nombre;
        this.sex = sex;
    }

    public void addLibro(Libro libro)
    {
        libros.add(libro);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_autor() {
        return id_autor;
    }

    public void setId_autor(Long id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_autor == null) ? 0 : id_autor.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
        Autor other = (Autor) obj;
        if (id_autor == null) {
            if (other.id_autor != null)
                return false;
        } else if (!id_autor.equals(other.id_autor))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (sex != other.sex)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Autor [id_autor=" + id_autor + ", nombre=" + nombre + ", sex=" + sex + "]";
    }
    
}
