package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="modulo")
public class Modulo implements Serializable{

    private static final long serialVersionUID = 1L;
    
    // El id no sera incremental, ya que cada uno tiene un id legal
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="curso", nullable = false)
    private String curso;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @ManyToMany(mappedBy= "modulosMatriculados")
    private Set<Alumno> listaAlumnos = new HashSet<>();

    
    public Modulo() {}
    
    public Modulo(Long id, String curso, String nombre) {
        this.id = id;
        this.curso = curso;
        this.nombre = nombre;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(Set<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Modulo other = (Modulo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Modulo [id=" + id + ", curso=" + curso + ", nombre=" + nombre + "]";
    }

    public boolean anniadirAlumno(Alumno alumno)
    {
        return listaAlumnos.add(alumno);
    }

    public boolean  quitarAlumno(Alumno alumno)
    {
        return listaAlumnos.remove(alumno);
    }
}
