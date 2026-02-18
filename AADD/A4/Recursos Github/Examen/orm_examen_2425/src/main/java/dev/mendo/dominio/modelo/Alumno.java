package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Alumnos")
public class Alumno implements Serializable
{
	public static final long serialVersionUID = 1l;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alumno")
	private Integer id;
	
	@Column(name = "nombreyapellido", nullable = false)
	private String NomApe;
	
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNac;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomApe() {
		return NomApe;
	}

	public void setNomApe(String nomApe) {
		NomApe = nomApe;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((NomApe == null) ? 0 : NomApe.hashCode());
		result = prime * result + ((fechaNac == null) ? 0 : fechaNac.hashCode());
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
		Alumno other = (Alumno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (NomApe == null) {
			if (other.NomApe != null)
				return false;
		} else if (!NomApe.equals(other.NomApe))
			return false;
		if (fechaNac == null) {
			if (other.fechaNac != null)
				return false;
		} else if (!fechaNac.equals(other.fechaNac))
			return false;
		return true;
	}

	public Alumno() {
	}

	public Alumno(String nomApe, LocalDate fechaNac) {
		NomApe = nomApe;
		this.fechaNac = fechaNac;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", NomApe=" + NomApe + ", fechaNac=" + fechaNac + "]";
	}
	
}
