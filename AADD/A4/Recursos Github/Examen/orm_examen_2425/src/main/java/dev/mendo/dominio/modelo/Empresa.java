package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empresas")
public class Empresa implements Serializable
{
	public static final long serialVersionUID = 1l;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Integer id;
	
	@Column(nullable  = false)
	private String nombre;
	
	private String sector;
	
	private String direccion;
	
	@Column(name = "email_contacto")
	private String email;
	
	@Column(name = "telefono_contacto")
	private String telefono;

	@OneToMany(mappedBy = "empresas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Tutor> tutores;

	public void addTutor(Tutor tutor){
		tutores.add(tutor);
	}

	public Empresa() {
		tutores = new HashSet<>();
	}

	public Empresa(String nombre, String sector, String direccion, String email, String telefono) {
		this();
		this.nombre = nombre;
		this.sector = sector;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tutores == null) ? 0 : tutores.hashCode());
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tutores == null) {
			if (other.tutores != null)
				return false;
		} else if (!tutores.equals(other.tutores))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + ", sector=" + sector + ", direccion=" + direccion
				+ ", email=" + email + ", telefono=" + telefono + ", tutores=" + tutores + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Tutor> getTutores() {
		return tutores;
	}

	public void setTutores(Set<Tutor> tutores) {
		this.tutores = tutores;
	}
	
	

}
