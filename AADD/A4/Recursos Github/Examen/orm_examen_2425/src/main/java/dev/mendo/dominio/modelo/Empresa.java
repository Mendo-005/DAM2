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
	
	private String email;
	
	private String telefono;

	@ManyToMany(mappedBy = "empresas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
	
	

}
