package dev.mendo.dominio.modelo;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tutores")
public class Tutor implements Serializable
{
	public static final long serialVersionUID = 1l;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tutor")
	private Integer id;
	
	@Column(nullable  = false)	
	private String nombre;
	
	@Column(unique = false, nullable = false)
	private String apellido;
	
	@Column(unique = true)
	private String email;
	
	private String telefono;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable  = false)
	private Sex tipo;
	public enum Sex{Instituto,Empresa}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa")
	private Empresa empresas;


	public void setEmpresas(Empresa empresas) {
		this.empresas = empresas;
	}

	public Tutor() {}
	public Tutor(String nombre, String apellido, String email, String telefono, Sex tipo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.tipo = tipo;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	public Sex getTipo() {
		return tipo;
	}
	public void setTipo(Sex tipo) {
		this.tipo = tipo;
	}
	public Empresa getEmpresas() {
		return empresas;
	}
	public void setEmpresas(Set<Empresa> empresas) {
		this.empresas = (Empresa) empresas;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((empresas == null) ? 0 : empresas.hashCode());
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
		Tutor other = (Tutor) obj;
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
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
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
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (empresas == null) {
			if (other.empresas != null)
				return false;
		} else if (!empresas.equals(other.empresas))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Tutor [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", telefono="
				+ telefono + ", tipo=" + tipo + "]";
	}
}
